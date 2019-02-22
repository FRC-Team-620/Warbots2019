/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import org.ejml.simple.SimpleMatrix;
import org.usfirst.frc620.Warbots2019.vision.Line;

/**
 * This class can be used to calculate weighted linear regressions.
 * 
 * A single instance of this class should be created and then reused
 * for all regression calculations.
 * 
 * This class is not currently optimized for performance. It could
 * be if neccessary.
 */
public class WeightedLinearRegressionCalculator 
{
    public Line calculateWeightedLinearRegression(double[] xs, double[] ys, double[] weights)
    {
        //Verify input arrays have the same size
        int numberOfDataPoints = xs.length;
        if (numberOfDataPoints != ys.length || numberOfDataPoints != weights.length)
            throw new IllegalArgumentException("Incompatible array sizes.");

        //Initialize relevant matrices
        var metric = SimpleMatrix.diag(weights);

        var linearFunctionBasis = new SimpleMatrix(numberOfDataPoints, 2);
        linearFunctionBasis.fill(1);
        linearFunctionBasis.setColumn(1, 0, xs);

        var exactFitFunctionVector = new SimpleMatrix(numberOfDataPoints, 1);
        exactFitFunctionVector.setColumn(0, 0, ys);

        //Project exact function onto the plane of linear functions according to the metric
        var projection =
            linearFunctionBasis
                .transpose()
                .mult(metric)
                .mult(linearFunctionBasis)
                .invert()
                .mult(linearFunctionBasis.transpose())
                .mult(metric)
                .mult(exactFitFunctionVector);

        //Create a line from the components of the projection and  it
        return new Line(projection.get(1, 0), projection.get(0, 0));
    }
}
