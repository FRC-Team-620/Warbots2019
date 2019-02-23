/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.util.Map;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */
public class SendableTalonWrapper extends SendableBase
{
    private final static Map<String, ParamEnum> talonPIDSettings = Map.ofEntries(
        Map.entry("kP", ParamEnum.eProfileParamSlot_P),
        Map.entry("kI", ParamEnum.eProfileParamSlot_I),
        Map.entry("kD", ParamEnum.eProfileParamSlot_D),
        Map.entry("kF", ParamEnum.eProfileParamSlot_F),
        Map.entry("integralZone", ParamEnum.eProfileParamSlot_IZone),
        Map.entry("maxAccumulatable", ParamEnum.eProfileParamSlot_MaxIAccum),
        Map.entry("peakOutput", ParamEnum.eProfileParamSlot_PeakOutput),
        Map.entry("allowableError", ParamEnum.eProfileParamSlot_AllowableErr)
    );

    private WPI_TalonSRX talon;

    public SendableTalonWrapper(WPI_TalonSRX talon)
    {
        setName("TalonSRX Speed Controller");
        this.talon = talon;
    }

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        builder.addStringProperty("controlMode", () -> talon.getControlMode().toString(), null);
        builder.addDoubleProperty("output", talon::get, talon::set);

        builder.addDoubleProperty("sensorPosition", talon::getSelectedSensorPosition, null);
        builder.addDoubleProperty("sensorVelocity", talon::getSelectedSensorVelocity, null);

        for (var entry : talonPIDSettings.entrySet())
            builder.addDoubleProperty(
                entry.getKey(), 
                () -> talon.configGetParameter(entry.getValue(), 0), 
                (value) -> talon.configSetParameter(entry.getValue(), value, 0, 0)
            );

        builder.addDoubleProperty("temperature", talon::getTemperature, null);
        builder.addDoubleProperty("busVoltage", talon::getBusVoltage, null);
        builder.addDoubleProperty("outputVoltage", talon::getMotorOutputVoltage, null);
        builder.addDoubleProperty("outputCurrent", talon::getOutputCurrent, null);
        builder.addDoubleProperty(
            "outputResistance", 
            () -> talon.getMotorOutputVoltage() / talon.getOutputCurrent(), 
            null
        );
    }
}
