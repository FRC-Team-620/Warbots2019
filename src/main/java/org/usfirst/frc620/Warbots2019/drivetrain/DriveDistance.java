/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
  private double m_speed;
  private double m_distance;
  private double init_position;
  private double position;
  private double final_position;
  private DriveTrain drivetrain;

  public DriveDistance(DriveTrain dt, double distance, double speed) {
    drivetrain = dt;
    // Distance is ft
    m_distance = distance;
    // Speed is ft/sec
    m_speed = speed;
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    init_position = drivetrain.getTotalDistanceTravelled();
    final_position = init_position + m_distance;
    System.out.println("The initial distance is: " + init_position);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.drive(m_speed, 0);
    position = drivetrain.getTotalDistanceTravelled() - init_position;
  }
//h
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean ret = Math.abs(position) >= final_position;
    if (Math.abs(position) >= final_position)
      Robot.driveTrain.drive(0, 0);

    init_position = 0;
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
}