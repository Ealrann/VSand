# VSand `new_algorithm.md` — Implementation Notes + Review Context (Mass + Pressure Liquids)

This document is a companion to `VSand/new_algorithm.md`.

It serves two purposes:

1. Record the **important deltas** between the design doc and the **current implementation** in VSand.
2. Capture the **current difficulties/needs** so the author/reviewer of `new_algorithm.md` can propose an improved approach (especially for long-distance flattening).

Audience: someone who understands CA + pressure-like liquid solvers in grid sandboxes, but **has not worked on VSand**.

## Glossary (VSand terms)

- **Cell**: one fine-grid pixel.
- **Chunk**: fine simulation chunking; **16×16** effective cells.
- **Tick**: one `board_update.comp` dispatch (one CA update step).
- **Pressure iteration**: one `pressure_flux.comp` dispatch + one `pressure_apply.comp` dispatch.
- **Frame**: render frame; tests/dump runner can run multiple ticks per frame via `speed` (repeatCount).

## Quick status (what exists in code today)

Implemented (mass fluid layer + first pressure relaxation pass):

- Fine-grid `liqMass` stored as **packed u16** in ping-pong buffers (same swizzled layout as the board).
- “Pressure-enabled liquid” flag on materials (bitfield in the existing configuration UBO int slot).
- Drawing writes **both** `mat` and `liqMass` (full cell for pressure-liquids, zero otherwise).
- CA movement (`board_update.comp`) carries mass through swaps; transformations implement an initial coherence policy.
- Pressure relaxation implemented as conservative **flux + apply**, repeated **6 iterations per tick** (hard-coded in `Application.vsand.lm`).
- Fetch/debug/test utilities can retrieve and inspect mass.
- Optional on-screen “mass view” (`m`) to visualize fill/compression.
- Headless dump runner to inspect the board/mass evolution frame-by-frame (`VSand/state-dump-runner.md`).

Not implemented yet (described by `VSand/new_algorithm.md`, but not in code today):

- Separate `ChunkStateLiquid` buffer (we reuse the existing chunk buffer with a new bit).
- Pressure early-out on convergence / dynamic iteration count.
- Optional Pass 3.3 compaction/cleanup (surface-tension merge/fill).
- Any coarse air simulation (Pass 4–6).

## Where to look (current implementation)

Shaders:

- `VSand/org.sheepy.vsand/src/main/shader/board_update.comp` (Pass 1 CA movement + transformations; mass integrated here)
- `VSand/org.sheepy.vsand/src/main/shader/draw.comp` (edits; writes mat + mass)
- `VSand/org.sheepy.vsand/src/main/shader/pressure_flux.comp` (Pass 3.1 compute fluxes)
- `VSand/org.sheepy.vsand/src/main/shader/pressure_apply.comp` (Pass 3.2 apply fluxes)
- `VSand/org.sheepy.vsand/src/main/shader/mass_utils.glsl` (constants + swizzled mass indexing)
- `VSand/org.sheepy.vsand/src/main/shader/board_to_pixel.comp` (render; includes mass debug view)

Assets / scheduling:

- `VSand/org.sheepy.vsand/src/main/resources/Application.vsand.lm` (simulation pipeline order; pressure iteration count is encoded here)

Tooling:

- `VSand/state-dump-runner.md` (manual)
- `VSand/scenarios/` (dump runner scenarios)

## Current simulation pipeline (runtime reality)

Per tick we currently do:

1. **Pass 1**: `board_update.comp` (CA swap + transformations + mass coherence + chunk state updates)
2. **Pass 3**: pressure relaxation, implemented as **6** hard-coded iterations:
   - `pressure_flux.comp` then `pressure_apply.comp`, repeated 6 times

This ordering preserves the existing “falling look” (CA controls free-fall/runoff), while pressure iterations handle “pool packing + leveling” on supported cells.

## 1) Data layout — mass is logical `u16`, physically packed in `uint[]`

`VSand/new_algorithm.md` recommends `liqMass[x,y] : uint16` and “same swizzled indexing as the board”.

**What we did**

