# VSand – Board Update Algorithm (GPU Compute)

This document describes how VSand updates its 2D “falling sand” board each simulation tick, based on the current code and shaders. It is meant to be detailed enough for someone to re‑implement or redesign the algorithm (GPU or CPU) without needing to read the original sources.

**Primary sources**
- `VSand/org.sheepy.vsand/src/main/shader/board_update.comp`
- `VSand/org.sheepy.vsand/src/main/shader/draw.comp`
- `VSand/org.sheepy.vsand/src/main/shader/board_to_pixel.comp`
- `VSand/org.sheepy.vsand/src/main/resources/Application.vsand.lm`
- `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/constants/BoardConstantBufferAdapter.java`
- `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/constants/DrawConstantBufferAdapter.java`
- `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/constants/PixelConstantBufferAdapter.java`
- `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/loader/*BufferLoader.java`
- `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/util/TransformationUtil.java`

---

## 1) High‑level model

The “world” is a 2D grid of **cells** of size `WIDTH × HEIGHT`. Each cell stores an **8‑bit material id** (0–255).

At each simulation step, cells can:
- **swap** with one neighbor (down/left/right/up) based on **density** and a few heuristics (this is the “movement” system),
- **transform** into another material based on **neighbor‑dependent reaction rules** (this is the “chemical/reaction” system).

The simulation is implemented as a Vulkan compute pipeline and is updated in **fixed-size chunks** to enable sleeping/early-out.

Material id `0` is the “Void” (empty space). It is still treated as a regular dynamic material by the movement rules (it has `density` and `runoff`), which is how buoyancy is achieved: lighter-than-void materials (negative density) rise because void swaps downward into them.

---

## 2) GPU data layout (what the shaders read/write)

### 2.1 Board storage (two ping‑pong buffers)

There are 2 storage buffers:
- **Board 1**: `buffer uint board1[]`
- **Board 2**: `buffer uint board2[]`

They implement ping‑pong (double buffering): each step **reads from one** and **writes to the other**.

#### 2.1.1 Swizzled 2×2 packing

Cells are stored as bytes packed in a `uint32` covering a `2×2` block:

```
byte 0 (bits  0.. 7) = cell (x even, y even)     // top-left
byte 1 (bits  8..15) = cell (x odd,  y even)     // top-right
byte 2 (bits 16..23) = cell (x even, y odd)      // bottom-left
byte 3 (bits 24..31) = cell (x odd,  y odd)      // bottom-right
```

Indexing is “column‑major”:
- `swizzledX = x >> 1`
- `swizzledY = y >> 1`
- `boardIndex = swizzledX * SWIZZLED_HEIGHT + swizzledY`

where:
- `SWIZZLED_WIDTH = WIDTH / 2`
- `SWIZZLED_HEIGHT = HEIGHT / 2`

This layout is used consistently by:
- `board_update.comp` for reading and writing,
- `draw.comp` for atomic per-cell writes,
- `board_to_pixel.comp` for reading and rasterizing.

### 2.2 Chunk buffer (sleeping / active / render-dirty)

The board is divided into fixed **chunks of 16×16 cells**.

There is a storage buffer:
- `buffer int chunks[]` of size `CHUNK_WIDTH * CHUNK_HEIGHT`

with:
- `CHUNK_WIDTH = WIDTH / 16`
- `CHUNK_HEIGHT = HEIGHT / 16`
- `chunkIndex = chunkX * CHUNK_HEIGHT + chunkY`  (again, X-major)

Each `chunks[chunkIndex]` is a bitfield. The shaders effectively use:

- `bit 0 (value 1)`: **simulation-active**
  - If set, `board_update.comp` fully simulates this chunk.
  - If clear, the chunk is considered “sleeping” for physics (with a border exception described later).

- `bit 1 (value 2)`: **render-dirty / render-awake**
  - If set, `board_to_pixel.comp` will rasterize this chunk to the output image.
  - If clear, `board_to_pixel.comp` can skip work and keep the previous pixels.

Other bits may exist historically, but this is the behavior in the current shaders.

### 2.3 Material configuration buffer

There is a uniform buffer `materials[]` (std140) that stores per-material parameters:

```
struct Entry {
    int isStatic;   // 1 = immovable, 0 = dynamic
    int density;    // higher = “heavier”
    int runoff;     // horizontal spread/search range
    int padding;
    vec4 color;     // used for rendering only
};
```

It is populated by `ConfigurationBufferLoader` from the `(materials ...)` section of `Application.vsand.lm`.

Movement uses `isStatic`, `density`, `runoff`.

### 2.4 Transformation (reaction) matrix buffer

