@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC% -p ..\modules -Xshareclasses:cacheDir=sharedClasses -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
