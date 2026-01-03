# VSand – Water “Boiling” + Mass Drift Fixes (Pressure Solver)

This document describes two bugs that affected pressure-liquids (notably Water):

1) **Total mass drift** (“mass shift”): water mass changes over time in a closed tank.
2) **Boiling**: even with stable total mass, internal `Void` pockets appear inside resting water, typically near the bottom.

It also describes the fixes that were applied.

## Symptoms / Repro

**Scenarios**
- `VSand/scenarios/water_pool_boil_128x128.txt`
- `VSand/scenarios/water_pool_boil_64x64.txt`

**Unit tests**
- `VSand/org.sheepy.vsand/src/test/java/org/sheepy/vsand/test/LiquidMassDriftTest.java` (mass conservation)
- `VSand/org.sheepy.vsand/src/test/java/org/sheepy/vsand/test/WaterBoilingInvariantTest.java` (no internal voids near bottom)

## Problem 1 — Total mass drift (“mass shift”)

### What we observed

- In a closed tank scenario, total water mass was not constant across many iterations.

### Why it happened

The pressure solver is chunk-gated (active chunks + 1-ring halo). This processing domain is **not closed** under transfers:

- A processed cell could produce flux into a cell whose **destination chunk does not run apply**.
- Apply subtracts mass in the source, but the destination never receives its inflow in the same iteration.
- Result: net mass loss (or gain if stale flux is involved).

### Fix applied (processed-domain closure / no-flux frontier)

Treat the boundary between “processed” and “unprocessed” chunks as an impermeable boundary for the pressure solver:

- If `shouldProcessChunk(chunkLoc) == false`, write `flux[...] = 0` before returning (prevents stale flux).
- If a candidate flow’s **destination chunk** is not `shouldProcessChunk(destChunkLoc)`, force that flow to `0`.

Implementation: `pressure_flux_{down,up,left,right}.comp`.

## Problem 2 — Boiling (internal void creation inside resting water)

### What we observed

- Even after fixing total mass conservation, resting water could create internal `Void` pockets near the bottom.
- The mass view showed local low-mass streaks within otherwise “full” water.

### Why it happened

Horizontal sweeps are head-driven:

- In `pressure_flux_left/right`, the head delta can propose an outflow that equals the entire source mass (`candidate == mass`).
- When this happens on a supported **non-surface** cell, `pressure_apply_left/right` clears the drained source cell to `Void` (`newMass == 0 → newMaterialId = 0`).
- This creates/moves internal voids (“boiling”) while totalMass can remain constant.

We confirmed this by extending the debug void counters to split “dest was void” vs “dest was liquid” during void-creation events.

### Fix applied (anti-cavitation for horizontal sweeps)

Prevent a supported **non-surface** cell from being fully drained horizontally in a single step:

- In `pressure_flux_left/right`, when `candidate == mass` and `isSurface(srcLoc, materialId) == false`, reduce the flow to leave at least `MIN_FLOW` behind.

Implementation: `pressure_flux_left.comp`, `pressure_flux_right.comp`.

## Verification

Run unit tests:

```bash
./gradlew -p VSand test
```

Run deterministic dumps:

```bash
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=128x128 --scenario=$PWD/VSand/scenarios/water_pool_boil_128x128.txt --frames=200 --speed=20 --seed=1234 --no-raw --out=VSand/build/state-dumps/boil_check"
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=64x64 --scenario=$PWD/VSand/scenarios/water_column_fall_to_floor_64x64.txt --frames=120 --speed=1 --seed=1234 --out=VSand/build/state-dumps/lateral_check"
```
