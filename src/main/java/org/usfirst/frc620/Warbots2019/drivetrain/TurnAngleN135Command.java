/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class TurnAngleN135Command extends Command 
{

  public TurnAngleN135Command() 
  {
    Logger.log("New Command: "+this.getName());
  }


  // Called once when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    TurnAnglePIDCommand cmd = new TurnAnglePIDCommand(Angle.fromDegrees(135));
    Scheduler.getInstance().add(cmd);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }
}
