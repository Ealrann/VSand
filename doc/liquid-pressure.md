# Liquid Pressure System

How VSand makes pressure liquids balance in U-pipes and flatten pools, while
keeping the classic falling-sand movement (and its visual noise) untouched.
Updated 2026-06-11, replacing the bounded in-`board_update` pressure helpers
(`tryPressureColumnLift` / `tryPressureRowShift`, removed).

## Design summary

The system is split into a **pressure field** (the mass buffers) and two
**particle transport operations** (`pressure_apply.comp`). Particles remain
authoritative for everything visible; the field only tells them where
pressure is. Cell count is conserved by both operations (pure shifts);
mass is a signal, not a conserved volume.

### Tick pipeline

```
board_update      classic movement + transformations (1 dispatch)
mass_seed         seeds M_FULL for newly created liquid cells (1 dispatch)
mass_update x6    field relaxation: COLUMN_PROFILE / ROW_EQUALIZE passes
pressure_apply x4 two LIFT+PULL cycles
board_to_pixel    rendering
```

`pressure_apply` alternates the board ping-pong internally (LIFT reads
board[w], writes board[1-w]; PULL reads board[1-w], writes board[w]), so the
tick still ends with the freshest state in board[w] where the next tick
expects it. Every cell is copy-through-written, which keeps both board
buffers coherent (including for sleeping chunks).

## The pressure field (mass buffers, mass_update.comp)

Semantics: `mass(x, y)` approximates the hydrostatic pressure at that cell,
with `M_FULL` (4096) = atmospheric (a plain full cell at a surface) and a
gradient of `M_GRADIENT` (256) per cell of depth. `M_CAP_MAX` (32768) lets
the field represent ~112 cells of head.

Per tick, six alternating passes (an alternating-direction style solver):

- **COLUMN_PROFILE**: each resting vertical liquid run is set exactly to the
  conservative hydrostatic profile (`base + M_GRADIENT * depth`, topmost
  cells pinned at `MIN_LIQUID_MASS` when the run is mass-deficient). One
  pass fully converges a column; iterative pairwise packing was abandoned
  because it never outran the stirring caused by particle movement (it
  delivered roughly half the head, which silently starved the lift trigger).
- **ROW_EQUALIZE**: bounded same-liquid resting row segments are equalized
  to their mean (cells at one height in a connected body are at one
  pressure). Segment windows are anchored to absolute x positions and
  shifted between passes (offsets 0/16/8) so neighboring windows couple.

Boundary condition: a resting cell with open sky above relaxes toward
`M_FULL` — quickly upward (`SURFACE_REFILL_RATE`, it is the mass source that
keeps a draining body pressurized) and slowly downward
(`SURFACE_DRAIN_RATE`, so pressure arriving at an outlet surface can
accumulate and fire the lift instead of being swallowed by its own boundary
condition). This anchors the gauge globally and lets triggers use absolute
thresholds.

Stability rules learned the hard way:

- A liquid cell is never drained below `MIN_LIQUID_MASS`: `mass_seed`
  re-seeds zero-mass liquid cells with `M_FULL` (they read as newly created)
  — letting relaxation drain skins to 0 created a 4096-per-tick mass pump.
- `mass_seed` must never top up nonzero cells (mass rides with its
  particle), for the same reason.
- Falling runs (bottom rests on void) keep their masses frozen; the field
  only describes resting liquid, and landing impulses would otherwise fire
  the pressure ops. Exception: a single void with the same liquid right
  below it (a healing bubble in transit) does not freeze the run above it —
  one bubble would otherwise starve its whole column, collapse the local
  gauge and breed more bubbles ("boiling"). Voids resting on walls (lee
  pockets under flowing water) do stay frozen: pressurizing across them
  only churns.

## Particle transport (pressure_apply.comp)

Two phases, each a separate full-board dispatch over a consistent snapshot.
Every invocation re-evaluates any operation overlapping its cell from global
reads — identical inputs, identical verdicts — so there are no claims, no
shared-memory windows, and **no chunk-seam blind spots** (the old column
lift could not cross 16-row chunk boundaries, which is why U-pipes stalled
at exactly y=48).

### LIFT (vertical column extrusion)

A resting same-liquid column run lifts one cell when:

- the cell above the run top is void **and under open sky** (only true
  surfaces are extruded; lifting into interior holes would bubble them
  upward through the body),
- the run rests on a barrier (static or denser material) within `LIFT_MAX`,
  and has at least 2 cells,
- the **two deepest cells** exceed their hydrostatic reference
  (`M_FULL + depth * M_GRADIENT`) by `Q_LIFT` — the trigger reads the run
  bottom because that is where pressure from a connected body arrives
  (U-pipe channel, pool floor), so it does not wait for the signal to climb
  the column; two cells filter single-cell transients.

