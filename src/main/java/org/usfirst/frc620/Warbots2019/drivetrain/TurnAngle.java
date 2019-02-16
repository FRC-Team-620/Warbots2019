/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.robot.StateManager.StateKey;
import edu.wpi.first.wpilibj.SendableBase;
import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.Command;

public class TurnAngle extends Command {
  private double m_speed;
  private Angle m_angle; 
  private Angle init_angle;
  private Angle angle = new Angle(0);
  private Angle final_angle;
  private DriveTrain drivetrain;
  

  // use these, they are giving errors

  public TurnAngle(DriveTrain dt, Angle myAngle, double speed) {
    m_speed = speed;
    m_angle = myAngle;
    drivetrain = dt;
    System.out.println("hello");
    // requires(Robot.driveTrain);
  }

  public TurnAngle() {
    m_angle = new Angle(StateManager.getInstance().getDoubleValue(StateKey.COMMANDED_TURNANGLE));
    // m_angle = myAngle;
    // System.out.println("hello");
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    final_angle = init_angle.plus(m_angle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    angle = angle.plus(Robot.driveTrain.getAngle());
    Robot.driveTrain.drive(0, angle.toTurns());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() { //still needs to be fixed
    // boolean ret = (angle >= final_angle);
    // if (ret)
    //   Robot.driveTrain.drive(0, 0);
    // return ret;
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
