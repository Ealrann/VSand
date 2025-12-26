#!/bin/sh
DIR=${0%/*}/../bin
ARGS="-Xms64M -Xmx128M --enable-preview --enable-native-access=org.lwjgl --add-exports org.lwjgl/org.lwjgl.system.ffm=org.lwjgl.glfw,org.lwjgl.openal,org.lwjgl.stb,org.lwjgl.vulkan,org.lwjgl.nuklear"
(cd $DIR && ./java $ARGS -p ../modules -m org.sheepy.vsand/org.sheepy.vsand.VSandBenchmarkLauncher  $@)
