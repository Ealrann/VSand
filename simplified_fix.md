Yes. The simplified “head + directional pressure_step” design still needs two adaptations from `boil_fix.md` (one becomes trivial because you remove flux buffers; the other still applies).

## What `boil_fix.md` changed (and why)

`boil_fix.md` documents two issues in the current sweep solver: 

1. **Total mass drift** in a closed tank because the processed domain (active chunks + halo) is not closed: a processed source can send flow into an unprocessed destination chunk, so the source subtracts mass but the destination never adds it in that iteration. Fix: treat the processed/unprocessed boundary as **no-flux**, and clear flux to 0 for unprocessed chunks to avoid stale flux. 

2. **“Boiling” (internal void pockets)** because head-driven horizontal flow can drain a supported non-surface cell completely (`candidate == mass`), turning it into void and moving cavities inside a pool. Fix: in horizontal sweeps, if `candidate == mass` and the source is **supported and not surface**, clamp flow so at least `MIN_FLOW` remains. 

## Does the simplified algorithm need changes?

### A) Mass drift fix: still needed, but implemented differently

In your simplified algorithm you remove flux buffers, so the “stale flux” part disappears automatically. You don’t need “write flux=0 before returning” because there is no flux buffer. 

However, **processed-domain closure is still required** as long as you keep chunk gating (`shouldProcessChunk`) and still flip ping-pong buffers globally per sweep. Otherwise you recreate the same failure mode: a processed cell can subtract mass via `outflow`, while the destination cell (in an unprocessed chunk) never executes the update that would add that inflow.

**Required adaptation in simplified `pressure_step`:**

* When computing flow across an edge `src -> dst`, force that flow to **0** if `shouldProcessChunk(dstChunk)==false` (and also if `shouldProcessChunk(srcChunk)==false`, but you typically won’t compute flows there anyway). This is the direct equivalent of the “no-flux frontier” rule. 

**Additionally (important for ping-pong correctness):**

* If a cell/chunk is not processed in a sweep, you must still ensure `BoardOut/MassOut` receive a correct value. Either:

  * copy `BoardIn/MassIn` to `BoardOut/MassOut` for unprocessed chunks inside the shader (early return does a copy), or
  * run a separate “copy pass” for sleeping chunks.
    If you “return without writing,” the destination buffer contains stale values from a previous sweep and you can get apparent mass drift/corruption even if you blocked cross-boundary flows.

### B) Boiling (anti-cavitation): still needed

The simplified algorithm still uses head-driven horizontal outflow (dp / H_DIV). That can still produce `outflow == srcMass` and therefore drain an internal cell to void exactly as described in `boil_fix.md`. 

So you must carry over the same rule:

**Required adaptation for LEFT/RIGHT steps:**

* After computing `candidateFlow` (before subtracting it), if:

  * cell is **supported**
  * cell is **not surface** (`isSurface(src)==false`)
  * and `candidateFlow >= srcMass`
    then clamp:
  * `flow = max(0, srcMass - MIN_FLOW)`
    (and then still apply destination capacity clamp)

This preserves total mass but prevents internal void creation by ensuring a non-surface cell never becomes empty solely due to one horizontal sweep. 

## Minimal “diff” to your simplified design

If your simplified design is:

* `pressure_head`: compute head for whole board (or at least for all columns that may be referenced)
* `pressure_step(dir)`: update masses/materials in one direction

Then you add:

1. **No-flux frontier**

* In `pressure_step(dir)`, when you compute an edge flow, do:

  * if `!shouldProcessChunk(dstChunk)` → `flow = 0`
* And for cells in `!shouldProcessChunk(srcChunk)`, copy input to output (or guarantee output is written unchanged).

2. **Anti-cavitation (LEFT/RIGHT only)**

* In the horizontal flow computation, apply the “don’t fully drain non-surface supported cell” clamp exactly like the fix describes. 

That’s it. Everything else in `boil_fix.md` is specific to having separate flux/apply buffers; the simplified step-based solver only needs the conceptual parts above.