There is a uniform buffer `transformations` addressed as `uvec4[]`:

```
layout(binding = 1) uniform STransformation {
    uvec4 data[TRANSFORM_ARRAY_COUNT];
} transformations;
```

Conceptually it is a dense 2D matrix:

```
transfo[catalystMaterialId][reactantMaterialId] -> packed rule
```

This is filled by Java (`TransformationUtil`) from the `(transformations ...)` section of `Application.vsand.lm`.

#### 2.4.1 Indexing and packing to `uvec4[]`

The shader computes a linear index:

```
index = catalystId * MATERIAL_COUNT + reactantId;
uvec4Index   = index >> 2;    // index / 4
uvec4Element = index & 3;     // index % 4
rule = transformations.data[uvec4Index][uvec4Element];
```

So `uvec4` is purely a packing trick to avoid `float[]` std140 overhead: it is physically just an `int[]` / `uint[]` in memory grouped by 4.

Note: `TRANSFORM_ARRAY_COUNT` is computed as `(MATERIAL_COUNT * MATERIAL_COUNT + 3) / 4`, i.e. rounded up so the last `uvec4` can hold the remaining 1–3 entries when the matrix size is not divisible by 4.

#### 2.4.2 Rule bit layout (32-bit)

Each rule is a 32‑bit integer (or `-1` meaning “no rule”):

- bits `0..7`: `targetMaterialId` (new cell value if the transformation triggers)
- bits `8..15`: `propagation` (0..255; how far to spread the transformation)
- bits `16..29`: `probability` (0..1000 used by the game; shader extracts 14 bits)
- bit `30`: `STATIC_FLAG` (set when the rule is declared as `isStaticTransformation=true` in the model)
- bit `31`: unused in current Java writer

Decode in shader:

```
target = rule & 0xFF
propagation = (rule >> 8) & 0xFF
probability = (rule << 2) >> 18   // extracts bits 16..29
staticFlag = (rule & (1 << 30)) != 0
```

#### 2.4.3 How rules are authored and expanded (Java side)

The instance model (`Application.vsand.lm`) contains a list of transformation objects, which Java expands into the dense matrix described above (`TransformationUtil.toArray(...)`):

- `Transformation`
  - Fields: `reactant`, optional `catalyst`, `target`, `probability`, optional `propagation`, optional `isStaticTransformation`.
  - If `catalyst` is omitted, Java expands it to “all catalysts” (every material id), so the rule is effectively neighbor-independent (but note the shader still only checks when at least one neighbor differs).

- `MultipleTransformation`
  - Fields: a `MaterialProvider` for `reactants` and one for `catalysts` plus the same `target/probability/propagation/isStaticTransformation`.
  - `MaterialProvider.filterMode=true` means “all materials except this list”.

- `isStaticTransformation=true`
  - Java ignores the explicit catalyst list and instead writes the rule for catalysts that are likely to “block” the reactant:
    - `potentialCatalyst.isStatic == true` **or**
    - `potentialCatalyst.density >= reactant.density`,
    excluding `potentialCatalyst == reactant`.

If multiple model rules write to the same `(catalyst, reactant)` matrix entry, the last one processed wins (it overwrites the previous value in the `int[]`).

---

## 3) Pipeline scheduling (what runs each frame)

From `Application.vsand.lm`, the compute process contains three compute pipelines:

1) **Draw** (`draw.comp`)
   - Runs only when there are draw commands in the queue.
   - Writes directly into the current board buffer using atomics.
   - Sets affected chunks to state `3` (active + render-dirty).

2) **Step / Update Board** (`board_update.comp`)
   - This is the simulation tick.
   - It is wrapped in a `CompositeTask` with a variable `repeatCount` (“speed”), so multiple steps can be executed per frame.
   - Each repeat pushes 2 values via push constants:
     - a random seed (`float`)
     - the destination board index (`uint boardToWrite`)

3) **Board to Pixel** (`board_to_pixel.comp`)
   - Rasterizes the current board buffer to an output storage image (the displayed “board image”).
   - Uses chunk sleeping to skip pixels for stable chunks (unless debug/brush highlight forces it).

### 3.1 Board ping‑pong index (who toggles it)

`BoardConstantBufferAdapter` performs the ping‑pong:

- Before each **board update dispatch**, it:
  1) generates a new random float,
  2) toggles `currentBoardBuffer` between 0 and 1,
  3) pushes `{random, boardToWrite=currentBoardBuffer}` to the shader.

`PixelConstantBufferAdapter` and `DrawConstantBufferAdapter` read `currentBoardBuffer` to decide which board to read/write for rendering and drawing (they do not toggle it).

