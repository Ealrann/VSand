#!/bin/sh
DIR=${0%/*}/../bin
ARGS="-Xms64M -Xmx128M --enable-preview"
(cd $DIR && ./java $ARGS -p ../modules -m org.sheepy.vsand/org.sheepy.vsand.VSandHeadlessBenchmarkLauncher $@)
