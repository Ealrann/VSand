According to a document from (undated), your current pressure-liquid solver is (a) intentionally gated to preserve the existing CA “free-fall” look, (b) conservative, but (c) still too slow for long-distance leveling: at tick 300 (≈1800 pressure iterations) the boxed flattening scenario still has a surface-height range ≈20 cells, far from the <8 target. The internal review also flags two likely root causes: diffusion-like long-range convergence and a throughput cap introduced by the “single-winner claim” (each destination can accept from at most one source per iteration).

Below is a concrete replacement for the **mass flow / pressure relaxation algorithm** that is meant to “flatten fast” while keeping your constraints:

* **Keep CA free-fall**: pressure solver does not act on unsupported cells (so falling water still looks like your current CA).
* **Avoid thin-film carpet**: keep (and slightly tighten) your empty-target gating rules (supported/concavity/spill), so water doesn’t endlessly smear across flat ground.
* **Sleep-able chunks**: still works with your `CHUNK_FLAG_LIQUID_ACTIVE` + halo scheduling model.

The key change is: **remove the per-destination “claim” entirely** by switching to **directional sweeps** (Gauss–Seidel style) where each destination has at most one inflow source per pass, plus compute **horizontal flow from a hydrostatic “head”** (pressure-from-column-height) rather than only the local mass difference. This directly targets the “mounded hill” long-range behavior you observed.

---

## 1) Summary of the proposed solver

### Replace the current Pass 3 loop

Current pipeline is:

1. `board_update.comp`
2. 6× (`pressure_flux.comp` + `pressure_apply.comp`)

Proposed pipeline:

1. `board_update.comp` (unchanged)
2. Run **N solver groups** per tick, where each group is **4 directional pressure iterations** (each iteration = flux+apply, same definition as your doc).

Each group:

1. **Down sweep** (pack downward using stable-state)
2. **Right sweep** (lateral leveling, head-driven)
3. **Left sweep** (lateral leveling, head-driven)
4. **Up sweep** (decompress upward if overfull)

So one group = 4 pressure iterations.

### Why this should flatten faster

1. **No “single-winner claim” cap**: by construction, a destination has only one possible inflow per sweep direction, so you don’t need the claim winner logic that “likely caps throughput.”

2. **Horizontal flow uses hydrostatic head (column weight)**: your current horizontal flow is `diff = srcMass - destMass` then `diff / 2` (or `/1` into empty). When the pool is mostly full, neighboring cells often have similar mass, so this becomes diffusion-like and slow over distance.
   Head-driven flow instead uses **“how much water is above me”** which stays meaningful even when masses are locally similar, and it more directly corresponds to “pressure wants to equalize surface height.”

3. **Directional Gauss–Seidel is inherently faster than Jacobi-like relaxation** for these kinds of leveling problems: each sweep applies updates immediately, so pressure information propagates further per iteration than a scheme that effectively averages using old values.

---

## 2) Constants and the single most important tuning change: split “equilibrium compressibility” from “transport capacity”

Your current constants are: `M_FULL=4096`, `M_MAX=4352` (only +256 headroom), `M_EPS=16`, `MIN_FLOW=16`, horizontal divisor `/2` (and `/1` into empty).

### Problem

With `M_MAX - M_FULL = 256`, a “full” neighbor cell has only 256 capacity. This strongly limits how much mass can pass through already-full regions per iteration (especially when you also have the claim cap). It’s one of the main reasons wide pools level slowly.

### Proposed fix: introduce two maxima

* **`M_EQ_MAX`**: equilibrium compressibility limit used by your **stable-state vertical packing** (keep it small to preserve “slight compressibility” visuals).
* **`M_CAP_MAX`**: absolute per-cell capacity used by **transport clamps** (make this larger to allow faster lateral throughput).

Recommended starting values (tune later):

* `M_FULL = 4096` (unchanged)
* `M_EQ_MAX = 4352` (same as current, +256)
* `M_CAP_MAX = 6144` (i.e., `M_FULL + 2048`)

Meaning:

* Vertical solver still tries to keep local “rest” behavior similar to today.
* But sideways transport can temporarily “overfill” cells up to 6144 to push a pressure wave through packed regions quickly, then the up/down sweeps redistribute it back toward equilibrium.

### Code-level change

