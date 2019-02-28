/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber;

import org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class stopIfHas extends Command {

  private TazGrabber tazGrabber;

  public stopIfHas() {
    Logger.log("New Command: "+this.getName());
    tazGrabber = (TazGrabber) Robot.scoringMechanism;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //requires(tazGrabber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  System.out.println(String.format("Has Object: %s", !tazGrabber.hasGameObject()? "Yes" : "No"));
  if(tazGrabber.hasGameObject())
  {
  tazGrabber.stop();  
  tazGrabber.close();
  tazGrabber.stow();
  }
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
}
