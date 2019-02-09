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
    public abstract void drive(double speed);

    public abstract double getHeight();

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new ControlElevatorWithJoystick());
    }
}