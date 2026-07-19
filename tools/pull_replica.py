#!/usr/bin/env python3
"""Python replica of pressure_apply.comp's PULL phase, used to fuzz
conservation over dumped board states. Prints any (frame, seed) witness
where the pull pass changes the liquid cell count."""

import re
import sys
from pathlib import Path

PULL_MAX = 24
WALL_STATIC = True


def load(dump_dir, frame):
    txt = (Path(dump_dir) / f"frame_{frame:04d}.txt").read_text()
    m = re.search(r"matHex:\n(.*?)\n\nmassHex:\n(.*)", txt, re.S)
    mat = [[int(v, 16) for v in line.split()] for line in m.group(1).strip().splitlines()]
    mass = [[int(v, 16) for v in line.split()] for line in m.group(2).strip().splitlines()]
    return mat, mass


class Board:
    def __init__(self, mat, mass, materials):
        self.mat = mat
        self.mass = mass
        self.h = len(mat)
        self.w = len(mat[0])
        self.materials = materials  # id -> (isStatic, density, flags)

    def outside(self, x, y):
        return x < 0 or y < 0 or x >= self.w or y >= self.h

    def material(self, x, y):
        return 0 if self.outside(x, y) else self.mat[y][x]

    def m(self, x, y):
        return 0 if self.outside(x, y) else self.mass[y][x]

    def is_liquid(self, mid):
        return self.materials.get(mid, (0, 0, 0))[2] & 1 != 0

    def is_barrier_for(self, mid, density):
        s, d, _ = self.materials.get(mid, (0, 0, 0))
        return s == 1 or d >= density


def tie_break(y, seed):
    h = ((y * 2654435761) ^ seed) & 0xFFFFFFFF
    return 1 if ((h ^ (h >> 16)) & 1) == 0 else -1


def under_open_sky(b, x, y):
    for o in range(1, 7):
        if b.outside(x, y - o):
            return True
        if b.material(x, y - o) != 0:
            return False
    return True


def supported_void(b, x, y):
    return b.outside(x, y + 1) or b.material(x, y + 1) != 0


def eligible_source(b, x, y):
    if b.outside(x, y) or not b.is_liquid(b.material(x, y)):
        return False
    return b.outside(x, y + 1) or b.material(x, y + 1) != 0


def eat_direction(b, x, y, seed):
    if not supported_void(b, x, y):
        return 0
    le = eligible_source(b, x - 1, y)
    re = eligible_source(b, x + 1, y)
    if not le and not re:
        return 0
    if not re:
        return -1
    if not le:
        return 1
    lm, rm = b.m(x - 1, y), b.m(x + 1, y)
    if lm != rm:
        return -1 if lm > rm else 1
    return tie_break(y, seed)


def segment_end(b, nx, ny, away, liquid):
    stacked = False
    ends_open = False
    length = 0
    for off in range(PULL_MAX):
        sx = nx + away * off
        if b.outside(sx, ny) or b.material(sx, ny) != liquid:
            break
        length = off + 1
        above = 0 if b.outside(sx, ny - 1) else b.material(sx, ny - 1)
        if above == liquid:
            stacked = True
        elif above == 0 and off > 0:
            ends_open = True
            break
    return length, (length > 0 and stacked and ends_open)


def wins_run(b, vx, vy, eat, liquid, seed):
    nx = vx + eat
    for off in range(1, 2 * PULL_MAX + 1):
        sx = nx + eat * off
        if b.outside(sx, vy):
            return True
        sv = b.material(sx, vy)
        if sv == liquid:
            continue
        if sv != 0:
            return True
        if eat_direction(b, sx, vy, seed) != -eat:
            return True
        my_m = b.m(nx, vy)
        other_m = b.m(sx - eat, vy)
        if my_m != other_m:
            return my_m > other_m
        return vx < sx
    return True


def pull_new_state(b, x, y, seed):
    cur = b.material(x, y)
    if cur == 0:
        eat = eat_direction(b, x, y, seed)
        if eat == 0:
            return cur
        nv = b.material(x + eat, y)
        _, trig = segment_end(b, x + eat, y, eat, nv)
        trig = trig or not under_open_sky(b, x, y)
        if trig and wins_run(b, x, y, eat, nv, seed):
            return nv
        return cur
    if not b.is_liquid(cur):
        return cur
    for side in (1, -1):
        vd = 0
        for off in range(1, PULL_MAX + 1):
            sx = x + side * off
            if b.outside(sx, y):
                break
            sv = b.material(sx, y)
            if sv == cur:
                continue
            if sv == 0:
                vd = off
            break
        if vd == 0:
            continue
        vx = x + side * vd
        if eat_direction(b, vx, y, seed) != -side:
            continue
        nx = vx - side
        seg_len, trig = segment_end(b, nx, y, -side, cur)
        trig = trig or not under_open_sky(b, vx, y)
        if not trig or vd > seg_len:
            continue
        if not wins_run(b, vx, y, -side, cur, seed):
            continue
        if vd == seg_len:
            return 0
        return cur
    return cur


def run_pull(b, seed):
    new = [[pull_new_state(b, x, y, seed) for x in range(b.w)] for y in range(b.h)]
    return new


def main():
    dump_dir = sys.argv[1]
    frames = range(int(sys.argv[2]), int(sys.argv[3]))
    materials = {0: (0, 0, 0), 3: (1, 20, 0), 5: (0, 3, 1)}  # Void, Wall, Water
    for frame in frames:
        try:
            mat, mass = load(dump_dir, frame)
        except FileNotFoundError:
            break
        b = Board(mat, mass, materials)
        before = sum(1 for y in range(b.h) for x in range(b.w) if mat[y][x] == 5)
        for seed in range(16):
            new = run_pull(b, seed * 0x9E3779B9 & 0xFFFFFFFF)
            after = sum(1 for y in range(b.h) for x in range(b.w) if new[y][x] == 5)
            if after != before:
                print(f"WITNESS frame={frame} seed={seed}: {before} -> {after}")
                for y in range(b.h):
                    for x in range(b.w):
                        if new[y][x] != mat[y][x]:
                            print(f"  ({x},{y}): {mat[y][x]} -> {new[y][x]}")
                return
    print("no witness found")


if __name__ == "__main__":
    main()
