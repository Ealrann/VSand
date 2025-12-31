### Definitions in VSand terms

* A **“tick”** in your current engine is one `board_update.comp` dispatch.
* `repeatCount` (“speed”) is how many ticks you run per frame. 

For pressure liquids, you want a **batch of relaxation iterations per tick** (each iteration is typically “compute fluxes” + “apply fluxes”, so 2 dispatches per iteration if you use the conservative flux approach).

---

## Recommended iteration counts (starting point)

### Liquids (water, lava, …)

* **Start at 6 pressure iterations per tick** (u8 mass).
* Acceptable range:

  * **4**: usually “good enough” flattening, cheapest
  * **6–8**: visibly fast leveling + U-tube works well
  * **>10**: diminishing returns; mostly wastes GPU unless you have huge flat pools in view

### Gas (inverted solver)

* **Half of liquid** is usually enough: **2–4 iterations per tick**.

### Hard rule

* **Never go below 1 iteration per tick** for liquids that you expect to behave “pressure-like.”
  Otherwise you reintroduce the failure mode where mass doesn’t pack quickly on contact with a surface and you get underfilled “film” behavior.

---

## How it should interact with `repeatCount` (“speed”)

There are two sane policies. Pick one explicitly.

### Policy A — Time-consistent (simplest, best visual consistency)

Keep the same iterations **per tick** regardless of `repeatCount`:

* `iters_liquid_per_tick = 6` (example)
* `iters_gas_per_tick = 3`

Meaning: if the user speeds up the sim (higher `repeatCount`), liquids also get proportionally more relaxation work, so they remain “equally pressure-y” in simulation time. Cost scales with speed.

This is the best match for VSand-as-a-sandbox where higher speed is allowed to cost more.

### Policy B — Budget-capped (keeps GPU time stable at high speed)

Cap the **total** number of pressure iterations per frame, and divide by `repeatCount`:

* `iters_tick = min(base, max(1, floor(capPerFrame / repeatCount)))`

Good default caps:

* **Liquids:** `base=6`, `capPerFrame=24`

  * repeatCount 1–4 → 6 iters/tick
  * repeatCount 8 → 3 iters/tick
  * repeatCount 12 → 2 iters/tick
  * repeatCount 24 → 1 iter/tick
* **Gas:** `base=3`, `capPerFrame=12`

Tradeoff: at very high speed, liquids become slightly “less pressure-equalized per simulated time” (more sandy), but you avoid frame-time explosions.

---

## Two practical refinements (strongly recommended)

### 1) Early-out on convergence

Even if you schedule 6 iterations, stop early if nothing is moving:

* Each apply step sets `ChunkStateLiquid.updated` if any cell had `flow ≥ MIN_FLOW` (or `maxΔmass ≥ ε`).
* If after an iteration no chunks report updated, break the loop.

This makes “6 iterations” cheap once pools are already flat, and it improves sleep behavior.

### 2) Minimum packing iteration after movement

If you ever decide to optimize dispatch count by not doing a full batch every tick:

* still do **at least 1 iteration after each `board_update` tick** (liquids only),
* then you can spend remaining iterations at the end of the frame.

This specifically prevents the “liquid hits ground → stays underfilled for too long” artifact.

---

## Suggested defaults to implement first

* Liquids: `base=6`, `capPerFrame=24`, early-out enabled
* Gas: `base=3`, `capPerFrame=12`, early-out enabled
* `MIN_FLOW`: 2 (for u8 mass)

Then tune:

* If flattening still feels slow: increase `base` to 8 (keep the cap proportional, e.g., 32).
* If performance is too heavy: lower `base` to 4 and rely on early-out + your surface-tension/compaction rules to keep pools visually dense.

