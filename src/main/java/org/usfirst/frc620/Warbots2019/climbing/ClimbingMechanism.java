package org.usfirst.frc620.Warbots2019.climbing;

import edu.wpi.first.wpilibj.Sendable;

public interface ClimbingMechanism extends Sendable
{
    public void raise();

    public void lower();

    public void stop();

    public boolean isRaised();

    public boolean isLowered();
}