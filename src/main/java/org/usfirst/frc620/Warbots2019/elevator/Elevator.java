/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Add your docs here.
 */
public abstract class Elevator extends Subsystem 
{
    public static enum ElevatorLevel
    {
        FLOOR, MIDDLE, TOP
    }

    public abstract void drive(double speed);
    

    public abstract void driveTo(ElevatorLevel level);

    public abstract void holdCurrentHeight();

    public abstract double getHeight();

    public abstract boolean isAtTop();

    public abstract boolean isAtBottom();

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new ControlElevatorWithJoystick());
    }

    public static Configurable asConfigurable()
    {
        ConfigurableImpl configurable = new ConfigurableImpl();

        configurable.addElement(new Element("elevator.speed_factor", 
            "General Elevator Setting: The speed of the elevator as it travels between the enumerations of its levels", 
            new ArrayList<String>(Arrays.asList(
                "(0 to 1)"))));

        return configurable;
    }
}