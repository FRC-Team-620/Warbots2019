/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;

import edu.wpi.first.wpilibj.command.Command;

public class ControlElevatorWithJoystick extends Command {
  double speedFactor;
  public ControlElevatorWithJoystick() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    speedFactor = 1.0;
    ControlReader config = Robot.config;
    if (config.getMappedString("elevator.speed_factor") != null)
    {
        speedFactor = config.getMappedDouble("elevator.speed_factor");
    }
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = Robot.oi.getElevatorSpeed();
    Robot.elevator.drive(-speed * speedFactor);
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
