/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.robot.StateManager.StateKey;
import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public abstract class DriveTrain extends Subsystem 
{
    public abstract void drive(double speed, double turn);

    public abstract void curvatureDrive(double speed, double curvature); 

    public abstract Angle getAngle();

    public abstract double getTotalDistanceTravelled();
    public abstract void resetTotalDistanceTravelled();

    public DriveTrain()
    {
        StateManager.getInstance().setDoubleValue(StateKey.COMMANDED_DRIVEDISTANCE, 10);
        StateManager.getInstance().setDoubleValue(StateKey.COMMANDED_TURNANGLE, 180);
    }
}
