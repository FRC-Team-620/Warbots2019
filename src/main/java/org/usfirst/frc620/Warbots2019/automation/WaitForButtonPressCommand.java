/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForButtonPressCommand extends Command 
{
  private GenericHID joystick;
  private int button;

  public WaitForButtonPressCommand(GenericHID joystick, int button) 
  {
    this.joystick = joystick;
    this.button = button;
  }

  @Override
  protected boolean isFinished() {
    return joystick.getRawButton(button);
  }
}
