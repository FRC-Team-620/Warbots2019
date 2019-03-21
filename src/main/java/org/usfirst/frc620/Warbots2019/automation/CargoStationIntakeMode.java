/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc620.Warbots2019.automation.CargoScoringMode.CargoScoringLevel;
import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorToAndStop;
import org.usfirst.frc620.Warbots2019.mechanisms.StowScoringMechanismCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberCaptureCommand;

/**
 * Add your docs here.
 */
public class CargoStationIntakeMode extends IntakeMode 
{
    public CargoStationIntakeMode()
    {
        // addSequential(new MoveElevatorToAndStop(4000));
        // addSequential(new StowScoringMechanismCommand());
        addSequential(new GrabberCaptureCommand());
        // addSequential(new StowScoringMechanismCommand());
    }
}
