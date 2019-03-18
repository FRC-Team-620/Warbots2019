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

public class ScaleHabClosedLoopCommand extends Command 
{
  //TODO: load these from somewhere else
  private static final double elevatorBaseSpeed = 0.3;
  private static final double scissorLiftBaseSpeed = 0.4;
  private static final double elevatorKP = 0.03;
  private static final double scissorLiftKP = 0.04;

  private Elevator elevator = Robot.elevator;
  private ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;
  private DriveTrain driveTrain = Robot.driveTrain;

  public ScaleHabClosedLoopCommand() 
  {
    requires(elevator);
    requires(scissorLift);

    //Uses drive train for navX, but does not prevent other commands form using the
    //drive train. Should NOT call requires(driveTrain).
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    double pitch = driveTrain.getPitch().toDegrees();

    elevator.drive(elevatorBaseSpeed + elevatorKP * pitch);
    scissorLift.drive(scissorLiftBaseSpeed - scissorLiftKP * pitch);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return elevator.getHeight() == 0 && scissorLift.isLowered();
  }
}
