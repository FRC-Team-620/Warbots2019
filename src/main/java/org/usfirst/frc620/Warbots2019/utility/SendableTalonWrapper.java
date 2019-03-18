package org.usfirst.frc620.Warbots2019.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc620.Warbots2019.utility.TableProperty.PropertyType;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */
public class SendableTalonWrapper extends SendableBase implements AutoCloseable
{
    private final TalonReadThread updateThread;
    private final Map<String, ParamEnum> talonPIDSettings;
    private final List<TableProperty> properties;

    private WPI_TalonSRX talon;

    public SendableTalonWrapper(WPI_TalonSRX talon, TalonReadThread thread)
    {
        System.out.println("Wrapping talon with thread " + thread);
        updateThread = thread;
        setName("TalonSRX Speed Controller");
        this.talon = talon;

        talonPIDSettings = Map.ofEntries(
            Map.entry("kP", ParamEnum.eProfileParamSlot_P),
            Map.entry("kI", ParamEnum.eProfileParamSlot_I),
            Map.entry("kD", ParamEnum.eProfileParamSlot_D),
            Map.entry("kF", ParamEnum.eProfileParamSlot_F),
            Map.entry("integralZone", ParamEnum.eProfileParamSlot_IZone),
            Map.entry("maxAccumulatable", ParamEnum.eProfileParamSlot_MaxIAccum),
            Map.entry("peakOutput", ParamEnum.eProfileParamSlot_PeakOutput),
            Map.entry("allowableError", ParamEnum.eProfileParamSlot_AllowableErr),
            Map.entry("motionMagicAcceleration", ParamEnum.eMotMag_Accel),
            Map.entry("motionMagicCruiseVelocity", ParamEnum.eMotMag_VelCruise)
        );

        properties = new ArrayList<>();
        properties.add(new TableProperty(PropertyType.String, "controlMode", () -> talon.getControlMode().toString(), null));
        properties.add(new TableProperty(PropertyType.Double, "output", talon::get, value -> talon.set((Double) value)));

        properties.add(new TableProperty(PropertyType.Double, "sensorPosition", talon::getSelectedSensorPosition, null));
        properties.add(new TableProperty(PropertyType.Double, "sensorVelocity", talon::getSelectedSensorVelocity, null));
        
        for (var entry : talonPIDSettings.entrySet())
            properties.add(new TableProperty(
                PropertyType.Double, 
                entry.getKey(), 
                () -> talon.configGetParameter(entry.getValue(), 0), 
                (value) -> talon.configSetParameter(entry.getValue(), (Double) value, 0, 0)
            ));

        properties.add(new TableProperty(PropertyType.Double, "temperature", talon::getTemperature, null));
        properties.add(new TableProperty(PropertyType.Double, "busVoltage", talon::getBusVoltage, null));
        properties.add(new TableProperty(PropertyType.Double, "outputVoltage", talon::getMotorOutputVoltage, null));
        properties.add(new TableProperty(PropertyType.Double, "outputCurrent", talon::getOutputCurrent, null));
        properties.add(new TableProperty(
            PropertyType.Double, 
            "outputResistance", 
            () -> talon.getMotorOutputVoltage() / talon.getOutputCurrent(), 
            null
        ));

        properties.add(new TableProperty(PropertyType.Boolean, "topLimitSwitch", talon.getSensorCollection()::isFwdLimitSwitchClosed, null));
        properties.add(new TableProperty(PropertyType.Boolean, "bottomLimitSwitch", talon.getSensorCollection()::isRevLimitSwitchClosed, null));

        if (updateThread != null)
            for (var property : properties)
                updateThread.add(property);
    }

    public SendableTalonWrapper(WPI_TalonSRX talon)
    {
        this(talon, TalonReadThread.getDefault());
    }

    @Override
    public void close() 
    {
        if (updateThread != null)
            for (var property : properties)
                updateThread.remove(property);
    }

    public void loadSettingsFromConfig(ControlReader config, String prefix)
    {
        for (var entry : talonPIDSettings.entrySet())
        {
            String key = prefix + "." + entry.getKey();
            double value = config.getMappedDouble(key);
            if (value >= 0)
                talon.configSetParameter(entry.getValue(), value, 0, 0);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        for(var property : properties)
            property.addTo(builder);
    }
}