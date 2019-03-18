/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.elevator.Elevator.ElevatorLevel;
import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;
import org.usfirst.frc620.Warbots2019.utility.DelayCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabberLatchArmCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public GrabberLatchArmCommandGroup() {
    addSequential(new MoveElevatorTo(1000));
    addSequential(new GrabberOpenLatchCommand());
    addSequential(new GrabberDeployCommand());
    addSequential(new DelayCommand(1));
    addSequential(new GrabberCloseLatchCommand());
  }
}
