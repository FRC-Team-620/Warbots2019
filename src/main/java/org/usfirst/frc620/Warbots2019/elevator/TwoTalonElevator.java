/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package org.usfirst.frc620.Warbots2019.elevator;

import java.util.Map;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc620.Warbots2019.utility.Logger;

/**
 *
 */
public class TwoTalonElevator extends TalonElevator 
{
    private final static Map<ElevatorLevel, Integer> HEIGHTS = Map.ofEntries(
        Map.entry(ElevatorLevel.FLOOR, 0),
        Map.entry(ElevatorLevel.MIDDLE, 10000),
        Map.entry(ElevatorLevel.TOP, 20000)
    );

    private WPI_TalonSRX talon;

    public TwoTalonElevator(int masterCanID, int slaveCanID) 
    {
        super(masterCanID);
        Logger.log("Loaded two talon elevator");
        talon = super.getTalon();

        WPI_TalonSRX slave = new WPI_TalonSRX(slaveCanID);
        TalonSRXConfiguration masterConfiguration = new TalonSRXConfiguration();
        talon.getAllConfigs(masterConfiguration);
        slave.configAllSettings(masterConfiguration);
        slave.follow(talon);
    }

    @Override
    public double getHeight(ElevatorLevel level) 
    {
        return HEIGHTS.get(level);
    }
}
