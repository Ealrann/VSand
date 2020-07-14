@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
set ARGS="-Xms32M -Xmx64M --enable-preview"
pushd %DIR% & %JAVA_EXEC% %ARGS% -p ..\modules -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