- Mass buffers are SSBO `uint[]`; each `uint` packs **2×u16**.
- A 2×2 swizzled block uses **2 uint words** (row0 + row1) → 4×u16 total, matching the design.
- Helpers live in `VSand/org.sheepy.vsand/src/main/shader/mass_utils.glsl` (`massIndexShift()`, `packedU16Read/Write()`).
- CPU-side buffer sizing:
  - `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/loader/Mass1BufferLoader.java`
  - `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/loader/Mass2BufferLoader.java`

**Why it matters**

- Any new shader pass must use the same indexing mapping; otherwise mass will desync from materials.

## 2) Mass constants (u16 scale)

`VSand/new_algorithm.md` uses u8 examples; current VSand implementation uses u16:

- `M_FULL = 4096`
- `M_MAX  = 4352` (slight compressibility headroom: `+256`)
- `M_EPS  = 16`
- `MIN_FLOW = 16`
- `PRESSURE_HORIZONTAL_DIV = 2`

Defined in `VSand/org.sheepy.vsand/src/main/shader/mass_utils.glsl`.

Additional tuning in `pressure_flux.comp`:

- `PRESSURE_HORIZONTAL_EMPTY_DIV = 1`
- Used only when the **destination is empty** (`destMat == 0`) for left/right flows and claim scoring.

## 3) “Pressure-liquid” material flag (std140-safe)

`VSand/new_algorithm.md` suggests reusing an existing int field to keep std140 layout stable.

**What we did**

- `Entry` has an int slot that was previously padding; we treat it as a `flags` bitfield (layout unchanged).
- Bit `1` means “pressure-enabled liquid”.
- Loader: `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/loader/ConfigurationBufferLoader.java`
- Current policy: all liquids (Water, Lava, Acid, Petrol, …) are flagged pressure-enabled.

## 4) Drawing/edits: drawing a pressure-liquid writes `liqMass=M_FULL`

Shader: `VSand/org.sheepy.vsand/src/main/shader/draw.comp`

- Writes `mat` and `liqMass`.
- Rule: if `mat` is pressure-liquid ⇒ `liqMass=M_FULL`, else `liqMass=0`.

Note: there is no partial-mass drawing yet.

## 5) Pass 1 (CA movement + transformations): `board_update.comp` integration notes

`VSand/new_algorithm.md` models Pass 1 and Pass 2 separately. In VSand, we integrated mass handling into the existing monolithic CA shader:

- `VSand/org.sheepy.vsand/src/main/shader/board_update.comp`

### 5.1 Swaps carry mass (movement decisions remain mat-driven)

- Movement decisions remain based on `mat` only (to preserve the existing fall look).
- When a swap happens, mass is moved by using a pre-swap snapshot (`cellInitialMass`) and writing to `cellMass`.
- If the resulting cell is not a pressure-liquid, mass is forced to 0 (coherence).

### 5.2 Chunk activity/sleeping constraints

VSand relies on chunk sleeping for performance. Any pass that runs on a chunk must keep ping-pong buffers coherent when the chunk becomes inactive.

Current chunk bits (reusing the existing chunk buffer):

- `CHUNK_FLAG_SIM_ACTIVE` (bit 0): CA updated this tick
- `CHUNK_FLAG_RENDER_DIRTY` (bit 1)
- `CHUNK_FLAG_LIQUID_ACTIVE` (bit 2): pressure solver should run in/around this chunk

`board_update.comp` sets `CHUNK_FLAG_LIQUID_ACTIVE` by scanning supported pressure-liquid cells and marking activity if:

- there is an empty neighbor (left/right), or
- a neighbor has a sufficiently different mass (threshold currently derived from `MIN_FLOW` and `PRESSURE_HORIZONTAL_DIV`).

## 6) Pass 3 (pressure relaxation): key deltas vs the design doc

Design intent: conservative flux solver that:

- suppresses mid-air smear (unsupported ⇒ no sideways)
- packs downward using a stable-state function
- levels laterally without turning into a thin low-mass carpet

Implementation lives in:

- `VSand/org.sheepy.vsand/src/main/shader/pressure_flux.comp`
- `VSand/org.sheepy.vsand/src/main/shader/pressure_apply.comp`

### 6.1 “Supported” gating differs from the doc

Doc intent (“free-fall suppression”): unsupported cells shouldn’t do sideways/upward; CA handles falling look.

**What we implement**

