/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class ControlScissorLiftWithJoystick extends Command 
{
  ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;

  public ControlScissorLiftWithJoystick()
  {
    Logger.log("New Command: "+this.getName());

    
    requires(scissorLift);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    // TODO - convert this to use a new method on OI, such as
    // getLiftSpeed, similar to getRobotSpeed, so we can map different
    // analog controls to it...
    if ((Robot.oi != null) && (Robot.oi.driverController != null))
    {
        GenericHID joystick = Robot.oi.driverController;

        double speed = joystick.getRawAxis(2) - joystick.getRawAxis(3);
        if (Math.abs(speed) < 0.2)
          speed = -0.5;
        // System.out.println("Drving sicor lift " + speed);
        scissorLift.drive(speed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean ret = false;
    if (ret)
    {
        Logger.log("Command: ["+this.getName()+"] done");
    }
    return ret;
  }
}
