/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;
import java.util.ArrayList;
import java.util.Arrays;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.robot.StateManager.StateKey;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc620.Warbots2019.utility.Logger;

/**
 * Add your docs here.
 */
public abstract class DriveTrain extends Subsystem
{
    
    public abstract void drive(double speed, double turn);

    public abstract void curvatureDrive(double speed, double curvature); 

    public abstract Angle getAngle();

    public abstract Angle getPitch();

    public abstract double getTotalDistanceTravelled();
    public abstract void resetTotalDistanceTravelled();

    public DriveTrain()
    {
        StateManager.getInstance().setDoubleValue(StateKey.COMMANDED_DRIVEDISTANCE, 10);
        StateManager.getInstance().setDoubleValue(StateKey.COMMANDED_TURNANGLE, 180);
    }

    public static Configurable asConfigurable() 
    {
        final ConfigurableImpl configurable;
        configurable = new ConfigurableImpl();

        // NOTE: These should move to OI, for speed and rotation speed
        // so that getRobotSpeed returns post-filtered speed value.
        configurable.addElement(new Element("driver.center_deadzone", 
            "General DriveTrain Setting: Raw scale range 0 to 1.0 for speed dead zone", 
            new ArrayList<String>(Arrays.asList("(0,1.0)")))); 
        configurable.addElement(new Element("driver.rotation_deadzone", 
            "General DriveTrain Setting: radians of wedge that isolates a joystick 'spin' for values near 0", null));
        configurable.addElement(new Element("driver.speed_deadzone", 
            "General DriveTrain Setting: radians of wedge that isolates a joystick 'straight' for values near 0", null ));

        return configurable;
    }

    public abstract void stop();
}
