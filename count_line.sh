#!/bin/bash

find -type f \( -name *.java -o -name *.xcore \) ! -name package-info.java ! -path "*lily.lib*" ! -path *src-gen* | xargs wc -l
#find -type f \( -name *.java -o -name *.xcore \) ! -name package-info.java ! -path *src-gen* | xargs wc -l
