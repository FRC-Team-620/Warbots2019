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

public class ControlClimbingMechWithJoystick extends Command {

  Joystick joystick = Robot.oi.driverController;
  ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    System.out.println("Raised: " + scissorLift.isRaised());
    System.out.println("Lowered: " + scissorLift.isLowered());

    double speed = joystick.getRawAxis(4);
    if (Math.abs(speed) < 0.1)
      speed = 0;
    scissorLift.drive(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
}