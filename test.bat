@echo off
setlocal
set PROJ_PATH=C:\Users\Public\frc2019\workspace\Warbots2019

REM Load env variables
call %PROJ_PATH%\env.bat

REM Build unit tests
PUSHD %PROJ_PATH%\test\config\dump
%JAVA_EXE% -classpath "%OUR_CLASSPATH%" config.dump.ConfigurationDumpTest
if errorlevel == 1 (
    echo  ConfigurationDumpTest                    FAIL
) ELSE (
    echo  ConfigurationDumpTest                    PASS
)
POPD

PUSHD %PROJ_PATH%\test\robot\config
%JAVA_EXE% -classpath "%OUR_CLASSPATH%;%WPI_CLASSPATH%" robot.config.RobotConfigurationTest
if errorlevel == 1 (
    echo  RobotConfigurationTest                   FAIL
) ELSE (
    echo  RobotConfigurationTest                   PASS
)
POPD

endlocal
