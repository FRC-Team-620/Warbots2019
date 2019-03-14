/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.command.Command;

public class GrabberCaptureCommand extends Command {

  private CargoMech cargoMech;

  public GrabberCaptureCommand() {

    Logger.log("New Command: "+this.getName());
    /*
    THIS IS DEBUGGING CODE FOR USE WHEN TRYING TO FIGURE OUT 
    WHERE THIS COMMAND IS BEING INSTANTIATED - BEST FOR
    SEEING WHAT HAPPENS WHEN THIS COMMAND IS MAPPED TO A DPad
    VALUE IN THE CONFIG, like driver.pov.up
    try
    {
       throw new Exception();
    }
    catch(Exception e)
    {
      StackTraceElement[] arr = e.getStackTrace();
      Logger.log("-------------- EXCEPTION ---------------");
      for (int i=0;i<arr.length; i++)
      {
          Logger.log(arr[i].toString());
      }
    }
*/
    cargoMech = (CargoMech) Robot.scoringMechanism;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(cargoMech);


  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Capture");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    cargoMech.captureCargo();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return cargoMech.hasCargo() ||
      timeSinceInitialized() > 5 ||
      Robot.oi.scorerController.getRawAxis(2) > 0.7 ||
      Robot.oi.scorerController.getRawAxis(3) > 0.7;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Logger.log("Command: ["+this.getName()+"] done");
    cargoMech.stopCapture();
  }
}
