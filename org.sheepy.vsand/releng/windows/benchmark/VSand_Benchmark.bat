@echo off
set DIR="%~dp0\..\bin"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC% -m org.sheepy.vsand/org.sheepy.vsand.VSandBenchmarkLauncher  %* & popd
pause
