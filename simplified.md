Yes. You can get essentially the same “fast pooling/flattening” result with **much less code** by changing *how you implement the same solver*, not necessarily by inventing a fundamentally different fluid model.

Right now the complexity comes mainly from structural duplication:

* 4× direction-specific flux shaders + 4× apply shaders
* flux buffers per direction
* head recompute inserted multiple times in the schedule
* plus “special casing” in each file

You can collapse this to **2 shader sources** (3 if you keep `head` as a separate pass), while keeping the same core idea (mass-per-cell + pressure/head-driven lateral flow + vertical settling).

Below are two viable simplification paths. The first is the one I recommend because it keeps your current behavior quality, reduces code drastically, and makes U-pipe achievable (with predictable iteration scaling).

---

## Option A (recommended): One generic directional “pressure step” shader + one head pass

### What you keep

* Sand/powders: **unchanged CA** (`board_update.comp` as-is).
* Liquids: still **mass-per-cell u16**, same invariants (`mat` + `mass`).
* Fast flattening: still head-driven horizontal flow.
* Chunk sleeping: same model (process active chunks + neighbor halo band).

### What you remove

* All 4 flux shaders and all 4 apply shaders.
* All flux buffers (Down/Up/Left/Right).

### What you add

* **One** generic shader: `pressure_step.comp`
* Keep `pressure_head.comp` (or compute head in `pressure_step`, but that’s usually slower and doesn’t really reduce complexity much).

This reduces your “pressure liquid system” to **two shader source files**:

1. `pressure_head.comp`
2. `pressure_step.comp`

#### Why this works

Your current sweep solver works well because each sweep direction is **1D along an edge** and avoids the “many sources to one destination” conflict.

That property lets you compute the new mass **without writing any flux buffer**:

For one sweep direction `dir`, each cell only interacts with:

* its neighbor in direction `dir` (outflow), and
* its neighbor in direction `-dir` (inflow)

So each cell can update itself as:

`m_new = m_old + inflow_from_neighbor(-dir) - outflow_to_neighbor(dir)`

and both inflow/outflow can be recomputed deterministically from the same old state.

Global conservation still holds because for every edge, the amount one side subtracts is exactly what the other side adds (computed from the same inputs).

---

## Detailed algorithm for Option A

### Data / resources

Fine grid (same size as your board):

* `BoardIn`, `BoardOut`: `uint8 matId`
* `MassIn`, `MassOut`: `uint16 liqMass` (0 for non-pressure liquids and non-liquids)
* `Chunks`: chunk flags (at minimum: sim-active + render-dirty + liquid-active)

Extra buffer:

* `Head`: `uint32` per cell (fine grid)
  Hydrostatic “head sum” for **contiguous same-liquid columns**, exactly what you already compute.

Constants (start with your current tuned set):

* `M_FULL = 4096`
* `M_EQ_MAX = 4352` (equilibrium compress cap)
* `M_CAP_MAX = 6144` (transport cap; can remain)
* `MIN_FLOW = 16`

Material properties needed:

* `isPressureLiquid(matId)` (flag)
* optional: per-liquid tunables (`H_DIV`, etc.) but you can start global.

---

## Pass 1 — board_update (unchanged sand CA)

Keep your sand/powder CA unchanged.

For pressure-liquids you have 2 choices:

### A1) simplest integration (recommended)

* Let `board_update` continue to swap materials as today **only because sand needs to displace water**.
* Ensure: when a swap happens and either side is pressure-liquid, you swap `Mass` too, and enforce invariants (non-liquid → mass=0).

Do **not** rely on `board_update` to make liquids flow. Liquids flow is handled entirely by `pressure_step`.

This keeps the visual look stable and avoids “two solvers fighting.”

---

## Pass 2 — pressure_head (one pass)

Compute `Head[x,y]` per cell, per liquid type:

For each `x` column:

* scan from top to bottom
* keep a running sum `s`
* if `(mat is pressure-liquid) AND (mass > 0)` AND the liquid type matches the previous cell in the column:

  * `s += mass`
  * `Head[x,y] = s`
* else:

  * reset `s = 0`
  * `Head[x,y] = 0`

Notes:

* If you support multiple pressure liquids, `Head` must reset on type change (you already do).
* `Head` for empty / non-pressure-liquid is 0.

