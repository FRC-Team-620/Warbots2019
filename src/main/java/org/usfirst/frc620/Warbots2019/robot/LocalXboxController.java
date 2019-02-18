/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.robot;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Add your docs here.
 * DO NOT USE,
 * DO NOT IMPLEMENT!
 * THERE IS NO TIME TO IMPLEMENT THIS CLASS!
 * 
 */
@Deprecated
public class LocalXboxController extends GenericHID
{
    public LocalXboxController(int index)
    {
        super(index);
    }

    @Override
    public double getRawAxis(int axis) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean getRawButton(int button) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean getRawButtonPressed(int button) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean getRawButtonReleased(int button) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int getPOV(int pov) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int getAxisCount() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int getPOVCount() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int getButtonCount() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public HIDType getType() {
        return HIDType.kHIDGamepad;
    }

    @Override
    public String getName() {
        return "Local Xbox Controller";
    }

    @Override
    public int getAxisType(int axis) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int getPort() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setOutput(int outputNumber, boolean value) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setOutputs(int value) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setRumble(RumbleType type, double value) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public double getX(Hand hand) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public double getY(Hand hand) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
