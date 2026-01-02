> Companion / implementation notes: see `new_algorithm_answer.md` (what differs from this design doc in current VSand code).

## State model and invariants

### Fine grid (materials + liquids)

**Per fine cell `(x,y)`**

* `mat[x,y] : uint8`
  `0` means “empty space” (not necessarily vacuum; vacuum is an air-field state).
* `liqMass[x,y] : uint16 or uint8` (recommend `uint16` if you can afford it; `uint8` works but gives you less headroom)
  Meaningful only when `mat` is a **pressure-enabled liquid**.

**Liquid constants**

* `M_FULL` = nominal “1 cell of liquid” (e.g. 240 if using u8).
* `M_MAX`  = max allowed mass in one cell (e.g. 255 for u8).
  This is **slight compressibility** → “pressure feel”.
* `M_EPS`  = tiny threshold below which we treat as zero (e.g. 1–2).

**Invariants you must enforce every tick**

1. **Coherence**

   * If `mat` is **not** a pressure-enabled liquid → `liqMass = 0`.
   * If `liqMass == 0` → `mat` must be `0` (empty) **or** a non-liquid material.
2. **Conservation (liquids)**
   For each liquid type, `Σ liqMass` is conserved except:

   * out-of-screen deletion
   * explicit reactions that define a conversion
   * explicit user/world edits that overwrite cells
3. **Sleeping safety (soft sleep now, future freeze later)**
   Any pass that runs on a chunk must leave that chunk’s **two ping-pong copies** consistent when the chunk becomes inactive (so sleeping does not create/destroy anything due to “reading the other buffer next frame”).

### Coarse grid (air simulation)

Let coarse cell size be `R×R` fine cells (typical: `R=4`).

**Per coarse cell `(i,j)`**

* `rho[i,j] : float16/float32` air amount/density (supports “void/vacuum” as `rho=0`)
* `v[i,j] : vec2` air velocity
* `block[i,j] : uint8` obstacle mask (airtight caves)
* Optional tracers: `steam[i,j]`, `smoke[i,j]`, etc. (recommended if you want “steam” without a separate fine-grid gas)

**Invariants**

* If `block==1` then treat the cell as non-participating (or participating but with zero velocity and no transport across its faces).
* Airtightness is enforced at **faces**: air flux across a face is allowed only if both adjacent cells are unblocked and the face is not considered sealed.

---

## Buffers and resources

### Core

* `BoardA`, `BoardB` (fine) — `mat`
* `MassA`, `MassB` (fine) — `liqMass` (pressure-enabled liquids only)
* `ChunkStateBoard` — per fine chunk activity/dirty bits (existing)
* `ChunkStateLiquid` — per fine chunk activity/dirty bits (new)
* `MaterialProps` — per material: `phase`, `density`, `runoff`, plus new couplings (`windCoupling`, `waterCoupling`, etc.)
* `ReactionRules` — extended rules (details below)

### Liquid pressure solver working buffers (reused per iteration)

* `Flux*` (fine) — outflow per cell per direction; either:

  * 4 buffers `FluxDown/Up/Left/Right`, or
  * 1 buffer storing 4 directional flows (packed)
* Optional (if you want cheaper coupling): `LiquidVel` (fine) — net flow vector per cell, computed during apply

### Air

* `AirRhoA/B`, `AirVA/B` (coarse)
* `AirBlock` (coarse)
* Optional `SteamA/B`, `SmokeA/B` (coarse)
* `ChunkStateAir` (coarse)

### Forces

Two viable layouts (pick one):

* **Option 1 (explicit force buffer)**: `ForceField` (fine) storing `(fx,fy)` as int8/int16
* **Option 2 (no force buffer)**: movement pass directly samples air/liquid fields on demand

---

## Simulation passes (per tick)

A practical ordering that keeps the “sand fall look” and avoids the previous mass bugs is:

1. **Fine movement (CA swap)**
2. **Mass-aware reactions**
3. **Liquid pressure relaxation** (N iterations)
4. **Build/update coarse air blockers**
5. **Coarse air update** (K substeps)
6. **Compute forces** (or fold into step 1 next tick)

Below are the detailed algorithms and edge cases per pass.

---

## Pass 1 — Fine movement CA (keep VSand fall as-is; add force bias; swap liquid mass)

### Purpose