Right now you clamp desired states and newMass to `M_MAX` (e.g., `min(stableState(...), M_MAX)` and apply-time clamp).
Change those clamps so:

* stable-state uses `M_EQ_MAX`
* capacity clamps use `M_CAP_MAX`
* apply clamps final mass to `M_CAP_MAX`

You can keep `M_EQ_MAX` and `M_CAP_MAX` as global constants for now, and later make them per-material if needed.

---

## 3) Hydrostatic head for horizontal flow

### Core definition

For a cell `loc` of material `mat` (pressure-liquid), define:

`Head(loc) = sum of mass of contiguous same-liquid cells from loc upward, until first non-mat or top-of-world`

This is the discrete “weight of the column above” (including self). Use 32-bit or 64-bit for the sum.

Then for a horizontal neighbor `n = loc + (±1, 0)`:

`ΔP = Head(loc) - Head(n)`

If `ΔP > 0`, flow should move from `loc` to `n`.

This turns “surface height differences” into a strong horizontal driving force even if the two cells have similar local mass.

### Practical bounded scan

You do not need a full-height scan to get a big improvement; you need enough to cover your typical height differences. Start with:

* `HEAD_MAX_CELLS = 64` (tune; if your typical height differences are <64 cells, this works well)

Then:

```glsl
uint headSum(ivec2 loc, uint mat) {
    uint sum = 0u;
    ivec2 p = loc;
    for (uint i = 0u; i < HEAD_MAX_CELLS; ++i) {
        if (p.y < 0) break;
        uint pm = readMaterialGlobal(p);
        if (pm != mat) break;
        sum += readMassGlobal(p);
        p.y -= 1;
    }
    return sum;
}
```

Notes:

* This is local, chunk-friendly, no global buffers.
* It respects your “no mid-air smear” constraint automatically because `Head()` only follows contiguous liquid (it stops at air gaps).

### Horizontal flow amount from head

Compute:

* `srcHead = headSum(loc, mat)`
* `dstHead = (dstMat==mat) ? headSum(dstLoc, mat) : 0`
  (If destination is empty, treat its head as 0. If it’s another material, no flow.)

Then:

* `ΔP = int(srcHead) - int(dstHead)`
* If `ΔP <= 0`: no flow.
* Otherwise proposed flow:

`proposed = uint(ΔP) / H_HEAD_DIV`

Recommended starting value:

* `H_HEAD_DIV = 4` (aggressive but usually stable with the directional sweeps)

  * Larger → more damping, slower leveling
  * Smaller → faster, potentially more “sloshing” / oscillation

Then clamp:

`flow = min(proposed, srcRemaining)`
`flow = min(flow, M_CAP_MAX - dstMass)`
and apply `MIN_FLOW` cutoff (as you already do).

---

## 4) Directional sweep solver in detail

### High-level per-tick scheduling

Let “pressure iteration” keep the same meaning as today: 1 flux dispatch + 1 apply dispatch.

Per tick:

1. Run `board_update.comp` (unchanged). This preserves CA free-fall and handles swaps carrying mass as in your integration notes.
2. For `group = 1..N_GROUPS` (tunable):

   * Run the 4 sweep iterations:

     1. Down
     2. Right
     3. Left
     4. Up

If you want “~300 iterations” as the target:

* 300 iterations / 4 per group = 75 groups total.
* If you run `N_GROUPS = 1` per tick → ~75 ticks.
* If you run `N_GROUPS = 2` per tick → ~38 ticks.
* If you keep “6 iterations per tick” budget, you could do 1 full group (4 iters) + 2 extra horizontal sweeps (2 iters) to bias flattening.

### Core invariant (why we can delete the claim mechanism)

In each sweep direction, **each destination cell can only receive mass from one source**:

* Down: only from above
* Up: only from below
* Right: only from left
* Left: only from right

So you never have the “multiple sources overfill one destination in the same iteration” case that motivated the claim mechanism.

This lets you:

* remove all `claimScoreForDestNeighbor` / `isWinningSourceForDest` work entirely, and
* increase throughput without needing a correction pass.

---

## 5) Exact per-sweep flux rules

You can implement this as either:

* **(A) four specialized flux shaders** (simplest, fastest), each writing only one `fluxDir` buffer, or
* **(B) one shader with a push-constant direction** (code reuse, slightly more branching)

I’ll specify (A) with one reusable `fluxDir` buffer to reduce bandwidth.