Practical meaning:
- After a board update dispatch completes, `currentBoardBuffer` is the board containing the latest state.
- The next board update will toggle and write into the other buffer, reading from the current one.

---

## 4) Board update compute shader (movement + transformations)

### 4.1 Chunk / workgroup mapping

The simulation uses:
- `EFFECTIVE_SIZE = 16` (cells per chunk side)
- `WORKGROUP_SIZE = 20` (threads per workgroup side)

Each workgroup is responsible for **one chunk** of `16×16` cells, but it also loads a 2‑cell border around it so it can look at neighbor cells without extra global memory accesses.

The global cell coordinate processed by a thread is:

```
cell = workgroupId * 16 + localId - 2
```

This comes from:
`loc = gl_GlobalInvocationID.xy - 2 - (4 * gl_WorkGroupID.xy)`
and `gl_GlobalInvocationID = gl_WorkGroupID * 20 + gl_LocalInvocationID`,
so `loc = gl_WorkGroupID * (20 - 4) + gl_LocalInvocationID - 2 = chunkOrigin + local - 2`.

Meaning:
- local indices `2..17` correspond to the chunk’s “real” 16×16 cells,
- indices `0..1` and `18..19` are the loaded border (2 cells wide on each side).

#### 4.1.1 Ownership, halo cells, and cross‑chunk swaps

Only the `16×16` “owned” cells of a chunk (local indices `2..17`) are **ever written back** to the destination board buffer.
The outer cells exist only as a **halo**:
- to read neighbor values cheaply,
- and to compute **movement intent** consistently near chunk boundaries.

Swaps and transformations are deliberately *not* executed on the halo cells (the shader excludes `farBorder` and `closeBorder` for those phases). This prevents a cell from being updated by two different workgroups.

Cross‑chunk movement still works because of the halo:
- A cell on the edge of a chunk may want to swap with a neighbor that belongs to an adjacent chunk.
- The edge cell can read the neighbor’s **intent bits** from its local halo copy (`decision[localTarget]`).
- The adjacent chunk’s workgroup computes the *same* intent for that neighbor cell (same source board state + same per-step seed + same deterministic hash), and executes the swap on its side because for it the cell is “owned”.

This “redundant intent evaluation on overlap” is the reason the workgroup is larger than the chunk and why there are multiple border bands:
- `farBorder` (local 0/19) is a pure read halo,
- `closeBorder` (local 1/18) participates in intent/acceptance decisions but does not execute swaps/transforms,
- the owned region (local 2..17) executes swaps/transforms and is written back.

### 4.2 Shared memory working set

The shader uses shared memory arrays:

- `shared uint board[20][20]`  
  The local working board (cell material ids) for this chunk + border.

- `shared uint decision[20][20]`  
  A per-cell scratch buffer storing:
  - the post-move “current value” in the top byte,
  - the chosen direction + target value for swaps in lower bits.

The algorithm is structured in phases separated by `barrier()` so all threads agree on the same intermediate state.

### 4.3 Chunk activation and neighbor influence

At the start of the workgroup:
- `chunkActive` is computed from `chunks[chunkIndex] & 1`.
- `chunkNeighborActive` reads the same bit from the 4 neighbor chunks (left/up/right/down), if they exist.

Each cell is considered **active for simulation** if:

```
activeCell =
    cell is within board bounds
    AND material.isStatic == 0
    AND (chunkActive OR borderActive)
```

`borderActive` is true only near chunk borders (local indices 1–2 or 17–18) if the adjacent chunk is active.

This is the “sleeping chunk” optimization:
- sleeping chunks are mostly skipped,
- but they can still interact at the boundary with active neighbors.

### 4.4 Phase 0: load the source board into shared memory

Only “even” local cells load packed `uint` values:

```
evenCell = (x % 2 == 0) && (y % 2 == 0)
```

An even cell loads a single packed `uint` from the **source board** (the opposite of `boardToWrite`), unpacks its 4 bytes, and writes them to the 2×2 region in shared `board[][]`.

Out-of-bounds cells (outside `0 <= x < WIDTH`, `0 <= y < HEIGHT`) are treated as material id `0` (Void).

### 4.5 Phase 1: initialize decision buffer

Each thread writes:

```
decision[local] = currentValue << 24
```

This means: at this point, the top byte of `decision` is “the current cell value”.

Later, after swaps, the top byte is overwritten so `decision >> 24` becomes “the post-move value”.

### 4.6 Phase 2: choose a movement (chooseTo)

This phase sets the intended swap direction for some cells.

Terminology:
- “heavier” = higher `density`
- swaps only happen with non-static targets (`target.isStatic == 0`)

