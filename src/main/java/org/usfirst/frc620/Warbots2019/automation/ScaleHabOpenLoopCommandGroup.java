/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.climbing.LowerClimbingMech;
import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;
import org.usfirst.frc620.Warbots2019.elevator.Elevator.ElevatorLevel;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleHabOpenLoopCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ScaleHabOpenLoopCommandGroup() {
    addParallel(new MoveElevatorTo(ElevatorLevel.FLOOR));
    addSequential(new LowerClimbingMech());
  }
}