### Common gating rules (applies to all sweeps)

For a cell `loc` to emit flow:

1. `mat = readMaterialGlobal(loc)` must be a pressure-liquid (same as today).

2. `isSupported(loc, mat)` must be true **for all sweeps** (including down), to preserve CA free-fall look. This matches your current implementation behavior: pressure solver returns early if not supported.

3. `srcMass = readMassGlobal(loc)` > 0.

4. Destination material must be `0` (empty) or `mat` (same liquid). Otherwise no flow.

5. Apply `MIN_FLOW` threshold (keep `MIN_FLOW=16` initially).

---

### 5.1 Down sweep (vertical packing)

Goal: pack mass downward toward the stable distribution (your current approach). This keeps “organic piling” and reduces “floating partials” once supported.

For `dest = loc + (0, +1)`:

* If out-of-bounds, emit 0 (you currently have unreachable “below out of bounds ⇒ flowDown = remaining” due to support gating; keep consistent).
* If `destMat` is not `0` and not `mat`: no flow.
* `destMass = (destMat==mat) ? readMassGlobal(dest) : 0`

Compute desired mass in destination using **equilibrium** stable-state:

`desiredBelow = min(stableState(srcMass + destMass, MAX_COMPRESS_EQ), M_EQ_MAX)`

If `desiredBelow > destMass`:

* `proposed = desiredBelow - destMass`
* `capacity = M_CAP_MAX - destMass`
* `flow = min(proposed, min(srcMass, capacity))`

Else `flow=0`.

**Important**: use `M_EQ_MAX` and `MAX_COMPRESS_EQ` in `stableState`, but cap by `M_CAP_MAX` for capacity.

Keep stableState shape the same as current (just replace `M_MAX` with `M_EQ_MAX`). Your current stableState uses `maxCompress = M_MAX - M_FULL` and returns a target mass for the lower cell.

---

### 5.2 Right sweep (horizontal leveling, head-driven)

Destination: `dest = loc + ( +1, 0 )`

Rules:

* If `dest.x >= WIDTH`: flow=0
* If `destMat` is not `0` and not `mat`: flow=0
* If `destMat == 0`: require `allowLateralIntoEmpty(srcLoc, destLoc, mat)` (see §6 below). This preserves your anti-thin-film intent (you already gate empty-target flows).

Compute:

* `srcHead = headSum(loc, mat)`
* `dstHead = (destMat==mat) ? headSum(dest, mat) : 0u`
* `dp = int(srcHead) - int(dstHead)`
* If `dp <= 0`: flow=0
* Else:

  * `proposed = uint(dp) / H_HEAD_DIV`
  * `capacity = M_CAP_MAX - destMass` (destMass=0 if empty)
  * `flow = min(proposed, min(srcMass, capacity))`
  * If `flow < MIN_FLOW`: flow=0

This replaces `diff = remaining - mN` that you currently use for horizontal flow.

---

### 5.3 Left sweep (horizontal leveling, head-driven)

Identical to right sweep, but `dest = loc + (-1, 0)` and neighbor checks use `x > 0`.

---

### 5.4 Up sweep (vertical decompression)

Goal: remove excessive compression after strong horizontal moves and restore equilibrium-ish packing.

Destination: `dest = loc + (0, -1)`

Rules:

* Only attempt up-flow if `srcMass > M_FULL` (same as you do now).
* If `destMat` is not `0` and not `mat`: flow=0
* `destMass = (destMat==mat) ? readMassGlobal(dest) : 0`

Compute desired mass to keep in source:

`desiredSelf = min(stableState(srcMass + destMass, MAX_COMPRESS_EQ), M_EQ_MAX)`

If `srcMass > desiredSelf`:

* `proposed = srcMass - desiredSelf`
* `capacityUp = M_CAP_MAX - destMass`
* `flow = min(proposed, capacityUp)`
* If `flow < MIN_FLOW`: flow=0

This matches your current up-flow logic shape but swaps `M_MAX` for `M_EQ_MAX` and capacity clamp for `M_CAP_MAX`.

---

## 6) Empty-target gating: keep it, but tighten the “escape hatch” to reduce thin-film risk

Your current empty-target allowance is:

Allow if any:

1. destination is supported, or
2. concavity (`Ndest>=2`), or
3. below destination is empty (escape hatch; thin-film risk).

