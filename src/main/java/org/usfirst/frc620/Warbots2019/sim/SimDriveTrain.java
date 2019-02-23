// Simulated Drive Train for use when instantiating the robot on a local 
// laptop when no other drive train iclass is appropriate

package org.usfirst.frc620.Warbots2019.sim;

import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveWithJoystickCommand;
import org.usfirst.frc620.Warbots2019.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SimDriveTrain extends DriveTrain 
{

    public SimDriveTrain() 
    {
    }

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new DriveWithJoystickCommand());
        System.out.println("We are in sim drive train");
    }

    @Override
    public void periodic() 
    {
    }

    public void drive(double speed, double turnRate)
    {
        //System.out.println("drive "+speed+" "+turnRate);
    }

    @Override
    public void curvatureDrive(double speed, double curvature) 
    {
        //System.out.println("drive "+speed+" "+curvature);
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

