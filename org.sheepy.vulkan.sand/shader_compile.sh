#!/bin/bash

rm *.spv 2>/dev/null

for i in `find res/ -type f -name "*.frag" -o -name "*.vert" -o -name "*.comp" 2>/dev/null`
do
    glslangValidator -V $i -o $i.spv
done
