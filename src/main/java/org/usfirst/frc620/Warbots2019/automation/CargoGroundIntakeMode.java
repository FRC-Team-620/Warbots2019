/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;
import org.usfirst.frc620.Warbots2019.mechanisms.DeployScoringMechanismCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.StowScoringMechanismCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberCaptureCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Add your docs here.
 */
public class CargoGroundIntakeMode implements IntakeMode {

    private final static List<ScoringMode> scoringModes = Arrays.asList(
        new CargoScoringMode(8000),
        new CargoScoringMode(12000),
        new CargoScoringMode(15000)
    );

    @Override
    public Command getPrepareIntakeCommand() {
        CommandGroup group = new CommandGroup();
        group.addSequential(new MoveElevatorTo(4000));
        group.addSequential(new DeployScoringMechanismCommand());
        group.addSequential(new GrabberCaptureCommand());
        return group;
    }

    @Override
    public Command getCompleteIntakeCommand() {
        return new StowScoringMechanismCommand();
    }

    @Override
    public boolean wasSuccessful() {
        return true;
    }

    @Override
    public List<ScoringMode> getScoringModes() {
        return scoringModes;
    }
}
