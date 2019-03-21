/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class MoveElevatorTo extends Command 
{
  private Elevator elevator = Robot.elevator;
  
  private double height;

  public MoveElevatorTo(double height) 
  {
    Logger.log("New Command: "+this.getName());
    this.height = height;
    requires(elevator);
  }

  @Override
  protected void initialize() 
  {
    elevator.driveTo(height);
  }

  @Override
  protected boolean isFinished() 
  {
    return Math.abs(Robot.oi.scorerController.getY(Hand.kLeft)) > 0.2;
  }

  public boolean onTarget()
  {
    return false;
    // return Math.abs(elevator.getHeight() - height) < 500;
  }

  @Override
  protected void end() 
  {
    Logger.log("Command: ["+this.getName()+"] done");
  }
}
