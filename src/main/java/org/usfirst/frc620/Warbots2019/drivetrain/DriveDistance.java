/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import com.revrobotics.CANEncoder;

import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
  private double m_speed;
  private double m_distance;
  private double init_rotation;
  private double final_position;
  // Assuming that wheel radius is 2 inches and divide by 12 to get feet.
  private static final double WHEEL_RADIUS = 2 / 12;
  private CANEncoder encoder;

  public DriveDistance(double distance, double speed) {
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
    init_rotation = encoder.getPosition();
    final_position = 2 * init_rotation * Math.PI * WHEEL_RADIUS + m_distance;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.drive(m_speed, 0);
  }
//h
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean ret = 2 * encoder.getPosition() * Math.PI * WHEEL_RADIUS >= final_position;
    if (2 * encoder.getPosition() * Math.PI * WHEEL_RADIUS >= final_position)
      Robot.driveTrain.drive(0, 0);
    System.out.println(String.format("Position = %f ", encoder.getPosition()));
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