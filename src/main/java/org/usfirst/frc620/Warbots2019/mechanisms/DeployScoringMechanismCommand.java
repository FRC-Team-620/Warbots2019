/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms;

import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class DeployScoringMechanismCommand extends Command {

  ScoringMechanism scoringMechanism = Robot.scoringMechanism;

  public DeployScoringMechanismCommand() {
    Logger.log("New Command: "+this.getName());
    requires(scoringMechanism);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    scoringMechanism.deploy();
    System.out.println("Deploying!");
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return scoringMechanism.isDeployed();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    scoringMechanism.stop();
  }
}
