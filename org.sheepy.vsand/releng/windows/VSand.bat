@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
set ARGS=-Xms32M -Xmx64M --enable-preview --enable-native-access=org.lwjgl --add-exports org.lwjgl/org.lwjgl.system.ffm=org.lwjgl.glfw,org.lwjgl.openal,org.lwjgl.stb,org.lwjgl.vulkan,org.lwjgl.nuklear
pushd %DIR% & %JAVA_EXEC% %ARGS% -p ..\modules -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
