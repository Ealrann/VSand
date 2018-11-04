#!/bin/bash

optimize='false'

for i in `find */res/ -type f -name "*.frag" -o -name "*.vert" -o -name "*.comp" 2>/dev/null`
do
	rm $i.spv 2>/dev/null
	dir=`dirname $i`
	if [ $optimize == 'true' ]
	then
		name=`basename $i`
		glslangValidator -V $i -o /tmp/$name.spv
		spirv-opt -O /tmp/$name.spv -o /tmp/$name.spv
		spirv-remap --strip all --dce all -i /tmp/$name.spv -o $dir
		rm /tmp/$name.spv
	else
		glslangValidator -V $i -o $i.spv
	fi
done
