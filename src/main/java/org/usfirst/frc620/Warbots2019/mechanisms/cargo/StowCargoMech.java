/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc620.Warbots2019.mechanisms.cargo;
//import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

public class StowCargoMech extends Command {

  private CargoMech cargoMech;
  //private DigitalInput limitSwitch;
  public boolean isCaptured = false;
  
  public StowCargoMech(CargoMech cargoMech) {
      // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // for the limit switch
    // limitSwitch = new DigitalInput();
    this.cargoMech = cargoMech;

    requires(this.cargoMech);
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
    if(isCaptured = true)
    {
      this.cargoMech.raiseCargoMech();
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