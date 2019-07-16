#!/bin/sh
DIR=${0%/*}/../bin
(cd $DIR && ./java -p ../modules -m org.sheepy.vsand/org.sheepy.vsand.VSandHeadlessBenchmarkLauncher  $@)
