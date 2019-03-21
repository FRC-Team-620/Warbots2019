/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ReleaseElevatorCommand extends InstantCommand {
  public ReleaseElevatorCommand() {
    requires(Robot.elevator);
  }

  @Override
  protected void execute() {
    Robot.elevator.drive(0);
  }
}
