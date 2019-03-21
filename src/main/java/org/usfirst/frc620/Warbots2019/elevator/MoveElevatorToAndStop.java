/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

public class MoveElevatorToAndStop extends MoveElevatorTo 
{
  public MoveElevatorToAndStop(double height) 
  {
    super(height);
    System.out.println("Creating move elvator to " + height + " and stop");
  }

  @Override
  protected boolean isFinished() 
  {
    return super.isFinished() || super.onTarget();
  }
}