This pass can stay column-based (one invocation per x or per block of x). It’s conceptually simple.

---

## Pass 3 — pressure_step (repeat many times)

This is the entire “solver iteration” now.

It takes a **direction parameter** `dir ∈ {DOWN, UP, LEFT, RIGHT}` (push constant or specialization constant).

It reads from `BoardIn/MassIn` and writes `BoardOut/MassOut` (ping-pong).
No other intermediate buffers.

### 3.1 Invariants enforced at start of the step

For each cell:

* If `mat` is not pressure-liquid: treat `mass = 0` for computations.
* If `mat` is pressure-liquid and `mass == 0`: treat as empty for computations (or repair to `M_FULL` only if you have a guaranteed source; otherwise you’re minting mass).

### 3.2 Eligibility / gating rules

You can now drop the old “water falls like sand” requirement, but you still want to avoid ugly mid-air spreading. Use a **simple falling gate**:

Define `unsupported` for liquids:

* `unsupported` if `below cell is empty (mass=0 and mat=0)`
  (treat non-pressure gas/steam as empty if you want)

Then:

* In DOWN steps: always allowed
* In LEFT/RIGHT steps:

  * allowed only if NOT `unsupported`
* In UP steps:

  * allowed only if NOT `unsupported`

This preserves “water doesn’t smear laterally while falling,” without needing CA-style falling.

If you want even simpler: allow lateral even when unsupported but with a very small factor; it will look more “misty.”

### 3.3 Computing edge flow for this step

For a cell `C` at `(x,y)` and its neighbor `N = C + dir`:

You will compute:

* `out = Flow(C -> N)`
* `in  = Flow(C - dir -> C)` (same formula, just different source)

Then:

* `m_new = clamp(m_old + in - out, 0..M_CAP_MAX)`
* Update `mat_new`:

  * if `m_new == 0`: `mat_new = 0`
  * else if `mat_old != liquidType`: `mat_new = sourceType` (for this direction step, the inflow can only come from one known neighbor)

Because each directional step only has **one possible inflow source**, material resolution is deterministic and trivial.

---

## Flow formulas per direction

### DOWN step

Goal: fast settling / pooling.

Let:

* `srcMat = mat[C]`, `srcMass = mass[C]`
* If not pressure-liquid or `srcMass==0` → out=0

Destination:

* if out of bounds: **delete** (out = srcMass) (your current behavior)
* else if `dstMat` is solid or different pressure-liquid type: out=0
* else `dstMass = (dstMat==srcMat ? mass[N] : 0)`

Use your stable-state equilibrium function (same shape you already implemented) but with two caps:

* `desiredBelow = stableStateEq(srcMass + dstMass)` clamped to `M_EQ_MAX`
* `out = clamp(desiredBelow - dstMass, 0..srcMass)`
* also clamp by capacity: `out ≤ (M_CAP_MAX - dstMass)`
* apply `MIN_FLOW`: if `out < MIN_FLOW` then `out = 0`

This packs quickly and stays stable.

### LEFT / RIGHT steps

Goal: flatten quickly over long distance.

Prereqs:

* only if not unsupported (recommended)
* only if `dstMat == 0` or `dstMat == srcMat`

If `dstMat == 0`, apply your “no thin-film” rule. Keep it simple:

**Allow lateral into empty if ANY:**

* destination is supported (below not empty), OR
* destination is concave (≥2 liquid neighbors of the same type), OR
* source is surface cell (above is empty) and you want spill-over behavior

Then compute pressure drive using head:

* `srcHead = Head[C]`
* `dstHead = (dstMat==srcMat ? Head[N] : 0)`
* `dp = srcHead - dstHead`
* if `dp <= 0` out=0
* else:

  * `out = dp / H_DIV` (start with `H_DIV = 4`)
  * clamp by `srcMass` and `(M_CAP_MAX - dstMass)`
  * apply `MIN_FLOW`

This reproduces your current “fast flattening” behavior.

### UP step (critical for U-pipe)

This is where most “tall U-pipe” failures come from.

If your UP step is only “relieve compression toward equilibrium,” it tends to keep extra volume as compression instead of translating it into **height**.

To make U-pipe work, the UP step must act like:

> “any excess volume should become surface height, not persistent compression.”

Use this UP rule:

Prereqs:

