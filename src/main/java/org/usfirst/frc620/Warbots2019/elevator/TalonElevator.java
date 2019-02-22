// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc620.Warbots2019.elevator;

import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;

/**
 *
 */
public class TalonElevator extends Elevator
{
    private WPI_TalonSRX talon;
    private double speedFactor;

    public TalonElevator(int canID) 
    {
        ControlReader config = Robot.config;
        speedFactor = 1.0;

        System.out.println("Loaded talon elevator");
        talon = new WPI_TalonSRX(canID);
        talon.configFactoryDefault();

        if (config.getMappedString("elevator.speed_factor") != null)
        {
            speedFactor = config.getMappedDouble("elevator.speed_factor");
        }
    }

    @Override
    public void drive(double speed) 
    {
        talon.set(speedFactor * speed);
    }

    @Override
    public void driveTo(double height) 
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public double getHeight() 
    {
        talon.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0);
        return talon.getSelectedSensorPosition();
    }

    @Override
    public boolean isAtTop() {
        return talon.getSensorCollection().isFwdLimitSwitchClosed();
    }

    @Override
    public boolean isAtBottom() {
        return talon.getSensorCollection().isRevLimitSwitchClosed();
    }
}
