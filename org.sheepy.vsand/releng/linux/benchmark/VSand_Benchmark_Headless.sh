#!/bin/sh
DIR=${0%/*}/../bin
(cd $DIR && ./java -Xms64M -Xmx128M -p ../modules -m org.sheepy.vsand/org.sheepy.vsand.VSandHeadlessBenchmarkLauncher  $@)
