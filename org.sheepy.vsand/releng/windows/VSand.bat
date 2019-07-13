@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC% -Xshareclasses -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
