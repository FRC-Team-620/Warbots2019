/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
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
    private final static int INITIAL_CAPACITY = 20;

    private DMatrixRMaj M;
    private DMatrixRMaj A = new DMatrixRMaj(INITIAL_CAPACITY, 2);
    private DMatrixRMaj B = new DMatrixRMaj(INITIAL_CAPACITY, 1);
    private DMatrixRMaj AConjugate = new DMatrixRMaj(2, INITIAL_CAPACITY);
    private DMatrixRMaj ADotA = new DMatrixRMaj(2, 2);
    private DMatrixRMaj ADotAInverse = new DMatrixRMaj(2, 2);
    private DMatrixRMaj modifier = new DMatrixRMaj(2, INITIAL_CAPACITY);
    private DMatrixRMaj x = new DMatrixRMaj(2, 1);

    public WeightedLinearRegressionCalculator()
    {
        for (int i = 0; i < INITIAL_CAPACITY; ++i)
            A.set(i, 0, 1);
    }

    public Line calculateWeightedLinearRegression(double[] xs, double[] ys, double[] weights)
    {
        //Verify input arrays have the same size
        int numberOfDataPoints = xs.length;
        if (numberOfDataPoints != ys.length || numberOfDataPoints != weights.length)
            throw new IllegalArgumentException("Incompatible array sizes.");

        //Initialize relevant matrices
        M = CommonOps_DDRM.diag(weights);

        // var oldNumRows = A.getNumRows();
        A.reshape(numberOfDataPoints, 2);
        for (int i = 0; i < numberOfDataPoints; ++i)
        {
            A.set(i, 0, 1);
            A.set(i, 1, xs[i]);
        }

        B.setNumRows(numberOfDataPoints);
        B.setData(ys);

        //Project exact function onto the plane of linear functions according to the metric
        //(A*MA)'A*Mb

        CommonOps_DDRM.multTransA(A, M, AConjugate);
        CommonOps_DDRM.mult(AConjugate, A, ADotA);
        CommonOps_DDRM.invert(ADotA, ADotAInverse);
        CommonOps_DDRM.mult(ADotAInverse, AConjugate, modifier);
        CommonOps_DDRM.mult(modifier, B, x);

        //Create a line from the components of the projection and  it
        return Line.getNewInst(x.get(1, 0), x.get(0, 0));
    }
}
