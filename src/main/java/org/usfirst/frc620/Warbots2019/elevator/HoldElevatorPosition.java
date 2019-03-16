/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.elevator.Elevator.ElevatorLevel;
import org.usfirst.frc620.Warbots2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class HoldElevatorPosition extends Command 
{
  private Elevator elevator = Robot.elevator;

  public HoldElevatorPosition() 
  {
    requires(elevator);
  }

  @Override
  protected void initialize() 
  {
    System.out.println("Holding position");
    elevator.holdCurrentHeight();
  }

  @Override
  protected boolean isFinished() 
  {
    return Math.abs(Robot.oi.getElevatorSpeed()) > 0.2;
  }

  @Override
  protected void end() 
  {
    Logger.log("Command: ["+this.getName()+"] done");
  }
}