#### 4.6.1 Downward swap (“falling”)

If the cell below has lower density and is dynamic:

```
wantToFallDown = (below.isStatic == 0) && (densityHere > densityBelow)
```

then with probability **92%** (per-cell pseudo-random) the cell chooses `DOWN`:

```
if random(loc, seed, 0) < 0.92:
    decision |= TO_DOWN
    decision |= (belowValue << 16)   // store target value for swap
```

Important: even when the 92% check fails, the function still returns “I wanted to move”, which prevents the chunk from sleeping (see 4.9).

This introduces stochasticity and prevents stable-but-should-fall states from sleeping forever.

#### 4.6.2 Lateral swap (“runoff” / spreading)

If not falling down (or the fall luck failed), the cell may try left/right.

It considers a side only if the adjacent cell is dynamic and less dense:

```
canTryLeft  = (left.isStatic  == 0) && (densityLeft  < densityHere)
canTryRight = (right.isStatic == 0) && (densityRight < densityHere)
```

Then it computes a “distance to a drop” on each side using `checkFree(...)`, up to `runoff` cells.

##### What `checkFree` is looking for

Starting from the neighbor cell and walking `dir = ±1`:
- the path must go through cells with density **strictly lower** than the source (and non-static),
- it stops when it finds a position where the cell **below** is also lower density (so the source could start falling there).

If no such “drop” is found within `runoff`, the side is rejected.

##### The move is only one cell

Even when `checkFree` finds a drop farther away, the chosen move is still a **one-cell swap** with the immediate left/right neighbor.

`runoff` influences:
- whether a direction is considered valid at all (drop within range),
- which side is preferred (shorter distance to the first drop).

##### “Pressed” heuristic

If the cell above is the **same material** (`isPressed`), there is an additional heuristic:

- with a global random condition (`seed < 0.3`), `distLeft` or `distRight` is forced to `0`, allowing lateral motion even without a detected drop.

This is one of the key behaviors that makes high-runoff materials look “flatter”.

#### 4.6.3 Direction choice

If both sides are valid, the shader chooses the side with the smaller `dist`.
On tie, it uses a per-cell random (`random(loc, seed, 1) < 0.5`).

It then stores the chosen direction and the immediate neighbor’s value as `targetValue` (bits 16..23).

### 4.7 Phase 3: accept an incoming swap (acceptFrom)

Cells that did **not** choose a move can still be the target of a heavier neighbor’s move.

This phase tries to “accept” an incoming swap by choosing the opposite direction, enabling a 2‑way agreement.

For each cell, it checks up to 3 neighbor candidates in this order:
1) **UP** (always first)
2) **RIGHT or LEFT** (random choice)
3) the remaining side (the opposite of step 2)

For a candidate neighbor:
- if `neighborDensity > myDensity`,
- and if the neighbor has already requested to move into this cell (its `decision` contains the matching direction bit),

then this cell sets its own direction to the opposite and stores the neighbor’s value as the swap target.

This makes swaps effectively require **mutual agreement**:
- movers propose a direction,
- targets accept if appropriate,
- swaps execute only when both sides agree.

### 4.8 Phase 4: execute swaps (swap)

Only cells in the inner effective region execute swaps (local indices 2..17; borders are excluded).

For a cell with a non-zero direction bit:
- compute the target location and the inverse direction bit,
- if the target is out of bounds **or** the target cell’s decision contains the inverse direction,
  then execute the swap by replacing `board[local]` with `targetValue` stored in the decision.

When a swap executes, it also:
- sets `MOVED_TAG` (bit 4) in the decision,
- rewrites the top byte of `decision` to the post-swap value so that later phases can use `decision >> 24` as the authoritative “post-move” board.

### 4.9 Phase 5: apply transformations (reactions)

After movement, each inner cell may transform into another material based on its 4-neighborhood.

Inputs:
- `currentValue = decision[local] >> 24` (post-move value)
- `neighborValues = (decision[neighbor] >> 24)` for up/down/right/left

If at least one neighbor value differs from the current value, it evaluates up to 4 transformation rules:

```
rule = transfo[neighborValue][currentValue]
```

Rules are tried in neighbor order: **UP, DOWN, RIGHT, LEFT**.

#### Probability sampling

The shader generates a single random integer `rand` in `[0..1000]` for the cell (once per phase), as:

```
rand = round( sqrt( random(loc, seed, 3) ) * 1000 )
```

Note the `sqrt(...)`: it biases the distribution (not uniform).

The rule triggers when:

```
rand < rule.probability
```

On success:
- the cell becomes `rule.targetMaterialId`,
- if `rule.propagation > 1`, it additionally propagates in the 4 cardinal directions, converting contiguous same-material cells up to the propagation distance.

