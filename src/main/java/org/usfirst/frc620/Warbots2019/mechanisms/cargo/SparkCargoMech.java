/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * Add your docs here.
 */
public class SparkCargoMech extends CargoMech {

  private double cmspeed = 0.7;

  private SpeedController intakeWheels;
  private DigitalInput limitSwitch;
  private Solenoid mainPiston;

  // for TestBot
  public SparkCargoMech(int mainMotorPort, int limitSwitchPort) {

    SpeedController mainWheels = new Spark(mainMotorPort);

    limitSwitch = new DigitalInput(limitSwitchPort);
    intakeWheels = new SpeedControllerGroup(mainWheels);
  }

  // for this years robot
  public SparkCargoMech(int mainMotorPort, int limitSwitchPort, int mainPistonPort, int PCMCanID) {

    SpeedController mainWheels = new Spark(mainMotorPort);
    

    limitSwitch = new DigitalInput(limitSwitchPort);
    intakeWheels = new SpeedControllerGroup(mainWheels);
    mainPiston = new Solenoid(PCMCanID, mainPistonPort);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    System.out.print("Working");
  }

  public void idle() {
    stow();
  }

  public boolean hasCargo() {
    return limitSwitch.get();
  }

  public void captureCargo() {
    intakeWheels.set(cmspeed);
  }

  public void stopCapture() {
    intakeWheels.set(0);
  }

  public void ejectCargo() {
    intakeWheels.set(-cmspeed);
  }

  public void deploy() {
    mainPiston.set(true);
  }

  public void stow() {
    mainPiston.set(false);
  }

  @Override
  public void stop() {
  }

  @Override
  public boolean isDeployed() {
    return true;
  }

  @Override
  public boolean isStowed() {
    return false;
  }
}