# Headless state-dump runner

This is a small developer tool to run VSand *headless* (no window) and dump the full simulation state to disk **every frame**.

It is meant for debugging/analysis (including by AI coding agents): you can reproduce a scenario deterministically and inspect `mat` + `liqMass` precisely instead of relying on visuals.

## Run

From the repo root:

```bash
./gradlew -p VSand :org.sheepy.vsand:dumpRun --args="--size=64x32 --scenario=/abs/path/scenario.txt --frames=200 --speed=1 --seed=1234 --out=build/state-dumps/case1"
```

Notes:
- `--size` must be **even×even** (swizzled board storage).
- Dumps include `frame_0000` (initial state after applying the scenario) and then `frame_0001..frame_N`.
- `--speed` is VSand “ticks per frame” (`CompositePipeline.repeat`).

## CLI options

- `--size=WxH` (required) board size in pixels/cells
- `--scenario=PATH` (required) scenario text file (see below)
- `--frames=N` (required) number of frames to simulate/dump (**total dumps = N + 1** including `frame_0000`)
- `--speed=N` (optional, default `1`) ticks per frame
- `--seed=LONG` (optional) enables deterministic random (stable dumps across runs)
- `--out=DIR` (optional) output directory (default: `build/state-dumps/run-YYYYMMDD_HHMMSS`)
- `--no-raw` (optional) disables writing raw swizzled buffers (`*.swz.bin`)

## Scenario file format

One command per line, `#` starts a comment. Coordinates use the simulation convention: `x` to the right, `y` downward.

Sample scenarios live in `VSand/scenarios/`.

Supported commands:
- `fill <MaterialName>`
- `frame <MaterialName> <thickness>`
- `line <MaterialName> <x1> <y1> <x2> <y2> <thickness>`
- `circle <MaterialName> <x> <y> <size>`
- `square <MaterialName> <x> <y> <size>`

Example (`scenario.txt`):
```txt
# Contained box + a water column
frame Wall 2
line Wall 2 24 61 24 2
line Water 32 4 32 14 5
```

## Output files

The output directory contains:
- `metadata.txt`: size, scenario path, seed, and the material id ↔ name mapping.
- `liquid_metrics.csv`: one compact per-frame row per pressure-managed liquid.
- `liquid_columns.csv`: per-frame column metrics for columns containing pressure-managed liquid.
- `frame_XXXX.txt`: per-frame human-readable dump (stats + grids).
- `frame_XXXX.board.swz.bin` / `frame_XXXX.mass.swz.bin` (unless `--no-raw`): raw GPU buffer contents in the VSand swizzled packing.

### `frame_XXXX.txt` format

- Header: `frame`, `ticks`, `size`, `speed`
- Summary:
  - `cellsNonEmpty`: number of cells where `mat != 0`
  - `cellsNonZeroMass`: number of cells where `liqMass != 0`
  - `totalMass`: sum of all `liqMass` (all liquids)
- Per material stats lines:
  - `mat=XX name=... cells=... mass=... minMass=... maxMass=...`
- `matHex`: a `WxH` grid of 2-digit hex material ids (`00` = empty)
- `massHex`: a `WxH` grid of 4-digit hex masses (`0000` = no mass)

### Liquid metrics

`liquid_metrics.csv` is the fastest file to inspect when comparing solver changes. It includes:
- mass/cell counts (`totalMass`, `cells`, `partialCells`, `fullCells`, `overfullCells`)
- shape information (`bbox*`, `avgX`, `avgY`, `leftExtent`, `rightExtent`)
- bottom-connected surface profile metrics (`surfaceMin`, `surfaceMax`, `surfaceRange`)
- diagnostic counts (`surfaceCells`, `voidBelowCells`, `isolatedCells`)

`liquid_columns.csv` adds one row per non-empty liquid column with `bottomHeight`, column mass, and per-column partial/full cell counts.

## Implementation

- Entry point: `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/VSandStateDumpLauncher.java`
- Scenario parsing: `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/dump/Scenario.java`
- Dump writer: `VSand/org.sheepy.vsand/src/main/java/org/sheepy/vsand/dump/StateDumpWriter.java`
