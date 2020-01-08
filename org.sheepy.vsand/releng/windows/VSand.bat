@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC% -Xms32M -Xmx64M -p ..\modules -Xscmx32M -Xshareclasses:cacheDir=sharedClasses,name=VSand,nonfatal -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
