/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package org.usfirst.frc620.Warbots2019.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc620.Warbots2019.utility.Logger;

/**
 *
 */
public class TwoTalonElevator extends Elevator {

    private WPI_TalonSRX talon;

    public TwoTalonElevator(int masterCanID, int slaveCanID) 
    {
        Logger.log("Loaded two talon elevator");
        talon = new WPI_TalonSRX(masterCanID);
        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Brake);
        talon.configClearPositionOnLimitR(true, 0);

        WPI_TalonSRX slave = new WPI_TalonSRX(slaveCanID);
        slave.configFactoryDefault();
        slave.setNeutralMode(NeutralMode.Brake);
        slave.follow(talon);

        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        // var pidConfig = new SendablePIDConfig();
        // pidConfig.setPeak(0.5);
        // pidConfig.setMaxError(100);
        // pidConfig.setRamp(0.5);
        // pidConfig.setP(0.1);

        //SmartDashboard.putData(pidConfig);
    }

    @Override
    public void drive(double speed) 
    {
        // System.out.println("Driving elevator " + speed);
        if (Math.abs(speed) < 0.1)
            talon.stopMotor();
        else
            talon.set(speed);
    }

    @Override
    public void driveTo(double height) 
    {
        System.out.println("Driving elevator to " + height);
        System.out.println("Error: " + talon.getClosedLoopError());
        System.out.println("Output: " + talon.getMotorOutputPercent());
        talon.set(ControlMode.Position, height);
    }

    @Override
    public double getHeight() 
    {
        return talon.getSelectedSensorPosition();
    }

    @Override
    public boolean isAtTop()
    {
        return talon.getSensorCollection().isFwdLimitSwitchClosed();
    }

    @Override
    public boolean isAtBottom() 
    {
        return talon.getSensorCollection().isRevLimitSwitchClosed();
    }
}