* Preserve the current visual motion: sand/grains fall with randomness and runoff.
* Keep “liquid falling in air” looking like today as much as possible.
* Move *materials* via swap CA.
* Ensure liquid mass follows the cell content (no creation/destruction).

### Inputs

* `BoardIn`, `MassIn`
* `ForceField` (if using explicit forces; otherwise sample `AirV` / `LiquidVel` directly)
* `MaterialProps`
* `ChunkStateBoard` (active chunks + border band)

### Outputs

* `BoardOut`, `MassOut`
* Update `ChunkStateBoard` updated/active bits

### Algorithm (per active cell)

Use the existing VSand “choose → accept → swap” exactly for movement decisions **based on `mat`** (not on mass), with these additions:

#### A) Swap liquid mass with the material

When the CA swaps two cells `(a ↔ b)`:

* Always swap `mat[a] ↔ mat[b]`
* Additionally:

  * If either side is a pressure-enabled liquid: swap `liqMass[a] ↔ liqMass[b]`
  * If after swap, `mat` is not a pressure-enabled liquid → force `liqMass=0`
  * If after swap, `mat` is a pressure-enabled liquid and `liqMass==0` → set `liqMass=M_FULL` **only if** the cell became liquid due to swap from empty (i.e., it’s a true moved liquid cell).
    Otherwise you risk silently “minting mass”. The safe rule is: **mass must come from somewhere**, so for swap you only move existing mass.

#### B) Out-of-screen deletion (keep current behavior)

If a move targets outside the simulation bounds:

* Set the moving cell to empty: `mat=0`, `liqMass=0`
* This deletes the mass/material exactly like today.

#### C) Force bias (secondary; must not dominate)

If you apply wind/explosion/water forces to particles:

* Treat force as an **extra bias** on direction choice, not as a replacement.
* Recommended pattern:

  * Let the existing CA decide normally.
  * With probability `pForce` (depends on material’s `windCoupling/waterCoupling` and force magnitude), *attempt* an additional move candidate in the force direction **only if** it passes the normal legality checks (non-static target, density rule, etc.).
  * Resolve conflicts using the same accept/swap scheme (so you don’t break the CA’s parallel safety).

This keeps the old randomness dominant and prevents “deterministic marching”.

### Edge cases

* **Partial-mass liquid cells in air**: movement pass treats them like a liquid cell; mass moves with it. The pressure pass will later “clean up” partial splatter (surface tension rules).
* **Sleeping must not desync**: if a chunk runs this pass, it must write back even if nothing changed (so next frame reading the other ping-pong buffer doesn’t accidentally resurrect old material).

---

## Pass 2 — Mass-aware reactions (conservative conversions; handles half-mass fluids correctly)

### Purpose

* Apply chemistry/transform rules without creating/destroying mass unless the rule explicitly does.
* Correct handling of **partial liquid mass** conversions (your “half-mass fluid transforms” point).
* Avoid the classic bug: converting a fluid cell into a solid by simply zeroing mass (material loss + air pockets).

### Inputs

* `BoardAfterMove`, `MassAfterMove`
* `ReactionRules`
* `MaterialProps`
* Optionally `AirRho/Steam` if some reactions create gas-tracers instead of fine-grid gas particles
* `ChunkStateBoard` or a separate `ChunkStateReact` (you can fold into board dirty)

### Outputs

* `BoardAfterReact`, `MassAfterReact`
* Optional modifications to air tracers (`steam`, etc.)
* Chunk updated flags

### Reaction rule format (recommendation)

Extend your current “(A,B) → C” table into explicit *conservative* rules:

**Unary rule (single cell):**

* `A → A'` with ratio `1:1` (mass preserved)
* Optional probability / conditions

**Binary rule (two adjacent cells):**

* `(A, B) → (A', B')`
* Consumption/production is cell-wise, ideally still `1:1` per cell for simplicity.

You can keep it simple and still satisfy conservation by adopting these restrictions:

#### Rule class 1: phase-preserving conversion (safe for partial mass)

If `A` is a pressure-enabled liquid and `A'` is a pressure-enabled liquid (or a gas tracer):

* Convert **the same mass**:

  * `m = liqMass[cell]`
  * Set `mat= A'`
  * Set `liqMass = m` (or move to tracer if gas)
    This directly solves: “what happens when we transform a half mass fluid?”
    → it becomes half-mass product, no creation/destruction.

#### Rule class 2: liquid ↔ air-tracer (recommended for “steam”)

