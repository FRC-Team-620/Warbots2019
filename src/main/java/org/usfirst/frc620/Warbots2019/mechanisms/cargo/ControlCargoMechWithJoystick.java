/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ControlCargoMechWithJoystick extends Command 
{
  private CargoMech cargoMech = (CargoMech) Robot.scoringMechanism;

  public ControlCargoMechWithJoystick() 
  {
    Logger.log("New command: ControlCargoMechWithJoystick");
    requires(cargoMech);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    Joystick joystick = Robot.oi.scorerController;
    if (joystick.getRawAxis(2) > 0.7)
      cargoMech.captureCargo();
    else if (joystick.getRawAxis(3) > 0.7)
      cargoMech.ejectCargo();
    else
      cargoMech.stopCapture();
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
