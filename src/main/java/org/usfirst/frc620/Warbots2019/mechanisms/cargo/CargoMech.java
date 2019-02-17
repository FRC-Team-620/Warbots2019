/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class CargoMech extends Subsystem {

  private SpeedController intakeWheels;
  private DigitalInput limitSwitch;
  private CargoMech cargoMech;
  private Solenoid mainPiston;
  // for TestBot
  public CargoMech(int mainMotorPort, int limitSwitchPort) {

    SpeedController mainWheels = new Spark(mainMotorPort);

    // I dont think we'll have a limit switch on the robot, at least with CargoMech (not sure about hatch)
    limitSwitch = new DigitalInput(limitSwitchPort);
    //Uses the port for the wheels to instansiate the mainWheels
    intakeWheels = new SpeedControllerGroup(mainWheels);
  }

  //for this years robot
  public CargoMech(int mainMotorPort, int limitSwitchPort, int mainPistonPort /* or a motor port */, int PCMCanID ) {

    SpeedController mainWheels = new Spark(mainMotorPort);
    
    limitSwitch = new DigitalInput(limitSwitchPort);
    //Uses the port for the wheels to instansiate the mainWheels
    intakeWheels = new SpeedControllerGroup(mainWheels);
    //I don't know if we're using a piston or motor to deploy and stow CargoMech
    mainPiston = new Solenoid(PCMCanID, mainPistonPort);
  }
  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    System.out.print("Working");
  }

  public void idle() {
    cargoMech.stowMech();
  }

  public boolean hasCargo() {
    return limitSwitch.get();
  }

  public void captureCargo(double cmspeed) {
    intakeWheels.set(cmspeed);
  }

  public void stopCapture(double cmspeed) {
    intakeWheels.set(cmspeed);
  }

  public void ejectCargo(double cmspeed) {
    intakeWheels.set(-cmspeed);
  }

  public void deployMech() {
     mainPiston.set(true);
  }

  public void stowMech() {
     mainPiston.set(false);
  }
}
