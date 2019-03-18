/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.elevator.Elevator.ElevatorLevel;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberCaptureCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberCloseLatchCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberOpenLatchCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberStopCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberStowCommand;
import org.usfirst.frc620.Warbots2019.utility.DelayCommand;
import org.usfirst.frc620.Warbots2019.climbing.RaiseClimbingMech;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveStraightDistancePIDCommand;
import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbHabCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClimbHabCommandGroup() 
  {
    addSequential(new MoveElevatorTo(10000));
    addSequential(new ScaleHabClosedLoopCommand());
    addSequential(new GrabberOpenLatchCommand());
    addSequential(new GrabberCaptureCommand());
    addSequential(new DelayCommand(2));
    addSequential(new GrabberCloseLatchCommand());
    addSequential(new GrabberStopCommand());
    addSequential(new GrabberStowCommand());
    addSequential(new DriveStraightDistancePIDCommand());
    addSequential(new RaiseClimbingMech());
  }
}
