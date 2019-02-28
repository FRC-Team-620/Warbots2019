/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Simulated Drive Train for use when instantiating the robot on a local 
// laptop when no other drive train class is appropriate

package org.usfirst.frc620.Warbots2019.sim;

import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveWithJoystickCommand;
import org.usfirst.frc620.Warbots2019.utility.Logger;


/**
 *
 */
public class SimDriveTrain extends DriveTrain 
{

    public SimDriveTrain() 
    {
        // TODO add Elements to the configurable container (See OI as example)
        // Elements to add: commands, other config settings specific to this
        // sub-system (prefix impl specific names with 'sim_')
    }

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new DriveWithJoystickCommand());
        Logger.log("simdrivetrain: initDefaultCommand");
    }

    @Override
    public void periodic() 
    {
    }

    public void drive(double speed, double turnRate)
    {
        // System.out.println("drive "+speed+" "+turnRate);
    }

    @Override
    public void curvatureDrive(double speed, double curvature) 
    {
        // System.out.println("drive "+speed+" "+curvature);
    }

    public double getTotalDistanceTravelled()
    {
        return 0.0;
    }

    public void resetTotalDistanceTravelled()
    {
    }

    public double getAcceleration()
    {
        return 0.0;
    }

    @Override
    public Angle getAngle() 
    {
        return new Angle(0.0);
    }
}

