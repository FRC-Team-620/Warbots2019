/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class HatchCaptureCommand extends Command {

  private CargoMech cargoMech;

  public HatchCaptureCommand() {
    Logger.log("New Command: "+this.getName());
    cargoMech = (CargoMech) Robot.scoringMechanism;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(cargoMech);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Eject");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    cargoMech.ejectCargo();
  }
  //if(cargoMech)

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return 
      cargoMech.hasCargo() ||
      timeSinceInitialized() > 10 ||
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
