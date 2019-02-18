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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class TwoTalonElevator extends Elevator {

    private WPI_TalonSRX talon;

    public TwoTalonElevator(int masterCanID, int slaveCanID) 
    {
        System.out.println("Loaded two talon elevator");
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

        // SmartDashboard.putData(pidConfig);
    }

    @Override
    public void drive(double speed) 
    {
        if (Math.abs(speed) < 0.3)
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

    private class SendablePIDConfig extends SendableBase
    {
        private double p, i, d, f, ramp, peak;
        private int maxError, integralZone;

        public SendablePIDConfig()
        {
            setName("Elevator PID configuration");
        }

        @Override
        public void initSendable(SendableBuilder builder) 
        {
            builder.addDoubleProperty("Target", this::getTarget, null);
            builder.addDoubleProperty("Error", this::getClosedLoopError, null);
            builder.addDoubleProperty("Error Derivative", this::getErrorDerivative, null);
            builder.addDoubleProperty("Error Accumulator", this::getIntegralAccumulator, null);
            builder.addDoubleProperty("P", this::getP, this::setP);
            builder.addDoubleProperty("I", this::getI, this::setI);
            builder.addDoubleProperty("D", this::getD, this::setD);
            builder.addDoubleProperty("F", this::getF, this::setF);
            builder.addDoubleProperty("Ramp Rate", this::getRamp, this::setRamp);
            builder.addDoubleProperty("Peak Output", this::getPeak, this::setPeak);
            builder.addDoubleProperty("Max Acceptable Error", this::getMaxError, error -> this.setMaxError((int) error));
            builder.addDoubleProperty("Integral Zone", this::getIntegralZone, zone -> this.setIntegralZone((int) zone));
        }

        private void setP(double p)
        {
            this.p = p;
            talon.config_kP(0, p);
        }

        private double getP()
        {
            return p;
        }

        public void setI(double i) 
        {
            this.i = i;
            talon.config_kI(0, i);
        }

        public double getI()
        {
            return i;
        }

        public void setD(double d) 
        {
            this.d = d;
            talon.config_kD(0, d);
        }

        public double getD() 
        {
            return d;
        }

        public void setF(double f) 
        {
            this.f = f;
            talon.config_kF(0, f);
        }

        public double getF() 
        {
            return f;
        }

        public void setMaxError(int maxError) 
        {
            this.maxError = maxError;
            talon.configAllowableClosedloopError(0, maxError, 0);
        }

        public int getMaxError()
        {
            return maxError;
        }

        public void setPeak(double peak) 
        {
            this.peak = peak;
            talon.configClosedLoopPeakOutput(0, peak);
        }

        public double getPeak() 
        {
            return peak;
        }
    
        public void setRamp(double ramp) 
        {
            this.ramp = ramp;
            talon.configClosedloopRamp(ramp);
        }

        public double getRamp() 
        {
            return ramp;
        }

        public double getClosedLoopError()
        {
            return talon.getClosedLoopError();
        }

        public double getErrorDerivative()
        {
            return talon.getErrorDerivative();
        }

        public double getIntegralAccumulator()
        {
            return talon.getIntegralAccumulator();
        }

        public double getTarget()
        {
            return talon.getClosedLoopTarget();
        }

        public void setIntegralZone(int integralZone)
        {
            this.integralZone = integralZone;
            talon.config_IntegralZone(0, integralZone);
        }

        public int getIntegralZone() 
        {
            return integralZone;
        }
    }
}
