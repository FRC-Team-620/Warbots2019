/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.climbing.ScissorLift;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.elevator.Elevator;
import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ScaleHabOpenLoopCommand extends Command {
  private static final double elevatorBaseSpeed = 0.3;
  private static final double scissorLiftBaseSpeed = 1;

  private Elevator elevator = Robot.elevator;
  private ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;

  public ScaleHabOpenLoopCommand() 
  {
    requires(elevator);
    requires(scissorLift);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    elevator.drive(elevatorBaseSpeed);
    scissorLift.drive(scissorLiftBaseSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return elevator.getHeight() == 0 && scissorLift.isLowered();
  }
}
