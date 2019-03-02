/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class HabClimbMiddleCommand extends Command 
{
    private DriveTrain drivetrain;

    public HabClimbMiddleCommand() 
    {
        Logger.log("New Command: "+this.getName());
    
        drivetrain = Robot.driveTrain;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
    System.err.println("HabClimbMiddleCommand: NOT IMPLEMENTED!");
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        boolean ret = false;
        if (ret)
        {
            Logger.log("Command: ["+this.getName()+"] done");
        }
        return ret;
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

    public static ConfigurableImpl asConfigurable(){
        ConfigurableImpl configurable;
        configurable = new ConfigurableImpl();
        return configurable;
    }
}