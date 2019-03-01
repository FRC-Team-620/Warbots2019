/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.sim;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.util.WPILibVersion;

import org.usfirst.frc620.Warbots2019.utility.Logger;
import org.usfirst.frc620.Warbots2019.robot.Robot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class SimRobot extends Robot 
{

    /**
     * For unit testing - we're Real if the /home/lvuser
     * directory does not exist
     */
    public static boolean isReal()
    {
        return false;
    }

    public void startCompetition() 
    {
        robotInit();
        for (int i=0; i<simIterations; i++)
        {
            try {
                // thread to sleep for 1000 milliseconds
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
            loopFunc();
        }
        Logger.log("startCompetition(): loop done");
    }

    private static int simIterations = 0;
    /**
     * This is an [almost] exact copy og RobotBase::startRobot, but since it
     * has System.exit at the end, we don't want it to do that in a test
     * so we create another and call it only from test codes.
     * @param robotSupplier
     */
    public static <T extends RobotBase> void testRobot(Supplier<T> robotSupplier, int iter) 
    {
        simIterations = iter;
        if (!HAL.initialize(500, 0)) {
          throw new IllegalStateException("Failed to initialize. Terminating");
        }
    
        // Call a CameraServer JNI function to force OpenCV native library loading
        // Needed because all the OpenCV JNI functions don't have built in loading
        CameraServerJNI.enumerateSinks();
    
        HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Java);
    
        //System.out.println("********** Test Robot program starting **********");
    
        T robot;
        try {
          robot = robotSupplier.get();
        } catch (Throwable throwable) {
          Throwable cause = throwable.getCause();
          if (cause != null) {
            throwable = cause;
          }
          String robotName = "Unknown";
          StackTraceElement[] elements = throwable.getStackTrace();
          if (elements.length > 0) {
            robotName = elements[0].getClassName();
          }
          //DriverStation.reportError("Unhandled exception instantiating robot " + robotName + " "
          //    + throwable.toString(), elements);
          //DriverStation.reportWarning("Robots should not quit, but yours did!", false);
          //DriverStation.reportError("Could not instantiate robot " + robotName + "!", false);
          // Don't...!
          //System.exit(1);
          return;
        }

        if (isReal()) {
          try {
            final File file = new File("/tmp/frc_versions/FRC_Lib_Version.ini");
    
            if (file.exists()) {
              file.delete();
            }
    
            file.createNewFile();
    
            try (OutputStream output = Files.newOutputStream(file.toPath())) {
              output.write("Java ".getBytes(StandardCharsets.UTF_8));
              output.write(WPILibVersion.Version.getBytes(StandardCharsets.UTF_8));
            }
    
          } catch (IOException ex) {
            DriverStation.reportError("Could not write FRC_Lib_Version.ini: " + ex.toString(),
                    ex.getStackTrace());
          }
        }

        boolean errorOnExit = false;
        try {
          robot.startCompetition();
        } catch (Throwable throwable) {
          Throwable cause = throwable.getCause();
          if (cause != null) {
            throwable = cause;
          }
          DriverStation.reportError("Unhandled exception: " + throwable.toString(),
              throwable.getStackTrace());
          errorOnExit = true;
        } finally {
          // startCompetition never returns unless exception occurs....
          //DriverStation.reportWarning("Robots should not quit, but yours did!", false);
          if (errorOnExit) {
            //DriverStation.reportError(
            //    "The startCompetition() method (or methods called by it) should have "
            //        + "handled the exception above.", false);
          } else {
            //DriverStation.reportError("Unexpected return from startCompetition() method.", false);
          }
        }
        
        // Don't...!
        //System.exit(1);
    }
}
