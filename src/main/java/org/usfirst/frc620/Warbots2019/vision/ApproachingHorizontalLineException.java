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
public class ApproachingHorizontalLineException extends Exception
{
    private static final long serialVersionUID = 2731935013378995941L;

    public ApproachingHorizontalLineException()
    {
        super("");
    }

    public ApproachingHorizontalLineException (String message)
    {
        super(message);
    }
}
