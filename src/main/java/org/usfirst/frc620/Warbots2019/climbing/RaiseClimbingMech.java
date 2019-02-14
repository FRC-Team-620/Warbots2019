/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseClimbingMech extends Command {
  // Called repeatedly when this Command is scheduled to run

  ClimbingMechanism climbingMechanism = Robot.climbingMechanism;

  @Override
  protected void execute() {
    climbingMechanism.raise();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return climbingMechanism.isRaised();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    climbingMechanism.stop();
  }
}
