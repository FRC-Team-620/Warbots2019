/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ControlScissorLiftWithJoystick extends Command {

  Joystick joystick = Robot.oi.driverController;
  ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;

  public ControlScissorLiftWithJoystick()
  {
    requires(scissorLift);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = joystick.getRawAxis(3) - joystick.getRawAxis(2);
    if (Math.abs(speed) < 0.2)
      speed = 0;
    scissorLift.drive(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
}
