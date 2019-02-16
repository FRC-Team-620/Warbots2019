/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class TalonCargoMech extends CargoMech {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX talon;

  public TalonCargoMech(int canID)
  {
    talon = new WPI_TalonSRX(canID);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void idle() {
    stow();
  }

  @Override
  public boolean hasCargo() {
    return talon.getSensorCollection().isFwdLimitSwitchClosed();
  }

  @Override
  public void captureCargo(double cmspeed) {
    talon.set(-cmspeed);
  }

  @Override
  public void stopCapture() {
    talon.set(0);
  }

  @Override
  public void ejectCargo(double cmspeed) {
    talon.set(cmspeed);
  }

  @Override
  public void deploy() {

  }

  @Override
  public void stow() {

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
