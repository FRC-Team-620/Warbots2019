REM
REM This script is called by the other batch files to establish various
REM environment variables
REM
set PROJ_PATH=C:\Users\Public\frc2019\workspace\Warbots2019
set JDK_PATH=C:\Users\Public\frc2019\jdk\bin
set JAVAC_EXE=%JDK_PATH%\javac.exe
set JAVA_EXE=%JDK_PATH%\java.exe
set OUR_CLASSPATH=%PROJ_PATH%\build\classes\java\main

REM Build CLASSPATH
set WPIVERSION=2019.2.1
set NAVXVERSION=3.1.365
set OPENCVVERSION=3.4.4-4
set CTREVERSION=5.13.0

set MAVEN_PATH=C:\Users\Public\frc2019\maven
set WPI_UTIL_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\wpiutil\wpiutil-java\%WPIVERSION%
set WPI_LIB_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\wpilibj\wpilibj-java\%WPIVERSION%
set WPI_CS_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\cscore\cscore-java\%WPIVERSION%
set WPI_CAMSVR_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\cameraserver\cameraserver-java\%WPIVERSION%
set WPI_NT_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\ntcore\ntcore-java\%WPIVERSION%
set WPI_HAL_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\hal\hal-java\%WPIVERSION%
set EJML_JAR_PATH=C:\Users\Public\frc2019\workspace\Warbots2019\test
set NAVX_JAR_PATH=%MAVEN_PATH%\com\kauailabs\navx\frc\navx-java\%NAVXVERSION%
set OPENCV_JAR_PATH=%MAVEN_PATH%\edu\wpi\first\thirdparty\frc2019\opencv\opencv-java\%OPENCVVERSION%
set CTRE_JAR_PATH=%MAVEN_PATH%\com\ctre\phoenix\wpiapi-java\%CTREVERSION%
set CTRE_API_JAR_PATH=%MAVEN_PATH%\com\ctre\phoenix\api-java\%CTREVERSION%

set WPI_CLASSPATH=%WPI_UTIL_JAR_PATH%\wpiutil-java-%WPIVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%WPI_LIB_JAR_PATH%\wpilibj-java-%WPIVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%WPI_CS_JAR_PATH%\cscore-java-%WPIVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%WPI_CAMSVR_JAR_PATH%\cameraserver-java-%WPIVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%WPI_NT_JAR_PATH%\ntcore-java-%WPIVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%WPI_HAL_JAR_PATH%\hal-java-%WPIVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%EJML_JAR_PATH%\ejml-simple-0.37.1.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%NAVX_JAR_PATH%\navx-java-%NAVXVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%OPENCV_JAR_PATH%\opencv-java-%OPENCVVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%CTRE_JAR_PATH%\wpiapi-java-%CTREVERSION%.jar
set WPI_CLASSPATH=%WPI_CLASSPATH%;%CTRE_API_JAR_PATH%\api-java-%CTREVERSION%.jar

set PATH=%PATH%;%PROJ_PATH%\build\tmp\jniExtractDir