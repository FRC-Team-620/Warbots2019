/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech;
import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PullForwardWithCargoMechCommand extends Command {

  private CargoMech cargoMech = (CargoMech) Robot.scoringMechanism;

  public PullForwardWithCargoMechCommand() {
    requires(cargoMech);
  }

  @Override
  protected void initialize() {
    System.out.println("Starting pull forward with cargo mech");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    cargoMech.pullForward();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.oi.getDriverController().getRawButtonPressed(3);
  }

  @Override
  protected void end() {
    cargoMech.stopCapture();
  }
}
