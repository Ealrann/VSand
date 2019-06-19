#!/bin/sh
DIR=${0%/*}/../bin
(cd $DIR && ./java -Dlog4j.configurationFile=./log4j2.xml -m org.sheepy.vsand/org.sheepy.vsand.VSandHeadlessBenchmarkLauncher  $@)
