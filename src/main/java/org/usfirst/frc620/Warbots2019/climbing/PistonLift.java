/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class PistonLift extends Subsystem implements ClimbingMechanism
{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  Solenoid piston;

  public PistonLift(int canID, int port)
  {
    piston = new Solenoid(canID, port);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void raise() {
    piston.set(true);
  }

  @Override
  public void lower() {
    piston.set(false);
  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isLowered() {
    return !piston.get();
  }

  @Override
  public boolean isRaised() {
    return piston.get();
  }
}
