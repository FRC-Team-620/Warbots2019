/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import java.util.List;

import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorToAndStop;
import org.usfirst.frc620.Warbots2019.mechanisms.DeployScoringMechanismCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.StowScoringMechanismCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.HatchCaptureCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Not yet useable
 */
public class HatchFloorIntakeMode extends IntakeMode 
{
    public HatchFloorIntakeMode() {
        addSequential(new DeployScoringMechanismCommand());
        addSequential(new MoveElevatorToAndStop(300));
        addSequential(new HatchCaptureCommand());
        addSequential(new MoveElevatorToAndStop(1000));
        addSequential(new StowScoringMechanismCommand());
    }
}
