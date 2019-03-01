/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;


public class FollowLineWithCameraCommand extends Command 
{
    public FollowLineWithCameraCommand() 
    {
        Logger.log("New Command: "+this.getName());
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
        Processor = new LineProcessor();
    }



    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
System.out.println("Starting follow line with camera");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        try
        {
System.out.println("FollowLineCommand is running");

            Line line = Processor.Process();
            System.out.println("Line: " + line);
            double xIntercept = line.getXIntercept().getX();
            System.out.println("xIntercept: " + xIntercept);
            double invSlope = 1.0 / line.getM();
            System.out.println("invSlope: " + invSlope);
            double curvature = (XInterceptConstant * xIntercept) + (InverseSlopeConstant * invSlope);
            System.out.println("curvature: " + curvature);
            driveTrain.curvatureDrive(Speed, curvature); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() 
    {//TODO: cut control, catch impossible case, buzz drivers
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
    }

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        super.initSendable(builder);

        builder.addDoubleProperty("Inverse Slope Constant", () -> InverseSlopeConstant , (constant) -> 
            { 
                this.InverseSlopeConstant = constant; 
            });
        builder.addDoubleProperty("X Intercept Constant", () -> XInterceptConstant , (constant) -> 
            { 
                this.XInterceptConstant = constant; 
            });
        builder.addDoubleProperty("Speed Constant", () -> Speed , (constant) -> 
        { 
            this.Speed = constant; 
        });
    }

    double InverseSlopeConstant = 0.5;
    double Speed = 0.25;
    double XInterceptConstant = 0.5;
    LineProcessor Processor;
    DriveTrain driveTrain = Robot.driveTrain; 
}
