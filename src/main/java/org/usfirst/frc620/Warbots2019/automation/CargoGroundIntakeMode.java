/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.mechanisms.cargo.GrabberCaptureCommand;

/**
 * Add your docs here.
 */
public class CargoGroundIntakeMode extends IntakeMode 
{
    public CargoGroundIntakeMode() 
    {
        // addSequential(new MoveElevatorToAndStop(5400));
        // addSequential(new DeployScoringMechanismCommand());
        addSequential(new GrabberCaptureCommand());
        // addSequential(new StowScoringMechanismCommand());
    }
}
