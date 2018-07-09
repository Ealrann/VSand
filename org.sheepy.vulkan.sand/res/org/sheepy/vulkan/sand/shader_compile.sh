#!/bin/bash

rm *.spv 2>/dev/null

for i in `ls *.comp`
do
    glslangValidator -V $i -o $i.spv
done
