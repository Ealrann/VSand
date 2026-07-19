# Liquid Pressure Baseline

Reference numbers captured on 2026-06-11 for the pressure system described
in `doc/liquid-pressure.md` (mass field + `pressure_apply` lift/pull pass).
The previous baseline (2026-04-25, in-`board_update` pressure helpers) is
kept in the tables for comparison.

## Commands

```bash
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=64x64 --scenario=/home/eal/git/Logoce/VSand/scenarios/liquids/spread/water_column_flatten_64x64.txt --frames=240 --speed=4 --seed=1234 --out=/tmp/vsand_pressure_spread --no-raw"
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=64x64 --scenario=/home/eal/git/Logoce/VSand/scenarios/liquids/u-pipe/water_u_pipe_64x64.txt --frames=240 --speed=4 --seed=1234 --out=/tmp/vsand_pressure_upipe --no-raw"
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=96x64 --scenario=/home/eal/git/Logoce/VSand/scenarios/liquids/slope/water_slope_chunk_boundary_96x64.txt --frames=240 --speed=4 --seed=1234 --out=/tmp/vsand_pressure_slope --no-raw"

python3 tools/analyze_dump.py /tmp/vsand_pressure_spread --mode=spread --core=12,28
python3 tools/analyze_dump.py /tmp/vsand_pressure_upipe --mode=upipe --left=18,24 --right=40,46
```

## U-Pipe (the headline change)

Scenario: `scenarios/liquids/u-pipe/water_u_pipe_64x64.txt` — left arm
filled, right arm empty. ΔH = left arm surface height − right arm surface
height (bottom-connected columns, bands x=18..24 / x=40..46).

| Metric | Old helpers | Pressure pass |
| --- | ---: | ---: |
| ΔH at frame 240 | 12 (stalled forever) | **2** |
| frames to stable ΔH ≤ 8 | never | **61** |
| frames to stable ΔH ≤ 4 | never | **77** |
| frames to stable ΔH ≤ 2 | never | **85** |
| right-arm cells at frame 240 | 19 | **~72** |
| cells conserved | 179 → 179 | 179 → 179 |

The old column lift could not operate across 16-row chunk seams
(`PRESSURE_COLUMN_LIFT_SCAN_MAX_Y`), which is why the right arm stalled at
exactly y=47/48. The same scenario shifted 6 cells up
(`water_u_pipe_shifted_64x64.txt`) used to creep to ΔH≈4 in 240 frames; it
now balances at frame 81 — behavior is seam-position independent.

## Spread / pool flatten

Scenario: `scenarios/liquids/spread/water_column_flatten_64x64.txt` — a
16-wide column collapsing into a 62-wide box (equilibrium depth ~10.5).
Core band = cells remaining in x=12..28.

| Frame | Cells | Core band (old → new) | Surface range (old → new) | voidBelow (old → new) |
| ---: | ---: | --- | --- | --- |
| 20 | 651 | 283 → **262** | 17 → 21 | 16 → 55 |
| 40 | 651 | 230 → **216** | 10 → 17 | 4 → 32 |
| 80 | 651 | 183 → **182** | 3 → 11 | 0 → 7 |
| 120 | 651 | 173 → **181** | 1 → **2** | 0 → **0** |
| 240 | 651 | 174 → **179** | 1 → **2** | 0 → **0** |

- The hump drains faster (core band), and the settled pool is dead stable
  (0 isolated cells; at most a lone transit bubble in flight).
- The old version pushed a 1-cell-thin film to the walls faster (bbox width
  62 at frame 20 vs 53 now); the new version advances as a body.
- frames to stable surface range: ≤2 at 68, ≤1 at 166 (old: ≤1 at 85).
- Settled pools hold zero interior voids: a 128x64 pour reaches 0 wandering
  bubbles by frame ~300 and stays silent; landing-phase bubbles float out
  at BUBBLE_FLOAT_MAX*2 cells per tick instead of wandering for hundreds of
  ticks. Very wide ramps (250+ cells) level by stack-drain erosion with a
  decaying bubble stream while they settle.

## Slope / chunk boundary

Scenario: `scenarios/liquids/slope/water_slope_chunk_boundary_96x64.txt`.

| Frame | Cells | Surface range | Seam pockets H/V |
| ---: | ---: | ---: | --- |
| 40 | 558 | 2 | 1/5 |
| 120 | 557 | 6 | 3/7 |
| 240 | 557 | 2 | 0/1 |

One cell is occasionally lost during the heavy seam-crossing slide phase
(~1 per 500 ticks); the regression test tolerated 557..558 before this work
as well, so it predates the pressure rework.

## Invariants to keep

- Non-reactive water conserves its cell count in spread and U-pipe.
- Only pressure liquids hold mass; masses stay in (0, M_CAP_MAX].
- Settled pools report 0 interior voids and 0 isolated cells.
- `./gradlew -p VSand test` covers all of the above
  (`LiquidPressureBaselineTest`, `PressureLiquidRegressionTest`,
  `LiquidMass*Test`, `WaterColumnCollapseTest`).
