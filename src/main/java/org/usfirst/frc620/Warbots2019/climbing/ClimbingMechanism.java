package org.usfirst.frc620.Warbots2019.climbing;

public interface ClimbingMechanism
{
    public void raise();

    public void lower();

    public void stop();

    public boolean isRaised();

    public boolean isLowered();
}