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
public class LineProcessor 
{
    public Line Process (Line screenLine)
    {
        Point screenPoint1 = screenLine.getPoint(-50);
        Point screenPoint2 = screenLine.getPoint(50);

        //math to go from screen to floor
        Point floorPoint1 = screenPoint1;
        Point floorPoint2 = screenPoint2;

        //how to get line
        Line floorLine = Line.getNewInst(floorPoint1, floorPoint2);
        return floorLine;
    }
    
    // private final int Rows = 100;
    // private final int Columns = 50;
}


