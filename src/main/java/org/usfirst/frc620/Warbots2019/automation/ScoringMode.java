/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public interface ScoringMode {

    public double getHeight();

    public Command getPrepareEjectCommand();

    public Command getCompleteEjectCommand();
}
