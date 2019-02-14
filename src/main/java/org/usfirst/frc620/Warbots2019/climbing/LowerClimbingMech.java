/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LowerClimbingMech extends Command {
  
  ClimbingMechanism climbingMechanism = Robot.climbingMechanism;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    climbingMechanism.lower();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return climbingMechanism.isLowered();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    climbingMechanism.stop();
  }
}
