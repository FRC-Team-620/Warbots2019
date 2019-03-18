/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.elevator;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ControlElevatorWithJoystick extends Command {
  private double[] snapHeights = null;
  private double maxSnapDist = 2000;
  double speedFactor;
  public ControlElevatorWithJoystick() {
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
<<<<<<< HEAD
    //double speed = Robot.oi.driverController.getY(Hand.kRight);
    //Robot.elevator.drive(-speed);
=======
    double speed = Robot.oi.scorerController.getY(Hand.kLeft);
    if (Math.abs(speed) < 0.2)
      Scheduler.getInstance().add(new MoveElevatorTo(getSnapHeight()));
    else
      Robot.elevator.drive(-speed);
>>>>>>> 602af89437b51697a1cd93ee74964040aeca9f79
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() 
  {
    Logger.log("Command: ["+this.getName()+"] done");
  }

  private double getSnapHeight()
  {
    double height = Robot.elevator.getHeight();

    if (snapHeights == null)
      return height;

    double minDist = Double.POSITIVE_INFINITY;
    double closestHeight = height;
    for (double snapHeight : snapHeights)
    {
      double diff = Math.abs(height - snapHeight);
      if (diff < minDist)
      {
        minDist = diff;
        closestHeight = snapHeight;
      }
    }
    if (minDist < maxSnapDist)
      return closestHeight;
    else
      return height;
  }
}
