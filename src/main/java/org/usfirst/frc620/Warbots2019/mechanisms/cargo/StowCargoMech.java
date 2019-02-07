/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class StowCargoMech extends Command {
  public boolean isCaptured = false;

  public StowCargoMech() {
      // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.cargoMech);
  }

  //Raises the Mech
  public void raiseCargoMech(){
  
  }
  /* 
  Called just before this Command runs the first time, should check if 
  cargo is captured with the boolean isCaptured
  */
  @Override
  protected void initialize() {
  
  }

  /* Called repeatedly when this Command is scheduled to run,
  and should only run if isCaptured is true
  */
  @Override
  protected void execute() {
    if(isCaptured)
    {
      Robot.cargoMech.raiseCargoMech();
    }
  }
//h
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

  return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}