If `water → steam`:

* Let `m = liqMass[cell]`
* Remove liquid: `mat=0`, `liqMass=0`
* Add to coarse tracer: `steam[coarse(cell)] += m`
  This preserves amount in closed systems (steam can’t leave airtight caves unless the air solver lets it).

For condensation `steam → water`:

* Convert tracer amount back into fine liquid mass:

  * Choose deposition cell(s) in the corresponding coarse block (details below)
  * Decrease `steam` by the deposited amount
  * Increase water `liqMass` in fine cells

**Deposition edge case (important):**
You must avoid creating “floating partial water” everywhere. Use a deterministic preference:

1. deposit into the lowest available empty fine cell in the block (or lowest reachable within a small local search)
2. if blocked, deposit into a neighboring block with lower cell
3. if nowhere, keep as steam (no loss)

This prevents the “mist turns into scattered droplets” look unless you want it.

#### Rule class 3: producing a solid from liquid (must not mint matter)

Solids in VSand are discrete full cells. So you cannot turn `m < M_FULL` liquid into a solid cell without creating material.

Use one of these conservative policies:

* **Policy S1 (thresholded solidification):**
  Allow solid creation only if `liqMass >= M_FULL`.
  If `liqMass < M_FULL`, reaction does not create solid (it can still change temperature/metadata if you have it).

* **Policy S2 (solidification with displacement):**
  When a cell must become solid (e.g., reaction dictates), displace liquid mass `m` to neighbors before solidifying:

  * Attempt to push `m` into adjacent cells that can accept the same liquid (prefer up, then sideways, then down)
  * Allow neighbors to temporarily compress up to `M_MAX`
  * If after redistribution there is leftover mass that cannot be placed, cancel or delay the solidification (conservative).
    This directly addresses the “draw/insert released air” style artifacts caused by deleting liquid during solidification.

### Reaction execution without double-applying

Binary reactions can conflict (two neighbors both decide to react with the same cell). Use one of:

* a “proposal/accept” handshake like movement, but for reactions
* checkerboard update (even/odd cells react this pass)
* deterministic priority (e.g., only react “right and down” directions in one pass, then alternate)

Any of these prevents the same mass from being consumed twice.

---

## Pass 3 — Liquid pressure relaxation (fast flattening + pressure; conservative; fixes the low-mass pool bug)

### Purpose

* Rapidly level liquid surfaces (multi-iteration).
* Produce “pressure feel” (U-tube balancing, pushing upward when compressed).
* Remain conservative and chunk-sleepable.
* Avoid the previous failure mode: large pools becoming a wide region of low-mass “film”.

### Inputs

* `BoardAfterReact`, `MassAfterReact`
* `MaterialProps`
* `ChunkStateLiquid` (active chunks + border band)

### Outputs

* `BoardAfterLiquid`, `MassAfterLiquid`
* Optional `LiquidVel` (fine) for coupling
* Update `ChunkStateLiquid`

### Key design choice: **flux-based** update (not single-direction handshake)

The bug you reported (pool turns into many low-mass cells) is strongly encouraged by “one neighbor per step” and overly diffusive lateral equalization. The robust approach is:

Each relaxation iteration is **two subpasses**:

1. compute outflows (fluxes) for all cells
2. apply fluxes to update masses (conservative gather)

This allows *down + sideways + up* to happen in one iteration, and it converges to a stable “hydrostatic” distribution much more reliably.

---

### Pass 3.1 — Compute liquid fluxes (one iteration)

**Working buffers used**

* `FluxDown/Up/Left/Right` (reset to 0 each iteration)

**For each fine cell `c`**
If `mat[c]` is not a pressure-enabled liquid: all flux=0.

Let:

* `T = mat[c]` liquid type
* `m = liqMass[c]`

#### Step A: determine which neighbors are eligible

For each direction `dir` to neighbor `n`:

* If `n` is out of bounds:

  * treat as eligible sink **only for downward** if you want “water can flow out of the world”
  * otherwise ineligible
* If `mat[n] == 0` (empty) → eligible
* If `mat[n] == T` → eligible
* Else (solid or different liquid) → ineligible for this liquid-relax pass
  (different liquids are handled by the CA swap stage and/or separate relax per liquid if you later choose that)

#### Step B: free-fall suppression (visual requirement)

Define `supported`:

