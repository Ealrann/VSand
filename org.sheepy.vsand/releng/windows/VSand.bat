@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC% -Xms32M -Xmx64M -p ..\modules -Xshareclasses:cacheDir=sharedClasses -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
