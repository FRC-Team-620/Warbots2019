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

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;
import org.usfirst.frc620.Warbots2019.utility.SendableTalonWrapper;
import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TalonElevator extends Elevator
{
    private final static Map<ElevatorLevel, Integer> HEIGHTS = Map.ofEntries(
        Map.entry(ElevatorLevel.FLOOR, 0),
        Map.entry(ElevatorLevel.MIDDLE, 6000),
        Map.entry(ElevatorLevel.TOP, 12000)
    );

    private WPI_TalonSRX talon;
    private double speedFactor;

    public TalonElevator(int canID) 
    {
        Logger.log("Loaded talon elevator");
        ControlReader config = Robot.config;

        speedFactor = 1.0;
        if (config.getMappedString("elevator.speed_factor") != null)
            speedFactor = config.getMappedDouble("elevator.speed_factor");

        talon = new WPI_TalonSRX(canID);
        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Brake);
        talon.configClearPositionOnLimitR(true, 50);
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        var talonConfig = new SendableTalonWrapper(talon);
        talonConfig.loadSettingsFromConfig(Robot.config, "Elevator.MotionMagic");
        addChild(talonConfig);
    }

    public WPI_TalonSRX getTalon()
    {
        return talon;
    }

    @Override
    public void drive(double speed) 
    {
        // System.out.println("Driving elevPdator " + speed);
        if (Math.abs(speed) < 0.1)
            talon.stopMotor();
        else
            talon.set(speedFactor * speed);
    }

    @Override
    public void driveTo(ElevatorLevel level) 
    {
        double height = getHeight(level);
System.out.println("Current height: " + talon.getSensorCollection().getQuadraturePosition());
System.out.println("Driving elevator to: " + height);
System.out.println("Error: " + talon.getClosedLoopError());
System.out.println("Output: " + talon.getMotorOutputPercent());
        talon.set(ControlMode.MotionMagic, height);
    }

    @Override
    public void holdCurrentHeight()
    {
        double height = getHeight();
        System.out.println("Holding Elevator at " + height);
        System.out.println("Error: " + talon.getClosedLoopError());
        System.out.println("Output: " + talon.getMotorOutputPercent());
        talon.set(ControlMode.MotionMagic, height);
    }

    double getHeight(ElevatorLevel level)
    {
        return HEIGHTS.get(level);
    }

    @Override
    public double getHeight() 
    {
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

    public static Configurable asConfigurable()
    {
        ConfigurableImpl configurable = (ConfigurableImpl)Elevator.asConfigurable();

        configurable.addElement(new Element("Elevator.MotionMagic.kP", 
            "Talon Elevator Setting: PID Proportional constant", null));

        configurable.addElement(new Element("Elevator.MotionMagic.kI", 
            "Talon Elevator Setting: PID Integral constant", null));

        configurable.addElement(new Element("Elevator.MotionMagic.kD", 
            "Talon Elevator Setting: PID Differential constant", null));

        configurable.addElement(new Element("Elevator.MotionMagic.kF", 
            "Talon Elevator Setting: PID Differential constant", null));

        configurable.addElement(new Element("Elevator.MotionMagic.motionMagicAcceleration", 
            "Talon Elevator Setting: Elevator acceleration encoder cnts/sec^2?", null));

        configurable.addElement(new Element("Elevator.MotionMagic.motionMagicCruiseVelocity", 
            "Talon Elevator Setting: Elevator velocity encoder cnts/sec?", null));
        return configurable;
    }
}
