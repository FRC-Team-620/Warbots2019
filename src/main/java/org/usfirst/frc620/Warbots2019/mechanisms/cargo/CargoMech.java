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
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * Add your docs here.
 */
public class CargoMech extends ScoringMechanism {

  private SpeedController intakeWheels;
  private DigitalInput limitSwitch;

  public CargoMech(int mainMotorPort, int limitSwitchPort) {

    SpeedController mainWheels = new Spark(mainMotorPort);

    limitSwitch = new DigitalInput(limitSwitchPort);
    intakeWheels = new SpeedControllerGroup(mainWheels);
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
    intakeWheels.set(-0.5);
  }

  public void stopCapture() {
    intakeWheels.set(0);
  }

  public void ejectCargo() {
    intakeWheels.set(0.5);
  }

  public void deploy() {
    // mainPiston.set(true);
  }

  public void stow() {
    // mainPiston.set(false);
  }

  @Override
  public void stop() {
    stopCapture();
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
