/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.util.List;
import java.util.Vector;

/**
 * Add your docs here.
 */
public class TalonReadThread {
    private static TalonReadThread defaultThread;

    private List<TableProperty> properties;
    private Thread updateThread;

    public TalonReadThread()
    {
        properties = new Vector<>();
        updateThread = new Thread(() -> {
            while (true)
                update();
        });
    }

    public static TalonReadThread getDefault()
    {
        if (defaultThread == null)
        {
            defaultThread = new TalonReadThread();
            defaultThread.start();
        }
        return defaultThread;
    }

    private void update()
    {
        try
        {
            for(var property : properties)
                property.update();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void start()
    {
        System.out.println("Starting talon read thread");
        updateThread.start();
    }

    public void stop()
    {
        System.out.println("Stopping talon read thread");
        updateThread.interrupt();
    }

    public void add(TableProperty property)
    {
        properties.add(property);
    }

    public void remove(TableProperty property)
    {
        properties.remove(property);
    }
}
