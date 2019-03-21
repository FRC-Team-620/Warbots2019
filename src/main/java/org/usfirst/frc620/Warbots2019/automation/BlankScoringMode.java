/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.automation.ScoringMode;

/**
 * Add your docs here.
 */
public class BlankScoringMode extends ScoringMode
{
    private double height;

    public BlankScoringMode(double height)
    {
        this.height = height;
    }

    @Override
    public double getHeight() 
    {
        return height;
    }
}
