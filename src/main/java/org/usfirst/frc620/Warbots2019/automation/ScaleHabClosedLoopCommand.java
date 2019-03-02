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

public class ScaleHabClosedLoopCommand extends Command {

  private ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;
  private Elevator elevator = Robot.elevator;
  private DriveTrain driveTrain = Robot.driveTrain;

  public ScaleHabClosedLoopCommand() 
  {
    requires(scissorLift);
    requires(elevator);

    //uses drive train for navX; should not call requires(driveTrain) because
    //this command does not prevent other drive train commands from running
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    
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
