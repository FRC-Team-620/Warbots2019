/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber;

//import org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber;
//import org.usfirst.frc620.Warbots2019.robot.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class open extends Command {

  private Subsystem TazGrabber;

  public open() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(this.TazGrabber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    ((org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber) TazGrabber).open();
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
