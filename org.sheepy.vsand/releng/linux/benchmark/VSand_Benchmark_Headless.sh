#!/bin/sh
DIR=${0%/*}/../bin
(cd $DIR && ./java -m org.sheepy.vsand/org.sheepy.vsand.VSandHeadlessBenchmarkLauncher  $@)
