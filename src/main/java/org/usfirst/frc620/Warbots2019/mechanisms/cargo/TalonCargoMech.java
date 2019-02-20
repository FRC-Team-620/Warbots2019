/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class TalonCargoMech extends CargoMech {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX intakeWheels;
  private double cmspeed;

  private Solenoid wrist;

  public TalonCargoMech(int intakeWheelsCanID, int PCMCanID, int wristPistonChannel)
  {
    intakeWheels = new WPI_TalonSRX(intakeWheelsCanID);

    ControlReader config = Robot.config;
    cmspeed = config.getMappedDouble("CargoMechSpeed");
    if (cmspeed == -1)
      cmspeed = 0.1;

    wrist = new Solenoid(PCMCanID, wristPistonChannel);
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
    return intakeWheels.getSensorCollection().isFwdLimitSwitchClosed();
  }

  @Override
  public void captureCargo() {
    intakeWheels.set(-cmspeed);
  }

  @Override
  public void stopCapture() {
    intakeWheels.set(0);
  }

  @Override
  public void ejectCargo() {
    intakeWheels.set(cmspeed);
  }

  @Override
  public void deploy() {
    wrist.set(true);
  }

  @Override
  public void stow() {
    wrist.set(false);
  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isDeployed() {
    return wrist.get();
  }

  @Override
  public boolean isStowed() {
    return !wrist.get();
  }
}
