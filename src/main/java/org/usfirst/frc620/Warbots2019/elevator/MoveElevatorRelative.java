/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.robot.Robot;

public class MoveElevatorRelative extends MoveElevatorTo 
{
  public MoveElevatorRelative(double relativeHeight)
  {
    super(Robot.elevator.getHeight() + relativeHeight);
  }
}
