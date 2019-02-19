/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.Command;

public class TurnAngle extends Command {
  private DriveTrain drivetrain;
  //The speed we want to turn
  private double m_speed;
  //The amount we want to turn
  private Angle m_angle;
  //The starting angle
  private Angle m_startAngle;
  //The angle we want to finish at
  private double finalAngle;
  //use these, they are giving errors
  
  public TurnAngle(){
    drivetrain = Robot.driveTrain;
    m_speed = 1;
    System.out.println("=== grace is cool ===");
  }
 

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

 // Called repeatedly when this Command is scheduled to run
 @Override
 protected void execute() {
  m_angle = new Angle(StateManager.getInstance().getDoubleValue(StateManager.StateKey.COMMANDED_TURNANGLE)/360.0);
  m_startAngle = drivetrain.getAngle();
  finalAngle = m_startAngle.plus(m_angle).toDegrees();
  System.out.println("*** TurnAngle = " + m_angle.toDegrees());
   System.out.println("Turning: " + m_speed + ", angle: " + drivetrain.getAngle() + ", final angle: " + finalAngle);
   if (!isFinished())
   {

     drivetrain.drive(0, m_speed);
     System.out.println("Direction: " + m_speed);
   }
 }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
  
    double diff = Math.abs(m_angle.toDegrees() - drivetrain.getAngle().toDegrees());
    if (diff < 0.5 )
    {
      drivetrain.drive(0, 0.0);
    }
    return diff < 0.5;
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
