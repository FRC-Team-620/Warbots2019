/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public abstract class DriveTrain extends Subsystem 
{
    /**
     * Tell the robot to move in a direction with or without
     * some turning. If speed is zero, then rotationRate is
     * a spin rate about a point. If rotationRate is zero
     * then robot should move in a STRAIGHT line.
     * @param speed Fwd/Rev spped of robot in ft/sec
     * @param rotationRate Rotation rate in deg/sec.
     */
    public abstract void drive(double speed, double rotationRate);

    public abstract Angle getAngle();

    public abstract double getTotalDistanceTravelled();
    
    public abstract void resetTotalDistanceTravelled();
}
