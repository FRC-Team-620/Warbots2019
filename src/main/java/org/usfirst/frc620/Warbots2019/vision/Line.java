/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import java.time.chrono.ThaiBuddhistEra;

/**
 * Add your docs here.
 */
public class Line 
{
    protected Line()
    {

    }
    public Line (double x1, double y1, double x2, double y2)
    {
        if (!(x2 - x1 == 0)) this(mFind(x1, y1, x2, y2), bFind(x1, y1, x2, y2));
            
        else 
        {
            isLine = false;
            B = x1;
        }

    }

    public static Line getNewInst(double x1, double y1, double x2, double y2)
    {
        Line line;
        if (x2 - x1 == 0)
        {
            line = new Line();
            line.isLine = false;
            line.B = x1;
        }
            
        else 
        {
            line = getNewInst(mFind(x1, y1, x2, y2), bFind(x1, y1, x2, y2));
        }

        return line;
    }
    
    public static Line getNewInst (double m, double b)
    {
        Line line = new Line();
        line.B = 
    }
    private static double mFind (double x1, double y1, double x2, double y2)
    {
        return (y2-y1) / (x2-x1);
    }

    private static double bFind (double x1, double y1, double x2, double y2)
    {
        return -1 * ((mFind(x1, y1, x2, y2) * x1) - y1);
    }
    
    public Line (Point a, Point b)
    {
        this(a.getX(), a.getY(), b.getX(), b.getY());
    }
    public Line (double m, double b)
    {
        M = m;
        B = b;
    }

    public Line getPerpendicular (double x, double y)
    {
        if (!this.isLine) return new Line (1, y, 2, y);
        if (this.M == 0) return new Line(x, 1, x, 2);
        double m = -1 / this.M;
        double b = -1 * ((m * x) - y);
        return new Line (m, b);
    }

    public boolean isLine() 
    {
        return isLine;
    }

    public Point getPoint (double x)
    {
        return new Point(x, (M * x + B));
    }

    private double M;
    private double B; //if !isLine => horizontal offset
    private boolean isLine;
}
