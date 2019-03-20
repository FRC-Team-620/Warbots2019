/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import java.util.List;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public interface IntakeMode {

    public Command getPrepareIntakeCommand();

    public Command getCompleteIntakeCommand();

    public boolean wasSuccessful();

    public List<ScoringMode> getScoringModes();
}