#### Static transformations

Java sets `STATIC_FLAG` (bit 30) for `isStaticTransformation=true` rules.

In the current GLSL, this flag is used only in the selection condition:

```
if (rule exists) and (neighborValue != currentValue OR (STATIC_FLAG && !movedThisTick)) ...
```

However, the **dominant behavior** for static transformations comes from how Java expands them:
- in `TransformationUtil`, a static transformation ignores the configured catalyst and is instead written for catalysts that are either:
  - `isStatic == true`, or
  - `density >= reactant.density`,
  excluding the reactant itself.

So a static transformation is effectively “reactant transforms when adjacent to a blocking/heavier material”, with the flag also available to gate on movement depending on the shader logic.

### 4.10 Phase 6: write back to the destination board buffer

Even local cells (top-left of each 2×2 block) pack 4 shared `board[][]` values back into a `uint` and write it to the **destination** board buffer.

This writeback is skipped entirely when the chunk is sleeping and nothing changed.

Specifically, the shader writes the chunk if `(updated || chunkActive)`:
- `updated` becomes true when any movement decision occurred, a swap executed, or a transformation happened (within the effective region).
- `chunkActive` is the previous “active” bit (bit0) from the chunk buffer.

This is a critical detail for ping‑pong correctness:
- while a chunk is active, it keeps copying/writing, so both board buffers can be brought into sync when the chunk goes to sleep,
- once sleeping and synced, the shader can skip writeback safely even though the global ping‑pong index still toggles every step.

### 4.11 Phase 7: update chunk state bits

At the end of the workgroup, one invocation writes:
- if `updated`: `chunks[chunkIndex] = 3` (active + render-dirty)
- else: `chunks[chunkIndex] &= 2` (clear active, keep only bit1 if it was set by rendering/hover)

So:
- physics sleeping is controlled by bit0,
- render sleeping is controlled by bit1.

---

## 5) Drawing compute shader (user edits)

`draw.comp` applies draw commands (square/circle/line) into the board buffers.

Key points:
- It runs over the chunk grid, and each invocation corresponds to a single cell `(x, y)`.
- If the cell is inside the shape, it writes the new material id using atomics into the swizzled board buffer:
  - `atomicAnd` to clear the previous byte
  - `atomicOr` to set the new byte
- It sets the chunk state to `3` for the affected chunk, waking physics and marking it render-dirty.

Draw writes into `boardToWrite` taken from `DrawConstantBuffer`, which is populated from the current ping‑pong index.

---

## 6) Board-to-pixel compute shader (rendering)

`board_to_pixel.comp` converts the current board buffer into an RGBA8 output image.

Per chunk:
- It reads `chunks[chunkIndex]` and uses bit1 to decide if the chunk is “sleeping for rendering”.
- It also does a cheap “circle vs chunk AABB” test to decide whether the mouse brush highlight intersects the chunk.

Per invocation:
- Each invocation covers one packed `uint` (a 2×2 block of cells) and writes 4 pixels.
- If the chunk is render-sleeping and no debug/highlight forces rendering, it returns early, preserving existing pixels in the output image.

This is a performance optimization: unchanged chunks do not get re-rasterized each frame.

---

## 7) What this implies for “water / fluid” behavior

In this algorithm, “water” is not a continuous fluid simulation. It is just a material with:
- moderate density (so it sinks below lighter materials and floats above heavier),
- high `runoff` (so it can search farther for a drop and thus spreads flatter).

Important limitations that directly impact fluid quality:

1) **Movement is a local swap, at most 1 cell per tick.**  
   Even with high runoff, a cell only swaps with an immediate neighbor; runoff only decides *whether* and *which side* to swap.

2) **No pressure / no velocity / no incompressibility.**  
   There is no notion of pressure equalization in a pool; the only “pool behavior” comes from the `isPressed` heuristic and repeated local swaps.

3) **Randomness is part of the core behavior.**  
   Downward falling is probabilistic (92% per tick), and some lateral behavior is driven by global/per-cell random decisions. This can make water appear jittery or “stuck then jumpy”.

4) **Sleeping chunks gate updates.**  
   Only active chunks are fully simulated, with limited border activation. This is good for performance but adds constraints if you want long-range fluid effects (waves/pressure) because a sleeping area does not update unless re-activated.

If you redesign the water simulation, you will likely want to revisit one or more of:
- the “swap only” movement model,
- how runoff is interpreted (range vs velocity),
- adding pressure/height/flow fields (possibly separate buffers),
- the chunk sleeping policy (or how to propagate wakeups).
