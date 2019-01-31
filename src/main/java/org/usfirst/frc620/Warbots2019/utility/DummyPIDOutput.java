/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */
public class DummyPIDOutput extends SendableBase implements PIDOutput
{
    private double value;

    public DummyPIDOutput()
    {
        value = 0;
    }

    @Override
    public void pidWrite(double output) 
    {
        value = output;
    }

    public double getOutput()
    {
        return value;
    }

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        builder.addDoubleProperty("output", this::getOutput, this::pidWrite);
	}
}
