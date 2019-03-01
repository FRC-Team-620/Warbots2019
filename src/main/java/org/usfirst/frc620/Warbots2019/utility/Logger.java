/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.io.File;
import java.io.FileWriter;

/**
 *  Recorder of messages to log file
 */
public class Logger
{
    /**
     * Flag indicating if we care about logging to file. If false
     * then strings are dropped on the floor.
     */
    public static boolean isLogging = true;

    /**
     * Instance of file to write to - instantiated when needed first.
     */
    public static FileWriter writer = null;

    /**
     * Record a string to the log file, if logging is enabled.
     * @param str
     */
    public static void log(String str)
    {
        if (isLogging)
        {
            try
            {
                if (writer == null)
                {
                    File file = null;
                    if (new File("/home/lvuser").exists())
                    {
                        file = new File("/home/lvuser/events.log");
                    }
                    else
                    {
                        file = new File("events.log");
                    }
                    writer = new FileWriter(file);
                    writer.write("Opened log file\n");
                    writer.flush();
                }
                if (writer != null)
                {
                    writer.write(str+"\n");
                    writer.flush();
                }
            }
            catch(Exception e)
            {
                // don't care
                System.err.println("Problem opening log file:");
                System.err.println(e.getMessage());
            }
        }
    }
}