* supported if the cell below is solid or any liquid cell (any type) with nonzero mass.
* unsupported if below is empty (and in bounds).

If `unsupported`:

* do **not** compute sideways or upward fluxes (prevents mid-air expansion/smear)
* and typically do **not** compute partial downward dripping here (keep falling look controlled by CA movement)

  * optional: allow “drip” only for very small masses (aesthetic choice)

This preserves your current “water falls nicely like sand but smoother” feel.

#### Step C: gravity packing (fixes pool underfilling)

For supported cells, compute downward flux first (highest priority).
Use a stable-state function that gives slight compressibility:

Let `mb = mass[below]` if below is same liquid, else `0` if below empty, else ineligible.

Compute `desiredBelow = StableMass(m + mb)` where:

* `StableMass(total)` returns:

  * `M_FULL` when total ≤ `M_FULL`
  * increases above `M_FULL` gradually up to `M_MAX` to model compression
  * behaves like the classic slightly-compressible CA fluids

Then:

* `flowDown = clamp(desiredBelow - mb, 0, m, capacityBelow)`
* subtract from remaining `m`

This is what prevents the “pool of low mass water”: it forces mass to pack downward and saturate lower cells before mass stays spread up top.

#### Step D: lateral leveling (fast flattening but not film-spread)

Compute left/right fluxes next, but with **surface tension / cohesion gating**:

* If neighbor is **same liquid**: allow equalization flow
* If neighbor is **empty**: allow only if it looks like “filling a basin” rather than “spreading film”

A simple, effective cohesion rule that is still local:

* Let `Ndest = number of 4-neighbors of dest that are liquid type T` (excluding source)
* Allow flow into an empty dest only if `Ndest >= 2`
  (meaning you are filling a concavity / widening an existing pool, not creating isolated thin tendrils)

Then compute flow:

* `flowSide = clamp((m - mN) / K, 0, mRemaining, capacityN)`
  with `K` tuned for speed (smaller K spreads faster; start at 4)

This has two critical visual benefits:

* inside a pool (dest already liquid), leveling is fast
* at the edge, water doesn’t “paint a thin wet film” across flat ground, which is what caused your underfilled pool + air-release artifacts

#### Step E: upward pressure relief (U-tubes)

If `m > M_FULL`, allow upward flux (only to empty or same liquid above):

* compute how much is “excess” above a stable distribution:

  * `flowUp = clamp(m - StableMass(m + mAbove), 0, mRemaining, capacityAbove)`
    This is the minimal, cheap mechanism that produces communicating-vessel balancing without a global solve.

#### Step F: write fluxes

Write the computed outflows into `Flux*` buffers.

Also compute `maxFluxInCell` (for chunk activity), but do not store per-cell unless needed:

* chunk is “updated” if any `flow > ε` anywhere in the chunk.

---

### Pass 3.2 — Apply liquid fluxes (one iteration)

**For each fine cell `c`**
If `mat[c]` is not a pressure-enabled liquid and it is empty, it can still receive inflow.

Compute:

* `out = FluxDown[c] + FluxUp[c] + FluxLeft[c] + FluxRight[c]`
* `in = FluxUp[below] + FluxDown[above] + FluxRight[left] + FluxLeft[right]`
  (only from neighbors that are valid and eligible)

Then:

* `newMass = clamp(oldMass + in - out, 0, M_MAX)`
  (`clamp` should never clip if flux compute respected capacities; if it does clip, that indicates a bug)

**Occupancy update**

* If `newMass == 0`: set `mat=0`
* If `newMass > 0` and `mat==0`: set `mat` to the incoming liquid type **T**

**Incoming type resolution**
This is the main tricky edge case with multiple liquids:

* An empty cell could receive inflows from different liquid types in the same iteration.

You need one of these strategies (pick one; all are implementable later):

1. **Run this pressure relax only for one (or few) “pressure liquids”** (e.g., just water)

   * simplest and fastest
   * no conflicts

2. **Run pressure relax per liquid type sequentially** (only on chunks where that liquid exists)

   * deterministic, no type conflict
   * cost scales with number of pressure liquids

3. **Type-claim mechanism for empty cells**

   * each empty cell chooses one incoming type (e.g., max incoming mass)
   * and only accepts inflows from that type; other inflows are rejected
   * to remain conservative, rejected flows must not be subtracted from senders → this requires either a handshake or a second correction pass

