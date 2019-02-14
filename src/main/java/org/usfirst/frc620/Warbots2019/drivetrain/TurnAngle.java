/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.Command;

public class TurnAngle extends Command {
  private double m_speed;
 private Angle m_angle;
  private DriveTrain drivetrain;
  //use these, they are giving errors

  public TurnAngle(DriveTrain dt, Angle myAngle, double speed) {
    m_speed = speed;
    m_angle = myAngle;
    drivetrain = dt;
    System.out.println("hello");
    // requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Angle myAngle = Robot.driveTrain.getAngle();
    
    //System.out.println("The angle is:"+myAngle);
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
