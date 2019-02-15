/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class ScissorLift extends Subsystem implements ClimbingMechanism {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX talon;

  public ScissorLift(int talonCanID)
  {
    talon = new WPI_TalonSRX(talonCanID);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void raise() {
    talon.set(ControlMode.PercentOutput, 1);
  }

  @Override
  public void lower() {
    talon.set(ControlMode.PercentOutput, -1);
  }

  @Override
  public void stop() {
    talon.stopMotor();
  }

  @Override
  public boolean isRaised() {
    return talon.getSensorCollection().isFwdLimitSwitchClosed();
  }

  @Override
  public boolean isLowered() {
    return talon.getSensorCollection().isRevLimitSwitchClosed();
  }

  public void drive(double value) {
    talon.set(ControlMode.PercentOutput, value);
  }
}
