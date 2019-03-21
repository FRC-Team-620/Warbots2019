/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import java.util.List;
import java.util.Optional;

import org.usfirst.frc620.Warbots2019.automation.HoverScoringModeCommand;
import org.usfirst.frc620.Warbots2019.automation.ScoringMode;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ControlElevatorWithJoystick extends Command 
{
  private boolean finished;

  double speedFactor;
  public ControlElevatorWithJoystick() {
    finished = false;
    Logger.log("New Command: "+this.getName());
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    speedFactor = 1.0;
    ControlReader config = Robot.config;
    if (config.getMappedString("elevator.speed_factor") != null)
    {
        speedFactor = config.getMappedDouble("elevator.speed_factor");
    }
    requires(Robot.elevator);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.drive(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    // double speed = Robot.oi.getElevatorSpeed();
    // double speed = Robot.oi.scorerController.getY(Hand.kLeft);
    if (Robot.oi.scorerController.getRawButtonPressed(9))
      Robot.elevator.setSnapParameters(null);

    double speed = Robot.oi.scorerController.getRawAxis(1);
    if (Math.abs(speed) < 0.2)
      Scheduler.getInstance().add(new HoldElevatorPosition());
    else
      Robot.elevator.drive(-speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() 
  {
    Logger.log("Command: ["+this.getName()+"] done");
  }

  private Optional<ScoringMode> getNearestTarget()
  {
    List<ScoringMode> snapHeights = Robot.elevator.getScoringModes();
    double maxSnapDist = 3000;
    
    double height = Robot.elevator.getHeight();

    if (snapHeights == null)
      return Optional.empty();

    double minDist = Double.POSITIVE_INFINITY;
    Optional<ScoringMode> closestTarget = Optional.empty();
    for (ScoringMode target : snapHeights)
    {
      double targetHeight = target.getHeight();
      double diff = Math.abs(height - targetHeight);
      if (diff < maxSnapDist && diff < minDist)
      {
        minDist = diff;
        closestTarget = Optional.of(target);
      }
    }
    return closestTarget;
  }
}
