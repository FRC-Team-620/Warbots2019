/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;
import org.usfirst.frc620.Warbots2019.mechanisms.StowScoringMechanismCommand;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberEjectCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Add your docs here.
 */
public class CargoScoringMode implements ScoringMode {

    private double height;

    public CargoScoringMode(double height)
    {
        this.height = height;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Command getPrepareEjectCommand() {
        CommandGroup group = new CommandGroup();
        group.addSequential(new StowScoringMechanismCommand());
        group.addSequential(new MoveElevatorTo(height));
        return group;
    }

    @Override
    public Command getCompleteEjectCommand() {
        return new GrabberEjectCommand();
    }
}
