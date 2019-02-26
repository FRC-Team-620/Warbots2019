/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.Angle;
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
            line.IsFunction = false;
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
        return line;
    }
    
    public static Line getNewInst (Point a, Point b)    
    {
        return getNewInst(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public Line getPerpendicular (double x, double y)
    {
        if (!this.IsFunction) return getNewInst(1.0 , y, 2.0, y);
        if (this.M == 0) return getNewInst(x, 1.0, x, 2.0);
        double m = -1 / this.M;
        double b = -1 * ((m * x) - y);
        return getNewInst(m, b);
    }

    public Line getPerpendicular (Point a)
    {
        if (!this.IsFunction) return getNewInst(1.0 , a.getY(), 2.0, a.getY());
        if (this.M == 0) return getNewInst(a.getX(), 1.0, a.getX(), 2.0);
        double m = -1 / this.M;
        double b = -1 * ((m * a.getX()) - a.getY());
        return getNewInst(m, b);
    }

    public boolean IsFunction() 
    {
        return IsFunction;
    }

    public Point getPoint (double x) throws Exception
    {
        if (!IsFunction) throw new IsNotFunctionException();
        return new Point(x, (M * x + B));
    }

    public double getB() throws Exception
    {
        if (!IsFunction) throw new IsNotFunctionException();
        return B;
    }

    public double getM() throws Exception
    {
        if (!IsFunction) throw new IsNotFunctionException();
        return M;
    }

    public Point getXIntercept() throws Exception
    {
        if (!this.IsFunction) return new Point(B,0);
        if (!this.getPerpendicular(this.getPoint(12)).IsFunction) throw new ApproachingHorizontalLineException();
        return this.getPoint((-1*B) / M);
    }

    public Angle getAngle()
    {
        if (!IsFunction) return Angle.fromDegrees(90);
        return Angle.fromSlope(M);
    }

    private double M;
    private double B; //if !IsFunction => horizontal offset 
    private boolean IsFunction;
}
