/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberEjectCommand;

/**
 * Add your docs here.
 */
public class CargoScoringMode extends BlankScoringMode 
{
    public static enum CargoScoringLevel
    {
        TOP(18000),
        MIDDLE(10000),
        BOTTOM(2000);

        private double height;

        private CargoScoringLevel(double height)
        {
            this.height = height;
        }
    }

    public CargoScoringMode(CargoScoringLevel level)
    {
        super(level.height);
        addSequential(new GrabberEjectCommand());
    }
}
