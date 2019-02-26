/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.WeightedLinearRegressionCalculator;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class LineProcessor
{
    public LineProcessor ()
    {
        linRegCalc = new WeightedLinearRegressionCalculator();
    }
    
    public Line Process ()
    {
        Line line = getProperCoordinates(linRegCalc.calculateWeightedLinearRegression(getXs(), getYs(), getWeights()));
        return line;
    }

    private double[] getEntries (NetworkTableEntry entry)
    {
        return entry.getDoubleArray(new double[0]);
    }

    private double[] getXs ()
    {
        double [] x1s = getEntries(x1Entry);
        double [] x2s = getEntries(x2Entry);

        int numberOfLines = x1s.length;
        double [] xs = new double [2 * numberOfLines]; //2s indices are index + numberOfLines

        for (int i = 0; i < numberOfLines; i++)
        {
            xs[i] = x1s[i];
        }

        for (int i = 0; i < numberOfLines; i++)
        {
            xs[i + numberOfLines] = x2s[i];
        }

        return xs;
    }
    
    private double[] getYs ()
    {
        double [] y1s = getEntries(y1Entry);
        double [] y2s = getEntries(y2Entry);

        int numberOfLines = y1s.length;
        double [] ys = new double [2 * numberOfLines]; //2s indices are index + numberOfLines

        for (int i = 0; i < numberOfLines; i++)
        {
            ys[i] = y1s[i];
        }

        for (int i = 0; i < numberOfLines; i++)
        {
            ys[i + numberOfLines] = y2s[i];
        }

        return ys;
    }

    private double[] getWeights () //first all 1s, then all 2s
    {
        double [] x1s = getEntries(x1Entry);
        double [] x2s = getEntries(x2Entry);
        double [] y1s = getEntries(y1Entry);
        double [] y2s = getEntries(y2Entry);
        int numberOfLines = x1s.length;
        double [] weights = new double [2 * numberOfLines]; //2s indices are index + numberOfLines

        for (int i = 0; i < numberOfLines; i++)
        {
            weights[i] = Math.sqrt(Math.pow((x2s[i] - x1s[i]), 2) + Math.pow((y2s[i] - y1s[i]), 2));
        }

        for (int i = 0; i < numberOfLines; i++)
        {
            weights[i + numberOfLines] = Math.sqrt(Math.pow((x2s[i] - x1s[i]), 2) + Math.pow((y2s[i] - y1s[i]), 2));
        }

        return weights;
    }

    public Line getProperCoordinates (Line originalLine) //assuming is a function
    {
        while(true)
        {
        try
        {
            Line tempLine = Line.getNewInst(originalLine.getM(), originalLine.getB());
            Line finLine = Line.getNewInst(tempLine.getM(), tempLine.getB() - ImageWidth/2);
            return finLine;
        }
        catch(Exception e)
        {

        }
        }

    }

    private NetworkTable lineTrackingData = NetworkTableInstance.getDefault().getTable("GRIP/trackingLines");
    private final NetworkTableEntry x1Entry = lineTrackingData.getEntry("x1");
    private final NetworkTableEntry x2Entry = lineTrackingData.getEntry("x2");
    private final NetworkTableEntry y1Entry = lineTrackingData.getEntry("y1");
    private final NetworkTableEntry y2Entry = lineTrackingData.getEntry("y2");
    private WeightedLinearRegressionCalculator linRegCalc;
    private final int ImageWidth  = 180; //TODO: figure out actual width

}