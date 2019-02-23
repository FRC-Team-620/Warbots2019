/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import java.util.Arrays;

import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FollowLineWithCameraCommand extends Command 
{
    double turnConstant = 0.5;

    DriveTrain driveTrain = Robot.driveTrain; 

    public FollowLineWithCameraCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }



    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        double speed = 1 / 3;
        double curvature = 1;
        driveTrain.curvatureDrive(1.0 / 3.0, curvature * turnConstant);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        super.initSendable(builder);

        builder.addDoubleProperty("Turn Curvature Constant", () -> turnConstant , (turnConstant) -> 
          { 
            this.turnConstant = turnConstant; 
          });
    }
}
