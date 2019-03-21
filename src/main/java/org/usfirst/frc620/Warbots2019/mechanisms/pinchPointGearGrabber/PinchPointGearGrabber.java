/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.pinchPointGearGrabber;

import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;
import org.usfirst.frc620.Warbots2019.utility.Configurable;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Add your docs here.
 */
public class PinchPointGearGrabber extends ScoringMechanism {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  SpeedController motor;
  DigitalInput lowerLimitSwitch;
  DigitalInput upperLimitSwitch;

  public PinchPointGearGrabber(int motorPort, int lowerLimitSwitchPort, int upperLimitSwitchPort)
  {
    motor = new Spark(motorPort);
    lowerLimitSwitch = new DigitalInput(lowerLimitSwitchPort);
    upperLimitSwitch = new DigitalInput(upperLimitSwitchPort);
  }

  public void deploy()
  {
    motor.set(1);
  }

  public void stow()
  {
    motor.set(-1);
  }

  public void stop()
  {
    motor.set(0);
  }

  public boolean isDeployed()
  {
    return lowerLimitSwitch.get();
  }

  public boolean isStowed()
  {
    return upperLimitSwitch.get();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public static Configurable asConfigurable()
  {
      // We now have a Configurable object with all methods implemented
      // so programs can carry it around like a suitcase
      Configurable configurable = ScoringMechanism.asConfigurable();
      return configurable;
  }
}
