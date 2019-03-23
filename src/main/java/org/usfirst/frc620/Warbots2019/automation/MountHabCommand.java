/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.climbing.HoldScissorLiftPositionCommand;
import org.usfirst.frc620.Warbots2019.climbing.RaiseClimbingMech;
import org.usfirst.frc620.Warbots2019.elevator.HoldElevatorPosition;
import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MountHabCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public MountHabCommand() {
    addParallel(new DoNothingWithDriveCommand());
    addSequential(new ScaleHabClosedLoopCommand());
    addParallel(new HoldElevatorPosition());
    addParallel(new HoldScissorLiftPositionCommand(Angle.fromDegrees(10)));
    addSequential(new PullForwardWithCargoMechCommand());
    addParallel(new HoldScissorLiftPositionCommand(Angle.fromDegrees(-1)));
    addSequential(new PullForwardWithDriveTrainCommand());
    addParallel(new DoNothingWithDriveCommand());
    addSequential(new RaiseClimbingMech());
  }
}
