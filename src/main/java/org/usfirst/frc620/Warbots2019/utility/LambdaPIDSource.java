/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Add your docs here.
 */
public class LambdaPIDSource implements PIDSource {

    private PIDSourceType type;
    private DoubleSupplier source;

    public LambdaPIDSource(PIDSourceType type, DoubleSupplier source)
    {
        this.type = type;
        this.source = source;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        throw new UnsupportedOperationException("Source type cannot be changed.");
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return type;
    }

    @Override
    public double pidGet() {
        return source.getAsDouble();
	}
}
