#!/usr/bin/env python3
"""Summarize a VSand dump directory produced by dumpRun.

Usage:
  analyze_dump.py <dumpDir> [--mode=spread|upipe] [--liquid=Water]
                  [--core=XMIN,XMAX] [--right=XMIN,XMAX] [--left=XMIN,XMAX]
                  [--flat-threshold=1] [--balance-threshold=2]

Outputs per-frame metrics and convergence summaries:
  - spread: surfaceRange per frame, frames-to-flat (surfaceRange <= threshold, stable)
  - upipe: left/right arm cells+mass and surface heights, frames-to-balance
"""

import csv
import sys
from collections import defaultdict
from pathlib import Path


def parse_args(argv):
    args = {"mode": "spread", "liquid": "Water", "core": None, "right": None,
            "left": None, "flat_threshold": 1, "balance_threshold": 2}
    pos = []
    for a in argv[1:]:
        if a.startswith("--mode="):
            args["mode"] = a.split("=", 1)[1]
        elif a.startswith("--liquid="):
            args["liquid"] = a.split("=", 1)[1]
        elif a.startswith("--core="):
            lo, hi = a.split("=", 1)[1].split(",")
            args["core"] = (int(lo), int(hi))
        elif a.startswith("--right="):
            lo, hi = a.split("=", 1)[1].split(",")
            args["right"] = (int(lo), int(hi))
        elif a.startswith("--left="):
            lo, hi = a.split("=", 1)[1].split(",")
            args["left"] = (int(lo), int(hi))
        elif a.startswith("--flat-threshold="):
            args["flat_threshold"] = int(a.split("=", 1)[1])
        elif a.startswith("--balance-threshold="):
            args["balance_threshold"] = int(a.split("=", 1)[1])
        else:
            pos.append(a)
    if len(pos) != 1:
        print(__doc__)
        sys.exit(1)
    args["dir"] = Path(pos[0])
    return args


def read_metrics(dump_dir, liquid):
    rows = {}
    with open(dump_dir / "liquid_metrics.csv", newline="") as fp:
        for row in csv.DictReader(fp):
            if row["materialName"] != liquid:
                continue
            rows[int(row["frame"])] = row
    return rows


def read_columns(dump_dir, liquid):
    by_frame = defaultdict(dict)
    with open(dump_dir / "liquid_columns.csv", newline="") as fp:
        for row in csv.DictReader(fp):
            if row["materialName"] != liquid:
                continue
            by_frame[int(row["frame"])][int(row["x"])] = row
    return by_frame


def band_stats(cols, band):
    cells = mass = 0
    heights = []
    for x, row in cols.items():
        if band and not (band[0] <= x <= band[1]):
            continue
        cells += int(row["cells"])
        mass += int(row["mass"])
        bh = int(row["bottomHeight"])
        if bh > 0:
            heights.append(bh)
    return cells, mass, heights


def stable_from(frames, predicate):
    """First frame index such that predicate holds for it and all later frames."""
    ok_from = None
    for f in frames:
        if predicate(f):
            if ok_from is None:
                ok_from = f
        else:
            ok_from = None
    return ok_from


def main():
    args = parse_args(sys.argv)
    metrics = read_metrics(args["dir"], args["liquid"])
    columns = read_columns(args["dir"], args["liquid"])
    frames = sorted(metrics)
    if not frames:
        print("no frames for liquid", args["liquid"])
        return

    cells0 = int(metrics[frames[0]]["cells"])
    print(f"liquid={args['liquid']} frames={len(frames)} initialCells={cells0}")

    interesting = {0, 5, 10, 15, 20, 30, 40, 60, 80, 120, 160, 200, 240}

    if args["mode"] == "spread":
        print("frame cells surfRange bboxW isolated voidBelow coreCells")
        flat_t = args["flat_threshold"]
        for f in frames:
            m = metrics[f]
            core_cells = ""
            if args["core"]:
                c, _, _ = band_stats(columns.get(f, {}), args["core"])
                core_cells = str(c)
            if f in interesting:
                print(f"{f:5d} {m['cells']:>5} {m['surfaceRange']:>9} {m['bboxWidth']:>5} "
                      f"{m['isolatedCells']:>8} {m['voidBelowCells']:>9} {core_cells:>9}")
        flat_frame = stable_from(frames, lambda f: int(metrics[f]["surfaceRange"]) <= flat_t)
        print(f"\nframes-to-flat (surfaceRange<={flat_t}, stable): {flat_frame}")
        for thr in (4, 3, 2, 1):
            fr = stable_from(frames, lambda f, t=thr: int(metrics[f]["surfaceRange"]) <= t)
            print(f"  stable surfaceRange<={thr}: frame {fr}")
        last = metrics[frames[-1]]
        print(f"conservation: cells {cells0} -> {last['cells']}")
    else:
        if not args["right"] or not args["left"]:
            print("upipe mode requires --left=XMIN,XMAX and --right=XMIN,XMAX")
            sys.exit(1)
        print("frame cells  leftCells rightCells  leftMass rightMass  leftH rightH  dH")
        bal_t = args["balance_threshold"]

        def heights(f):
            cols = columns.get(f, {})
            _, _, lh = band_stats(cols, args["left"])
            _, _, rh = band_stats(cols, args["right"])
            lmax = max(lh) if lh else 0
            rmax = max(rh) if rh else 0
            return lmax, rmax

        for f in frames:
            if f not in interesting:
                continue
            m = metrics[f]
            cols = columns.get(f, {})
            lc, lm, _ = band_stats(cols, args["left"])
            rc, rm, _ = band_stats(cols, args["right"])
            lh, rh = heights(f)
            print(f"{f:5d} {m['cells']:>5} {lc:>10} {rc:>10} {lm:>9} {rm:>9} {lh:>6} {rh:>6} {lh - rh:>4}")

        bal_frame = stable_from(frames, lambda f: abs(heights(f)[0] - heights(f)[1]) <= bal_t)
        print(f"\nframes-to-balance (|leftH-rightH|<={bal_t}, stable): {bal_frame}")
        for thr in (8, 4, 2, 1):
            fr = stable_from(frames, lambda f, t=thr: abs(heights(f)[0] - heights(f)[1]) <= t)
            print(f"  stable |dH|<={thr}: frame {fr}")
        last = metrics[frames[-1]]
        print(f"conservation: cells {cells0} -> {last['cells']}")


if __name__ == "__main__":
    main()
