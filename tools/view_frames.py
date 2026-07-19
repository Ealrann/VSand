#!/usr/bin/env python3
"""Render VSand dump frames as compact ASCII art.

Usage:
  view_frames.py <dumpDir> <frame> [frame...] [--crop=X0,Y0,X1,Y1] [--mass]

Material rendering: 00->'.', Wall->'#', Water->'~', others->first letter of name.
With --mass, water cells show mass quantization: ' '<25%, '-', '+', '~'=full, 'O'=overfull.
"""

import re
import sys
from pathlib import Path


def load_metadata(dump_dir):
    names = {}
    text = (dump_dir / "metadata.txt").read_text()
    in_materials = False
    for line in text.splitlines():
        if line.startswith("materials:"):
            in_materials = True
            continue
        if in_materials and line.strip():
            mid, name = line.split(None, 1)
            names[int(mid, 16)] = name.strip()
    return names


def load_frame(dump_dir, frame):
    paths = sorted(dump_dir.glob(f"frame_*{frame:04d}.txt")) or sorted(dump_dir.glob(f"frame_{frame}.txt"))
    if not paths:
        raise FileNotFoundError(f"frame {frame} in {dump_dir}")
    text = paths[0].read_text()
    mat_match = re.search(r"matHex:\n(.*?)\n\nmassHex:\n(.*)", text, re.S)
    mat_lines = mat_match.group(1).strip("\n").splitlines()
    mass_lines = mat_match.group(2).strip("\n").splitlines()
    mat = [[int(v, 16) for v in line.split()] for line in mat_lines]
    mass = [[int(v, 16) for v in line.split()] for line in mass_lines]
    return mat, mass


SYMBOLS = {"Void": ".", "Wall": "#", "Water": "~", "Sand": "s", "Dirt": "d"}
M_FULL = 4096


def render(mat, mass, names, crop, show_mass):
    height = len(mat)
    width = len(mat[0])
    x0, y0, x1, y1 = crop if crop else (0, 0, width - 1, height - 1)
    out = []
    for y in range(max(0, y0), min(height, y1 + 1)):
        row = []
        for x in range(max(0, x0), min(width, x1 + 1)):
            mid = mat[y][x]
            name = names.get(mid, "?")
            if show_mass and name == "Water":
                m = mass[y][x]
                if m > M_FULL:
                    ch = "O"
                elif m == M_FULL:
                    ch = "~"
                elif m >= 3 * M_FULL // 4:
                    ch = "+"
                elif m >= M_FULL // 4:
                    ch = "-"
                else:
                    ch = "'"
            else:
                ch = SYMBOLS.get(name, name[0].lower() if name != "?" else "?")
                if mid == 0:
                    ch = "."
            row.append(ch)
        out.append("".join(row))
    return "\n".join(out)


def main():
    crop = None
    show_mass = False
    pos = []
    for a in sys.argv[1:]:
        if a.startswith("--crop="):
            crop = tuple(int(v) for v in a.split("=", 1)[1].split(","))
        elif a == "--mass":
            show_mass = True
        else:
            pos.append(a)
    dump_dir = Path(pos[0])
    names = load_metadata(dump_dir)
    for frame_arg in pos[1:]:
        frame = int(frame_arg)
        mat, mass = load_frame(dump_dir, frame)
        print(f"--- frame {frame} ---")
        print(render(mat, mass, names, crop, show_mass))


if __name__ == "__main__":
    main()
