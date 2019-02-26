/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

/**
 * Add your docs here.
 */
public class IsNotFunctionException extends Exception
{
    public IsNotFunctionException ()
    {
        super("");
    }
    public IsNotFunctionException (String message)
    {
        super(message);
    }
}
