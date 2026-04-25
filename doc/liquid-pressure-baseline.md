# Liquid Pressure Baseline

Baseline captured on 2026-04-25 for the current hybrid pressure behavior.
Use this as the "known good enough" reference before tuning pressure movement.

## Commands

```bash
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=64x64 --scenario=/home/eal/git/Logoce/VSand/scenarios/liquids/spread/water_column_flatten_64x64.txt --frames=240 --speed=4 --seed=1234 --out=/tmp/vsand_pressure_spread --no-raw"
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=64x64 --scenario=/home/eal/git/Logoce/VSand/scenarios/liquids/u-pipe/water_u_pipe_64x64.txt --frames=240 --speed=4 --seed=1234 --out=/tmp/vsand_pressure_upipe --no-raw"
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=96x64 --scenario=/home/eal/git/Logoce/VSand/scenarios/liquids/slope/water_slope_chunk_boundary_96x64.txt --frames=240 --speed=4 --seed=1234 --out=/tmp/vsand_pressure_slope --no-raw"
```

The dump tool writes `liquid_metrics.csv`, `liquid_columns.csv`, and text frames in
the selected `/tmp/vsand_pressure_*` output directories.

## Spread

Scenario: `scenarios/liquids/spread/water_column_flatten_64x64.txt`

| Frame | Cells | Mass | BBox | Surface range | Chunk seam pockets H/V | Core cells x=12..28 |
| ---: | ---: | ---: | --- | ---: | --- | ---: |
| 0 | 651 | 2666496 | 17x41 | 0 | 0/0 | 651 |
| 20 | 651 | 2922694 | 62x18 | 17 | 1/5 | 281 |
| 40 | 651 | 3214128 | 62x15 | 11 | 5/1 | 230 |
| 80 | 651 | 3430771 | 62x12 | 3 | 1/1 | 182 |
| 120 | 651 | 3466833 | 62x11 | 1 | 1/1 | 174 |
| 240 | 651 | 3466833 | 62x11 | 1 | 0/1 | 174 |

Reference expectations:

- Particle count is conserved at 651 cells.
- Horizontal spread is fast: width reaches 62 by frame 20.
- Settled surface range is 1 at frame 240.

## U-Pipe

Scenario: `scenarios/liquids/u-pipe/water_u_pipe_64x64.txt`

| Frame | Cells | Mass | BBox | Surface range | Chunk seam pockets H/V | Right arm cells | Right arm mass |
| ---: | ---: | ---: | --- | ---: | --- | ---: | ---: |
| 0 | 179 | 733184 | 5x37 | 0 | 0/0 | 0 | 0 |
| 20 | 179 | 767504 | 29x23 | 20 | 0/18 | 0 | 0 |
| 40 | 179 | 810533 | 29x20 | 17 | 0/18 | 0 | 0 |
| 80 | 179 | 877204 | 29x20 | 17 | 0/18 | 0 | 0 |
| 120 | 179 | 932862 | 29x20 | 16 | 0/18 | 19 | 81425 |
| 240 | 179 | 978320 | 29x19 | 15 | 0/0 | 19 | 91615 |

Reference expectations:

- Particle count is conserved at 179 cells.
- Right-arm rise is visible by frame 120.
- Horizontal chunk seam pockets are 0 at frame 240.

## Slope / Chunk Boundary

Scenario: `scenarios/liquids/slope/water_slope_chunk_boundary_96x64.txt`

| Frame | Cells | Mass | BBox | Surface range | Chunk seam pockets H/V |
| ---: | ---: | ---: | --- | ---: | --- |
| 0 | 558 | 2285568 | 34x18 | 0 | 0/0 |
| 20 | 558 | 2499145 | 86x45 | 0 | 3/1 |
| 40 | 558 | 2580984 | 94x42 | 2 | 8/1 |
| 80 | 558 | 2436171 | 94x37 | 6 | 6/1 |
| 120 | 558 | 2524409 | 94x28 | 13 | 4/1 |
| 240 | 558 | 2746971 | 94x9 | 4 | 1/1 |

Reference expectations:

- Particle count is conserved at 558 cells.
- The water reaches the right side of the slope by frame 40.
- At frame 240, horizontal and vertical chunk seam pockets are both bounded to 1.
