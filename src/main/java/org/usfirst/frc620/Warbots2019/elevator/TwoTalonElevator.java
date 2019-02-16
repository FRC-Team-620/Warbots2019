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

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class TwoTalonElevator extends Elevator {

    private WPI_TalonSRX talon;

    public TwoTalonElevator(int masterCanID, int slaveCanID) 
    {
        talon = new WPI_TalonSRX(masterCanID);
        talon.configFactoryDefault();
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        WPI_TalonSRX slave = new WPI_TalonSRX(slaveCanID);
        slave.configFactoryDefault();
        // slave.follow(talon);
    }

    @Override
    public void drive(double speed) {
        System.out.println(speed);
        talon.set(speed);
    }

    @Override
    public double getHeight() {
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