If you now enable head-driven strong lateral flows, rule (3) becomes much more likely to create floating sheets, because deep cells can push sideways into empty over shallow regions.

### Recommended updated rule

Keep (1) and (2), and replace (3) with a “spill from surface only” rule:

**Allow into empty if:**

* dest is supported
  OR
* dest is concavity (`>=2`)
  OR
* dest is unsupported AND below dest is empty **AND source cell is a surface cell** (i.e., above source is empty / not same liquid)

Definition:

* `isSurface(src) = (src.y == 0) ? true : (readMaterialGlobal(src + (0,-1)) != mat)`

Rationale:

* If you’re spilling into an unsupported empty cell, it should be coming from the surface (like water spilling over a lip), not from deep interior pressure.

This preserves your intent of allowing growth over shallow regions without turning everything into a thin film. It also aligns with your internal note that relaxing edge rules for speed may otherwise require a compaction pass later.

If you want to be even more conservative, you can also add:

* require `flow >= M_FULL/4` for spill into unsupported empty (so tiny trickles don’t paint the air)

---

## 7) Apply pass: simplest, deterministic, single-source material resolution

### Option A (recommended): write a dedicated apply per direction

Because each sweep direction has only one inflow source, apply is simpler than your current multi-direction apply (which currently chooses incoming type by fixed priority because claim tends to keep inflow single-sourced).

For each direction, you know exactly where inflow comes from:

* Down sweep: inflow from above
* Up sweep: inflow from below
* Right sweep: inflow from left
* Left sweep: inflow from right

So material resolution is deterministic and cheap.

Apply formula:

`newMass = oldMass + inflow - outflow`
Clamp:

* `newMass = clamp(newMass, 0, M_CAP_MAX)`

Material:

* If `oldMat == 0` and `newMass > 0`: `newMat = sourceMat` (the known neighbor)
* Else if `newMass == 0`: `newMat = 0`
* Else keep `oldMat`

As in your current code, if `oldMat` is not pressure-liquid, force `oldMass=0` before math (coherence).

Updated flag:

* set “updated” if (`newMat != oldMat`) OR (`abs(newMass-oldMass) >= MIN_FLOW`)

Chunk state updates:

* keep the same pattern as today: set `CHUNK_FLAG_SIM_ACTIVE` and `CHUNK_FLAG_RENDER_DIRTY` when updated. (You already do this in pressure_apply via `updateChunkState`.)

### Option B (minimum code churn): keep existing `pressure_apply.comp`

You *can* keep the existing apply shader if you keep the 4 flux buffers and ensure only one is non-zero per sweep. But that is more bandwidth and you must be careful to clear unused flux buffers each sweep to avoid “stale flux” reuse. (Also you still clamp to `M_MAX` today, so you’d need to replace that with `M_CAP_MAX` anyway.)

Given you want “simple, performant”, I recommend Option A.

---

## 8) Chunk sleeping integration

You already have:

* `CHUNK_FLAG_LIQUID_ACTIVE` set by `board_update.comp` by scanning **supported** pressure-liquid cells and marking activity if neighbor empty or neighbor mass differs enough.
* pressure passes only run where `shouldProcessChunk()` returns true (chunk active or halo neighbor).

This model can stay.

### One important adjustment for head-driven flow

Because head-driven flow can produce motion even when local mass differences are small, you may need to slightly broaden the “liquid active” detection to avoid chunks sleeping while there is still a surface slope.

Two low-risk options:

1. **Mark LIQUID_ACTIVE if any supported pressure-liquid cell is a surface cell** (above is empty).
   This tends to keep the pool surface region active, which is where head gradients exist.

2. **Mark LIQUID_ACTIVE if any supported pressure-liquid cell has different “local column height” vs left/right**, computed by a small upward scan (count contiguous same-liquid cells above up to `HEAD_MARK_MAX`, e.g. 16). If `abs(countL - countR) > 0`, slope exists → keep active.

Option (1) is cheaper.

This is specifically to avoid a case where “mass differences are too small to trip the current threshold” but head differences still matter. Your internal report notes that liquid-active marking tweaks didn’t materially change the metric for the current solver, but with a different driving term it becomes relevant again.

---

## 9) Parameter set to start with (to reach “200px wide pool flattens in ~300 pressure iterations”)