- `pressure_flux.comp` returns early if `isSupported(loc, materialId)` is false.
- Support is defined as:
  - below is **not empty**, and
  - below is **static** OR has **density ≥ current density**

This differs from the simpler “any liquid below supports”.

Rationale: preserve fall look and keep density ordering coherent when multiple liquids exist.

**Out-of-world note**

`pressure_flux.comp` contains code for “below out of bounds ⇒ flowDown = remaining”, but bottom-row cells are considered unsupported by `isSupported()` and are skipped. Out-of-world deletion is therefore primarily handled by the CA movement logic in `board_update.comp`.

### 6.2 Capacity safety: “single-winner claim” per destination (important)

To avoid exceeding destination capacity when multiple sources want to send to the same destination, we implemented a conservative claim mechanism:

- Each destination can effectively accept inflow from **at most one source** per iteration.
- Sources compute a `claimScoreForDestNeighbor()`.
- A source only emits a flow if it is the winner for that destination (`isWinningSourceForDest()`).

This avoids needing a second correction pass, but it likely caps throughput and can slow convergence over long distances.

### 6.3 Empty-target edge growth: more permissive than strict concavity gating

The design doc suggests allowing flow into empty only if it fills a concavity (e.g., `Ndest >= 2`) to avoid thin-film spread.

Current implementation allows empty-target lateral flow if any of these are true:

1. destination is **supported** (floor-contact style growth), or
2. destination passes concavity (`countSameLiquidNeighborsExcludingSource >= 2`), or
3. destination is empty and the cell **below destination is empty** (escape hatch to allow spreading above shallow regions; thin-film risk)

This logic is in `allowLateralIntoEmpty()` in `pressure_flux.comp`.

### 6.4 Faster empty-target equalization divisor

To speed up pool growth and leveling attempts, horizontal flows use:

- same-liquid: `diff / PRESSURE_HORIZONTAL_DIV` (currently `/2`)
- empty target: `diff / PRESSURE_HORIZONTAL_EMPTY_DIV` (currently `/1`)

This is applied in both:

- actual flow emission (left/right in `main()`), and
- claim scoring (horizontal potential in `claimScoreForDestNeighbor()`).

### 6.5 Incoming type resolution (multi-liquid reality)

We run the pressure solver for all pressure liquids, but flows only occur between same-liquid cells (and into empty). In `pressure_apply.comp`:

- if `mat==0` and `newMass>0`, material type is chosen by a fixed priority:
  1) from above (downward inflow), then 2) left, 3) right, else 4) below.

This is mostly safe because the claim mechanism tends to make inflow single-sourced, but it would become a correctness concern if multi-inflow is enabled later.

## 7) Iteration scheduling: hard-coded 6 iterations per tick (no early-out)

`VSand/pressure_iterations.md` recommends ~6 iterations per tick and early-out on convergence.

Current implementation:

- `Application.vsand.lm` contains “Pressure Flux 1..6” and “Pressure Apply 1..6”.
- There is no early-out yet; cost is mitigated via chunk masking (`shouldProcessChunk()`).

## 8) Debugging / validation tooling (implementation reality)

### 8.1 Mass view (`m`)

`board_to_pixel.comp` can render pressure-liquid mass as a debug overlay (cyan→orange/red for compression).

### 8.2 Headless dump runner

See `VSand/state-dump-runner.md`.

Used command (example):

`./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=192x128 --scenario=$PWD/VSand/scenarios/water_flatten_50x100.txt --frames=300 --speed=1 --seed=1234 --out=/tmp/vsand-dumps/example"`

The runner dumps, per frame:

- `frame_XXXX.board.swz.bin`
- `frame_XXXX.mass.swz.bin`
- `frame_XXXX.txt` summary (counts, min/max mass by material)

### 8.3 Scenarios relevant to flattening

- Boxed (touches many chunks, hits walls): `VSand/scenarios/water_flatten_50x100.txt`
- “Open” (more room): `VSand/scenarios/water_flatten_50x100_open.txt`

## 9) Current difficulty: long-distance flattening still too slow (needs design review)

### 9.1 Requirement (what we need for gameplay)

We need liquids (water first) to:

- keep the existing falling look (CA controls free-fall; pressure solver should not smear in air)
- avoid “thin wet film” behavior on flat ground
- flatten into a pool **fast enough that it feels like liquid flows**