For VSand complexity/performance, (1) or (2) is the most realistic first implementation.

**Optional output for forces**
During apply, you can compute a crude liquid velocity:

* `vx ~ (FluxRight[c] - FluxLeft[c])`
* `vy ~ (FluxDown[c] - FluxUp[c])`
  Store into `LiquidVel` (quantized) for later particle coupling.

---

### Optional Pass 3.3 — Surface-tension cleanup / compaction (recommended)

This directly targets:

* “avoid partial mass when not in a pool”
* “pool shouldn’t become a low-mass film”
* the “draw wall releases air around draw” symptom

**Inputs:** `Board/Mass` after the last relax iteration
**Outputs:** adjusted `Board/Mass` (conservative)

Local conservative cleanup rules:

1. **Kill tiny isolated droplets**
   If `mat[c]=T` and `liqMass[c] <= M_EPS`:

* move its mass into the best neighbor cell of type `T` (prefer down, then sideways, then up)
* set `mat[c]=0`, `liqMass[c]=0`

2. **Merge weak edge cells into pool**
   If `mat[c]=T` and `liqMass[c] < M_FULL/4` and `countSameLiquidNeighbors(c) == 1`:

* move all mass to that single neighbor (merge)
* empty this cell

3. **Fill holes aggressively**
   If `mat[c]==0` and it has **3 or 4** same-liquid neighbors `T` (a cavity inside a pool):

* pull mass from the richest neighbor(s) to fill it up to a small threshold
  (this reduces trapped air pockets / checkerboard artifacts)

This pass does not need to be perfect physics; it’s there to enforce the *visual model* of “liquid wants to be contiguous”.

---

## Pass 4 — Build/update coarse air blockers (airtight caves)

### Purpose

* Convert fine solids (and optionally liquids) into a coarse `block[i,j]` mask.
* This is what makes caves airtight and prevents air pressure/velocity from leaking through walls.

### Inputs

* `BoardAfterLiquid`, `MaterialProps`
* Coarse mapping `R`

### Outputs

* `AirBlock[i,j]`
* Mark corresponding `ChunkStateAir` dirty if it changed

### Algorithm (per coarse cell)

For each coarse cell `(i,j)` covering fine rectangle:

* `block=1` if **any** fine cell inside is a blocking material.
  Recommended blockers:

  * all solids (walls, sand, stone)
  * liquids as blockers **if** you want “water seals air” (airtight caves + drowning behavior)
  * do **not** block for empty

This is conservative (prevents leaks) but reduces thin-vent fidelity.

---

## Pass 5 — Coarse air update (pressure + velocity; supports explosions and wind)

### Purpose

* Maintain an air field that:

  * respects obstacles (airtight)
  * can carry impulses (explosions)
  * produces “wind” that can push particles
  * settles quickly (strong damping) so it sleeps well

### Inputs

* `AirRhoIn`, `AirVIn`, `AirBlock`
* Optional tracers `SteamIn`, `SmokeIn`
* Optional `ExplosionEvents` or a sparse “injection” grid

### Outputs

* `AirRhoOut`, `AirVOut`
* Updated tracers
* `ChunkStateAir`

### State update model (simple, compressible, local)

You want something closer to a *damped compressible wave / advection model* than a full incompressible projection.

Use parameters:

* `c` = wave speed (controls how fast pressure propagates)
* `k` = pressure coefficient (equation of state)
* `dV` = velocity damping per step (strong)
* `dRho` = density damping (usually **0** if you want conservation; you can damp toward ambient only if you accept non-conservation)

Let pressure be derived:

* `p = k * (rho - rho0)` where `rho0` is ambient (e.g. 1.0)

#### Substep (repeat K times if needed)

For each unblocked coarse cell:

1. **Apply external injections**

   * Explosions: add outward velocity impulse and/or locally increase `rho`
   * Fans: add directional velocity
   * Vacuum pumps: decrease `rho`

2. **Pressure gradient accelerates velocity**

   * `v += -∇p * dt`
   * When computing ∇p, ignore blocked neighbors; for a blocked face, treat it as no-flux (zero normal component).

3. **Update density from velocity divergence**

   * `rho += -rho * div(v) * dt` (or a linearized version)
   * Again, divergence only across unblocked faces.

4. **Damping**

   * `v *= (1 - dV)`
     (this is the big “sleep fast” knob; it removes long-lived oscillations)