Effect: outlet becomes liquid, run bottom becomes void, every cell between
is untouched (same liquid — so the converged field is not disturbed). The
outlet inherits the voided bottom's mass, re-gauged to `M_FULL` when it
surfaces under open sky (importing deep pressure to a surface would
self-retrigger).

### PULL (horizontal segment shift)

A void eats the adjacent cell of a same-liquid row segment; the hole
reappears at the segment's far end (up to `PULL_MAX` away), preferring a
cell with genuine sky above (2 void cells — strict enough to reject a
passing 1-cell bubble, permissive enough that splash strays cannot veto the
exit) so holes leave the liquid quickly. Triggers:

- the void is **enclosed and rests on a barrier** (the floor crawl): such a
  hole is transport debt and is always refilled — this conveys the lift's
  displaced void through a U-pipe channel back to the source arm. Holes
  with liquid below are buoyant bubbles and are left to the float (pulling
  them would make them burrow toward the higher-pressure bulk and wander);
- or the segment **drains a 2+ stack and ends at open sky**: surface
  leveling that erodes ramps from both sides. Single-cell ripples are below
  the leveling resolution and draining them would churn forever.

The eat direction is deterministic: from the side whose adjacent cell holds
the higher mass (ties per row/tick hash). Two voids flanking one run
arbitrate by that mass (then lower x), so a run is never shifted both ways.
Open-frontier fills are re-gauged to `M_FULL` like lift outlets.

### Bubble float

A void with the same liquid directly above and below is a bubble: each LIFT
pass it warps to the highest same-liquid cell within `BUBBLE_FLOAT_MAX`
above it (the body in between is identical liquid, so only the bubble
visibly moves). When the scan stops at another bubble it lands one cell
short, keeping a liquid spacer — bubbles stacked directly on each other
could not float and would jam into chains. Rising at gravity pace instead
(1 cell/tick) left landing-era bubbles wandering inside pools for hundreds
of ticks, which read as "boiling".

### Interplay with gravity

A lift's displaced void must be refilled (by PULL, from the pressurized
side) before gravity pulls the lifted column back down. Two LIFT+PULL
cycles per tick keep that pipeline fast; the deterministic pull direction
makes the refill reliable instead of a coin flip.

## board_update.comp changes

The movement shader lost all pressure helpers (~800 lines) and keeps only:

- classic falling/runoff behavior for **exposed skin** cells (sky above,
  not overfull) — the visual identity of the game is untouched;
- the mass-scored lateral level flow for **submerged or pressurized** cells
  (`isPressed || mass > M_FULL + M_EPS`).

## Tuning constants

| Constant | Value | Meaning |
| --- | --- | --- |
| `M_FULL` | 4096 | atmospheric gauge / one full cell |
| `M_GRADIENT` | 256 | pressure per cell of depth |
| `M_CAP_MAX` | 32768 | max storable head (~112 cells) |
| `MIN_LIQUID_MASS` | 256 | drain floor per liquid cell |
| `SURFACE_REFILL_RATE` / `SURFACE_DRAIN_RATE` | 128 / 16 | asymmetric surface anchor |
| `Q_LIFT` | 320 | lift overpressure threshold (> movement noise ~M_GRADIENT) |
| `Q_LIFT_PER_CELL` | 16 | extra lift margin per run cell (deep pools sit exactly Q_LIFT below the plain reference — the anchor's fixed point — and row-mixing noise grows with depth) |
| `LIFT_MAX` / `PULL_MAX` | 48 / 24 | op scan caps |
| `BUBBLE_FLOAT_MAX` | 6 | bubble warp distance per LIFT pass (×2 passes/tick) |

## Diagnostics

- `tools/analyze_dump.py <dumpDir> --mode=spread|upipe` — frames-to-flat /
  frames-to-balance summaries from `liquid_metrics.csv`.
- `tools/view_frames.py <dumpDir> <frame> [--crop=...] [--mass]` — ASCII
  board/mass rendering of dump frames.
- `tools/pull_replica.py` — Python replica of the PULL phase, used to fuzz
  cell-count conservation over dumped states.

Reference numbers live in `doc/liquid-pressure-baseline.md`.

## Known limitations

- Columns deeper than `COLUMN_PROFILE_MAX` (48) stratify into windows; the
  head below ~48 cells saturates per window.
- The slope scenario occasionally loses 1 cell over ~500 ticks of heavy
  chunk-seam traffic; the old baseline showed the same tolerance in its
  regression test (`assertLiquidCellsBetween(557, 558)`), so this predates
  the pressure rework.
- The mass and pressure passes run on the full board every tick (no chunk
  sleeping yet). Measured cost is small, but gating them on active-chunk
  neighborhoods is the natural next optimization.
