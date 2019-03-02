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

REM
REM !!!!!!!!! NOTE !!!!!!!!! 
REM This is a complete hack - we're trying to run the RobotConfigurationTest
REM and have it terminate cleanly - however whenever we instantiate the Robot and
REM and run it, the JVM hangs despite calling Runtime.getRuntime().halt!
REM Therefore we "start" the java appl, and then kill it some time later.
REM
PUSHD %PROJ_PATH%\test\robot\config
taskkill /f /im "java.exe"
start "Robot Config" /B %JAVA_EXE% -classpath "%OUR_CLASSPATH%;%WPI_CLASSPATH%" robot.config.RobotConfigurationTest
ping 127.0.0.1 -n 5 > nul
taskkill /f /im "java.exe"
if errorlevel == 1 (
    echo  RobotConfigurationTest                   FAIL
) ELSE (
    echo  RobotConfigurationTest                   PASS
)
POPD

endlocal
