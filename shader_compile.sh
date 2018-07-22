#!/bin/bash

for i in `find */res/ -type f -name "*.frag" -o -name "*.vert" -o -name "*.comp" 2>/dev/null`
do
    rm $i.spv 2>/dev/null
    name=`basename $i`
    dir=`dirname $i`
    glslangValidator -V $i -o /tmp/$name.spv
    spirv-opt -O /tmp/$name.spv -o /tmp/$name.spv
    spirv-remap --strip all --dce all -i /tmp/$name.spv -o $dir
    rm /tmp/$name.spv
done
