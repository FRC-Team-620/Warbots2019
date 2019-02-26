/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

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

    public abstract double getHeight();

    public abstract boolean isAtTop();

    public abstract boolean isAtBottom();

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new ControlElevatorWithJoystick());
    }
}