5. **Advect tracers (optional)**

   * `steam`, `smoke` advected by `v` + small diffusion.
   * If you use steam as the product of water boiling, this is where it moves.

### Edge cases

* **Airtightness** is entirely controlled by the face rules. If you accidentally include blocked neighbors in divergence/gradient, you’ll get leaks.
* **Vacuum**: `rho=0` is allowed. Pressure becomes negative, gradients pull air in; this creates suction effects naturally.
* **Conservation**: if you want “closed system keeps amount,” do **not** damp `rho` toward ambient; only damp `v`.

---

## Pass 6 — Coupling forces to particles (wind + explosions + water flow)

### Purpose

* Convert air motion and liquid motion into a gentle, visually plausible impulse on fine-grid materials, while keeping the existing CA look dominant.

### Inputs

* `AirV`, `AirRho` (and derived `p`) on coarse grid
* Optional `LiquidVel` (fine), or recompute a cheap approximation from `liqMass` gradients
* `MaterialProps` (coupling coefficients)

### Outputs

* Either:

  * `ForceField` (fine), or
  * no output (forces applied directly inside movement pass next tick)

### Algorithm (per fine cell)

Compute:

* `wind = sampleBilinear(AirV, x/R, y/R)`
* `blast = -∇p` sampled from coarse (optional, stronger near explosions)
* `waterPush = LiquidVel[x,y]` (if available)

Then:

* `force = windCoeff[mat] * wind + blastCoeff[mat] * blast + waterCoeff[mat] * waterPush`
* clamp force magnitude to a small range (so it remains a bias, not a deterministic driver)
* optionally quantize to int8/int16

### How movement uses the force (conceptual)

In Pass 1’s decision stage:

* treat force as a probability-weighted suggestion:

  * e.g., if `force.x > 0`, increase the chance of choosing right runoff
  * if `force.y < 0`, allow occasional upward movement for very light materials (dust/smoke), but keep sand mostly unaffected

This preserves your “random falling” style and avoids “sand swimming”.

---

## Addressing your listed edge cases explicitly

### 1) “Sleep should not destroy/create material”

Ensure these rules in every pass:

* A pass that runs on a chunk must write a fully coherent output for both board and mass fields for that chunk (even if unchanged), so next frame’s read doesn’t resurrect old data.
* Never apply “damping” to conserved quantities like `liqMass` or `steam` unless you explicitly accept losing amount.

### 2) “What happens when we transform a half mass fluid?”

If the reaction is phase-preserving or liquid→steam-tracer:

* Half-mass in → half-mass out (exact).
  Examples:
* `water(m) → steam(m)` (as tracer)
* `acid(m) → poisonGas(m)` (as tracer)
  No special case required beyond “preserve m”.

### 3) “Avoid partial mass outside pools / surface tension”

Use both:

* flow gating (don’t laterally spread unsupported cells)
* cohesion/concavity rule (don’t flow into empty unless it’s filling a pocket)
* cleanup/merge pass for tiny isolated masses

These are cheap, local, and directly target the ugly “thin film / mist” look.

### 4) “Reactions should not create/destroy unless defined”

* Unary liquid conversions preserve mass by definition.
* If producing solids from liquids, you must use a conservative policy (threshold or displacement) because solids are discrete full cells in your CA.

### 5) The previous “pool of low mass water” bug

Root causes you must avoid:

* overly diffusive lateral equalization into empty space
* single-direction transfers that never fully pack

Fixes built into the above algorithm:

* strong downward packing using `StableMass`
* lateral flow into empty is gated by a concavity / cohesion test
* optional compaction cleanup that merges weak droplets and fills holes

This combination prevents “wide puddle of underfilled cells” and prevents the “air released around draw” symptom because the pool remains a contiguous body rather than a low-mass fog.

---

## Minimal implementation path that still satisfies the requirements

If you want a staged implementation that keeps risk low:

1. Implement **Pass 1** mass swapping + force bias (no liquid solver yet).
2. Implement **Pass 3** (pressure relax) for **water only**, flux-based, with the concavity-gated lateral rule.
3. Add **Pass 2** for unary mass-preserving reactions (water↔steam tracer).
4. Add air solver (Pass 4–5) and couple it via Pass 6.

This yields: sand look unchanged, water levels quickly with pressure, airtight caves, explosions push particles — with conservative mass handling and without the underfilled-pool artifact.