Concrete target used for evaluation:

- Scenario: “~50×100 water column in a box”.
- Expectation: within ~**300 ticks**, the pool should be “reasonably flat”, quantified as:
  - surface height variation **< 8 cells** across the main body.

### 9.2 Metric used in practice (current)

From dumps, for each x column inside the box:

- `height[x] = number of consecutive water cells from the bottom interior row upward`

We then compute `min(height)`, `max(height)`, `range = max-min`.

This measures the discrete surface profile (what the player perceives as “mound vs flat pool”).

We also tested using a mass threshold (only count cells with `liqMass >= M_FULL`) and the range stayed essentially the same, suggesting the issue is not just a thin underfilled film: it’s a real occupancy distribution slope.

### 9.3 Observed results (boxed scenario)

Scenario:

- `VSand/scenarios/water_flatten_50x100.txt`
- Board: `192×128` with a `Wall` frame thickness 2
- Initial water: `line Water 96 40 96 90 50` (approx 50 wide, ~100 tall due to rounded caps)

At **tick 300** with the current implementation (6 pressure iterations/tick + empty-target `/1`), the measured bottom-column heights are:

- `min ≈ 9`, `max ≈ 29`, `range ≈ 20`

This is improved compared to earlier iterations (range ~34), but still far from the target `< 8`.

### 9.4 Why this is worrying: we already do a lot of work per tick

At 6 pressure iterations per tick:

- 300 ticks ⇒ **1800** pressure iterations (flux+apply pairs), plus 300 CA updates.

Even with that amount of relaxation, the pool remains too “mounded” over long distances.

This strongly suggests:

- the local conservative solver behaves **diffusion-like** for long-range leveling (convergence ~O(distance²)), and/or
- the safety mechanisms (notably the **single-winner claim**) reduce throughput enough that the pool can’t equalize quickly.

### 9.5 “Is it chunk-limited?”

We checked for chunk boundary artifacts in the dumped height profiles and did not observe 16-cell periodic discontinuities.

The remaining shape is a smooth “hill”, which points to algorithmic convergence limits rather than chunk partitioning/scheduling.

### 9.6 What we tried (high signal)

Changes that helped but didn’t fully solve the problem:

- Allowed empty-target fill on supported ground (Option 2-ish “S2 edge growth”).
- Made empty-target horizontal equalization more aggressive (`/1` vs `/2`).
- Increased relaxation iterations per tick from 4 → 6.

Other heuristic tweaks (e.g., chunk liquid-active marking tweaks) did not materially change the long-distance flattening metric.

### 9.7 What we need from the design doc author / reviewer

Given VSand constraints:

- GPU compute (Vulkan), no convenient global CPU-side solve per tick
- chunk sleeping and halo-based scheduling
- multiple liquids eventually (water, lava, oil, acid…)
- must preserve the existing “fall” feel (no mid-air smear)
- must avoid “thin-film carpet” behavior on flat ground

What is the best algorithmic extension to achieve **fast long-distance leveling**?

The current `new_algorithm.md` path mostly relies on “increase N iterations of Pass 3”.
That helps, but may not be sufficient or cost-effective for VSand’s target feel.

We likely need one of:

- a non-local / multi-scale liquid pressure propagation mechanism (analogous in spirit to the coarse air “wave-like” solver), or
- a different transport model that moves mass laterally faster than neighbor-to-neighbor diffusion, while remaining conservative and stable.

If you propose changes, the most useful framing is:

- which new pass/buffers to add (and where in the tick order),
- why it should converge faster on wide pools,
- how it maintains the two core visual constraints (no mid-air smear, no thin-film carpet),
- how it interacts with chunk sleeping (locality/halo).

## 10) Known mismatches / technical debt

- Strict conservation is **not guaranteed** for legacy transformations that produce pressure-liquids (`non-liquid → pressure-liquid` currently creates `M_FULL` mass).
- Pressure relaxation currently limits each destination to a single inflow per iteration (claim mechanism). This is conservative and stable, but it can cap convergence speed.
- Pass 3.3 compaction/cleanup is not implemented yet; if we continue relaxing edge rules for speed, a compaction pass may be required to avoid thin-film artifacts.