* only if not unsupported
* destination must be empty or same liquid
* source must be pressure-liquid

Let:

* `dstMass = (dstMat==srcMat ? mass[N] : 0)` else 0 if empty
* Define how much “excess” a cell is willing to push upward this iteration:

**Excess definition for U-pipe-friendly behavior**

* `excess = max(0, srcMass - M_FULL)`

Not `srcMass - stableStateEq(...)`.

Then decide whether the above cell can be filled:

* If `dstMass >= M_FULL`: out=0 (already full; no need to push height)
* Else:

  * `need = (M_FULL - dstMass)`
  * `out = min(excess, need)`

This does two important things:

1. It prevents “infinite compression”: excess above M_FULL will move upward.
2. It ensures the rising column grows by creating **full cells** rather than leaving a tall column of low-mass cells.

Clamp by capacity:

* if you keep the “fill-to-full” rule (`need`), capacity is naturally respected
* still clamp to `(M_CAP_MAX - dstMass)` if you allow `dstMass > M_FULL` in special cases

Apply `MIN_FLOW`.

#### What this implies for U-pipe speed

This UP rule propagates the rising front at approximately:

* **~1 cell of rise per UP iteration** (worst case)

So a 500px U-pipe typically needs on the order of **hundreds of UP iterations** to fully equalize, unless you run multiple UP steps per tick.

That’s expected for a purely local solver.

If you want faster-than-1-cell/iter rise, you need a non-local vertical pass (Option B below).

---

## Scheduling recommendation for Option A

You already have a multi-sweep schedule; you can simplify it to something like:

Per tick:

1. `board_update` (sand)
2. `pressure_head`
3. Repeat `G` times:

   * DOWN
   * RIGHT
   * LEFT
   * UP

Where:

* `G` = 1..4 depending on performance
* If U-pipe is a priority, give UP more weight, e.g.:

  * DOWN, RIGHT, LEFT, UP, UP (or UP×k)

Key point: with the UP rule above, more UP steps directly translates to faster U-pipe equalization.

---

## Chunk sleeping compatibility (soft sleep only)

This stays simple:

In `pressure_step`, mark the chunk updated/active if:

* `mat` changed (empty↔liquid), OR
* `abs(m_new - m_old) >= MIN_FLOW`

Write-back rule remains:

* if a chunk was processed, it must write coherent outputs so the ping-pong buffers don’t desync.

No additional complexity required.

---

# Option B (more radical): Add a single “vertical compaction” scan pass and drop UP entirely

If you want **500px U-pipe to equalize quickly without hundreds of UP iterations**, you need to break the “1 cell per iteration” vertical propagation limit.

The simplest way (still quite small in code) is:

### Vertical compaction pass (per column)

For each x column, for each vertical segment separated by solid cells:

1. compute total liquid mass of that segment (for a given liquid type)
2. rewrite the segment bottom-up as:

   * as many full cells as possible (`M_FULL`)
   * one partial cell at the top if remainder exists
   * empties above that

This makes vertical propagation effectively “infinite speed” (in one pass) within that segment.

### Horizontal head-driven pass (still needed)

Horizontal redistribution still uses the head-driven method.

### Tradeoffs

* Vertical motion becomes “snappy” (less organic). Often acceptable if your goal is “fast flattening.”
* This pass is harder to make chunk-sleep-perfect if you literally scan entire columns always; you’ll want an “active column” bitset/list to avoid touching the whole world.

This option can be fewer conceptual steps, but it can create visible “teleport settling” unless you add smoothing.

---

# What I would do given your constraints

1. Keep your current head-driven idea (it already solved flattening well).
2. **Refactor into Option A**:

   * head pass + one generic directional pressure step shader (no flux buffers, no apply shaders)
3. Change UP logic to “push excess above M_FULL upward to fill to full.”
4. Increase the number of UP steps when you want U-pipe to converge.

This yields:

* Much less code (2 shader sources + shared utils)
* Same kind of pooling/flattening behavior
* U-pipe works reliably; its convergence time becomes predictable (mostly proportional to height unless you later add Option B’s compaction pass).

If you paste the current U-pipe test scenario description (dimensions, where the pipe is, initial fill levels, number of ticks run), I can tell you what to expect from Option A (iteration count estimates) and exactly which schedule bias (how many UP steps vs horizontal steps) is likely to make it converge in your target time.

