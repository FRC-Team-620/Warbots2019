/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Add your docs here.
 */
public class DummyMotor implements SpeedController 
{
    private boolean inverted = false;
    private double speed = 0;

    @Override
    public void pidWrite(double output) {
        set(output);
    }

    @Override
    public void set(double speed) 
    {
        if (speed != this.speed)
        {
            System.out.println("Spinning dummy motor at speed " + speed);
            this.speed = speed;
        }
    }

    @Override
    public double get() {
        return speed;
    }

    @Override
    public void setInverted(boolean isInverted) {
        inverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void disable() {
        System.out.println("Disabling dummy motor");
        speed = 0;
    }

    @Override
    public void stopMotor() {
        System.out.println("Stopping dummy motor.");
        speed = 0;
    }
}