You asked for ~200 px wide pool to flatten in ~300 iterations (pressure iterations). Your current solver did ~1800 iterations and still had range ~20, so you need a much bigger per-iteration “reach”.

Start with:

### Mass/capacity

* `M_FULL = 4096` (unchanged)
* `M_EQ_MAX = 4352` (unchanged equilibrium compress, +256)
* `M_CAP_MAX = 6144` (transport capacity, +2048)

### Stable-state

* Keep your current stableState formula shape, but use `MAX_COMPRESS_EQ = M_EQ_MAX - M_FULL`, and clamp stableState outputs to `M_EQ_MAX` (not `M_CAP_MAX`). Current shape is shown in `pressure_flux.comp` today.

### Horizontal head flow

* `HEAD_MAX_CELLS = 64`
* `H_HEAD_DIV = 4`
* `MIN_FLOW = 16` (unchanged)

### Iteration scheduling

Replace “6 identical iterations” with **groups**:

* Start with `N_GROUPS = 2` per tick → 8 pressure iterations/tick.

  * If you target 300 pressure iterations: that’s ~38 ticks.
* If performance is too high, drop to `N_GROUPS = 1` (4 iters/tick) and increase ticks.

You can also bias flattening by doing:

* Down, Right, Left, Up, Right, Left (6 iters/tick)
  This keeps the same iteration budget you already pay (~6), but allocates more to lateral leveling.

---

## 10) Implementation checklist (end-to-end)

1. **Add new constants** to `mass_utils.glsl` (or a new include):

   * `M_EQ_MAX`, `M_CAP_MAX`
   * Keep `M_FULL`, `MIN_FLOW`, `M_EPS`

2. **Rewrite Pass 3 shaders**:

   * Delete claim scoring logic (`claimScoreForDestNeighbor`, `isWinningSourceForDest`) entirely. This is explicitly called out as a throughput cap in your internal review.
   * Implement 4 directional flux shaders (or one with direction):

     * `pressure_flux_down`
     * `pressure_flux_right`
     * `pressure_flux_left`
     * `pressure_flux_up`
   * Each one:

     * early-out if not pressure-liquid or not supported (keep free-fall look)
     * compute single `flow` as specified
     * write to one `fluxDir` buffer

3. **Implement 4 directional apply shaders**:

   * Each reads:

     * input board/mass (current pingpong buffer)
     * `fluxDir` (outflows)
     * neighbor `fluxDir` (inflows)
   * Writes:

     * output board/mass (next pingpong buffer)
   * Clamp final mass to `M_CAP_MAX` (not `M_MAX` as today). Your current apply clamps to `M_MAX` (4352) which would negate the transport-capacity change.

4. **Update pipeline scheduling** in `Application.vsand.lm`:

   * Replace “Pressure Flux 1..6 / Pressure Apply 1..6” with your new sequence:

     * FluxDown+ApplyDown
     * FluxRight+ApplyRight
     * FluxLeft+ApplyLeft
     * FluxUp+ApplyUp
     * repeat per group

5. **Keep chunk masking**:

   * Use your existing `shouldProcessChunk()` (active + halo) logic.
   * Optionally enhance LIQUID_ACTIVE marking as described in §8.

6. **Validate with your existing tooling**:

   * Use the same flatten scenario and height metric already documented.
   * Use mass view overlay to confirm you see transient compression waves (mass temporarily > `M_EQ_MAX` but ≤ `M_CAP_MAX`) and then settling back.

---

## 11) If this is still not enough

Your internal review explicitly mentions that you may need a non-local / multi-scale mechanism if purely local relaxation remains too diffusion-like.
The design above is still local, but it materially increases effective propagation speed by:

* removing claim capping, and
* driving lateral flow with head (column weight), not only local mass diff.

If you still can’t hit the “~300 pressure iterations” target after tuning `M_CAP_MAX`, `H_HEAD_DIV`, and `HEAD_MAX_CELLS`, the next extension would be a **coarse-grid (multiscale) head equalization pass** per chunk-block (e.g., 4×4 supercells) that moves volume over longer distances per iteration. But I recommend implementing the directional head-driven sweeps first because they are much simpler and should already be a large step up from the current solver’s behavior.

---

If you want, I can also write the exact GLSL skeletons for the four flux and four apply shaders (including swizzled indexing patterns and boundary-safe inflow reads) to minimize integration ambiguity.

