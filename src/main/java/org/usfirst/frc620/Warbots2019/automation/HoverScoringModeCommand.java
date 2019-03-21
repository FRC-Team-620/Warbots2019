/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;
import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Scheduler;

public class HoverScoringModeCommand extends MoveElevatorTo 
{
  private boolean executed;
  private ScoringMode scoringMode;

  public HoverScoringModeCommand(ScoringMode scoringMode) 
  {
    super(scoringMode.getHeight());
    this.scoringMode = scoringMode;
    executed = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    super.execute();
    if (Robot.oi.scorerController.getRawButtonPressed(3))
    {
      Scheduler.getInstance().add(scoringMode);
      executed = true;
    }
  }

  @Override
  protected boolean isFinished() 
  {
    return super.isFinished() || executed;
  }
}
