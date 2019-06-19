@echo off
set DIR="%~dp0\bin"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC% -Dlog4j.configurationFile=./log4j2.xml -m org.sheepy.vsand/org.sheepy.vsand.VSandApplicationLauncher  %* & popd
