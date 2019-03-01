@echo off
setlocal
set PROJ_PATH=C:\Users\Public\frc2019\workspace\Warbots2019

REM Load env variables
call %PROJ_PATH%\env.bat

REM make sure required TEMPORARY directories exist - These don't
REM get committed
if NOT EXIST %OUR_CLASSPATH% (
    mkdir %OUR_CLASSPATH%
)



@echo on
REM Build unit tests
%JAVAC_EXE% -classpath "%OUR_CLASSPATH%" -d "%OUR_CLASSPATH%" %PROJ_PATH%\test\config\dump\ConfigurationDumpTest.java
REM %JAVAC_EXE%
endlocal
