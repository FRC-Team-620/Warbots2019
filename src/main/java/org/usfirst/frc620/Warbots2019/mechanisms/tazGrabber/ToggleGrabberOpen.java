/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.command.Command;
public class ToggleGrabberOpen extends Command 
{
  public static boolean toggle = true;
  public ToggleGrabberOpen() 
  {
    Logger.log("New Command: "+this.getName());
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.scoringMechanism);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(toggle)
    {
      Robot.scoringMechanism.deploy();
      System.out.println("Deployed!");
      toggle = false;
    } 
    else
    {
      Robot.scoringMechanism.stow();
      System.out.println("Stowed!");
      toggle = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
}
