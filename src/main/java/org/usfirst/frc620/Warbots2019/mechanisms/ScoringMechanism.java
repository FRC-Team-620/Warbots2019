/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.Configurable;
/**
 * Add your docs here.
 */
public abstract class ScoringMechanism extends Subsystem 
{
    protected ConfigurableImpl configurable;

    public ScoringMechanism()
    {
        configurable = new ConfigurableImpl();
    }

    public abstract void deploy();

    public abstract void stow();

    public abstract void stop();

    public abstract boolean isDeployed();

    public abstract boolean isStowed();

    public Configurable asConfigurable() {
        return configurable;
    }
}
