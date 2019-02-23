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
            double m = (y2-y1) / (x2-x1);
            double b = -1 * (m - y1);
            line = getNewInst(m, b);
        }

        return line;
    }
    
    public static Line getNewInst (double m, double b)
    {
        Line line = new Line();
        line.B = b;
        line.M = m;
        line.isLine = true;
        return line;
    }
    
    public static Line getNewInst (Point a, Point b)
    {
        return getNewInst(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public Line getPerpendicular (double x, double y)
    {
        if (!this.isLine) return getNewInst(1.0 , y, 2.0, y);
        if (this.M == 0) return getNewInst(x, 1.0, x, 2.0);
        double m = -1 / this.M;
        double b = -1 * ((m * x) - y);
        return getNewInst(m, b);
    }

    public Line getPerpendicular (Point a)
    {
        if (!this.isLine) return getNewInst(1.0 , a.getY(), 2.0, a.getY());
        if (this.M == 0) return getNewInst(a.getX(), 1.0, a.getX(), 2.0);
        double m = -1 / this.M;
        double b = -1 * ((m * a.getX()) - a.getY());
        return getNewInst(m, b);
    }

    public boolean isLine() 
    {
        return isLine;
    }

    public Point getPoint (double x)
    {
        return new Point(x, (M * x + B));
    }

    @Override
    public String toString()
    {
        if (isLine)
            return "line[y = (" + M + ")x + (" + B + ")]";
        else
            return "line[x = (" + B + ")]";
    }

    private double M;
    private double B; //if !isLine => horizontal offset
    private boolean isLine;
}
