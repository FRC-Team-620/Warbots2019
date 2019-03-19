/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc620.Warbots2019.automation.ScaleHabClosedLoopCommand;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveWithJoystickCommand extends Command {

    private static enum SHUFFLEBOARD_OPTIONS {
        frontCurvatureArc(0.3),
        backCurvatureArc(0.3),
        frontCurvatureCoefficient(0.5),
        backCurvatureCoefficient(0.5),

        fastSpeedCoefficient(1.0), 
        slowSpeedCoefficient(0.5), 
        minSpeedCoefficient(0.1), 
        speedDeadzone(0.2),
        speedDeadzoneWhileTurning(0.1), 
        speedCurve(0.9),

        fastTurnCoefficient(.75), 
        slowTurnCoefficient(.38), 
        minTurnCoefficient(0.1), 
        turnDeadzone(0.2),
        turnDeadzoneWhileMoving(0.1), 
        turnCurve(1.2);

        private double defaultValue;

        private SHUFFLEBOARD_OPTIONS(double defaultValue) {
            this.defaultValue = defaultValue;
        }

        public double getDefaultValue() {
            return defaultValue;
        }
    }

    private Map<SHUFFLEBOARD_OPTIONS, Double> shuffleboardValues;

    boolean useSlowSpeed = false;
    boolean useSlowTurning = false;

    double speedCoeff = 1.0;
    double turnCoeff = -1.0;

    double centerDZ;
    double rotationDZ;
    double straightDZ;

    public DriveWithJoystickCommand() {
        shuffleboardValues = new HashMap<>();
        for (var value : SHUFFLEBOARD_OPTIONS.values()) {
            SmartDashboard.putNumber(value.toString(), value.getDefaultValue());
            shuffleboardValues.put(value, value.getDefaultValue());
        }

        Logger.log("New Command: " + this.getName());
        ControlReader config = Robot.config;
        centerDZ = config.getMappedDouble("driver.center_deadzone");
        rotationDZ = config.getMappedDouble("driver.rotation_deadzone");
        straightDZ = config.getMappedDouble("driver.speed_deadzone");
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // Shuffleboard.selectTab();
        // SmartDashboard.putNumber("speedCoefficient", speedCoeff);
        // SmartDashboard.putNumber("turnCoefficient", turnCoeff);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.oi.getDriverController().getRawButtonPressed(1))
            Scheduler.getInstance().add(new ScaleHabClosedLoopCommand());

        for (var value : SHUFFLEBOARD_OPTIONS.values()) {
            shuffleboardValues.put(value, SmartDashboard.getNumber(value.toString(), value.getDefaultValue()));
        }

        if (Robot.oi.getDriverController().getRawButtonPressed(5))
            useSlowSpeed = !useSlowSpeed;
        if (Robot.oi.getDriverController().getRawButtonPressed(6))
            useSlowTurning = !useSlowTurning;

        speedCoeff = useSlowSpeed ? shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.slowSpeedCoefficient)
                : shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.fastSpeedCoefficient);
        turnCoeff = useSlowTurning ? shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.slowTurnCoefficient)
                : shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.fastTurnCoefficient);

        if (Robot.oi.getDriverController().getRawButton(9))
            turnCoeff = 1;

        double speed = Robot.oi.getRobotSpeed();
        double turning = Robot.oi.getRobotRotationRate();
        double staticSpeedDeadzone = shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.speedDeadzone);
        double staticTurningDeadzone = shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.turnDeadzone);
        if (Math.abs(speed) < staticSpeedDeadzone && Math.abs(turning) < staticTurningDeadzone)
        {
            Robot.driveTrain.drive(0, 0);
        } else {
            double angle = Math.atan2(turning, -speed);
            double frontCurvatureArc = shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.frontCurvatureArc);
            double backCurvatureArc = 1 - shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.backCurvatureArc);

            if (Math.abs(angle) < frontCurvatureArc * Math.PI) {
                speed = Math.copySign(Math.sqrt(speed * speed + turning * turning), speed);
                speed = curveSpeed(speed);
                angle = curveTurning(angle);
                double arcCoefficient = shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.frontCurvatureCoefficient);
                Robot.driveTrain.curvatureDrive(speed * speedCoeff, -arcCoefficient * turnCoeff * angle);
            } else if (Math.abs(angle) > backCurvatureArc * Math.PI) {
                speed = Math.copySign(Math.sqrt(speed * speed + turning * turning), speed);
                angle = Math.copySign(Math.PI - Math.abs(angle), angle);
                speed = curveSpeed(speed);
                angle = curveTurning(angle);
                double arcCoefficient = shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.backCurvatureCoefficient);
                Robot.driveTrain.curvatureDrive(speed * speedCoeff, -arcCoefficient * turnCoeff * angle);
            } else {
                // These speed/rotation -1.0 to 1.0
                speed = curveSpeed(speed);
                turning = curveTurning(turning);
                Robot.driveTrain.drive(speedCoeff * speed, turnCoeff * turning);
            }
        }
    }

    private double curve(double x, double deadzone, double min, double curve) {
        double absVal = Math.abs(x);
        if (absVal < deadzone)
            absVal = 0;
        else {
            absVal = (absVal - deadzone) / (1 - deadzone);
            absVal = Math.pow(absVal, curve);
            absVal = min + absVal * (1 - min);
        }

        return Math.copySign(absVal, x);
    }

    private double curveSpeed(double speed) {
        return curve(
            speed, 
            shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.speedDeadzoneWhileTurning),
            shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.minSpeedCoefficient),
            shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.speedCurve)
        );
    }

    private double curveTurning(double turning) {
        return curve(
            turning, 
            shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.turnDeadzoneWhileMoving),
            shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.minTurnCoefficient),
            shuffleboardValues.get(SHUFFLEBOARD_OPTIONS.turnCurve)
        );
    }

    private boolean isInCenterDeadzone(double x, double y) {
        return (x <= centerDZ && x >= -centerDZ && y <= centerDZ && y >= -centerDZ);
    }

    private boolean isInStraightDeadzone(double a) {
        boolean isInDZ = false;
        if ((a > Math.PI / 2 - straightDZ && a < Math.PI / 2 + straightDZ)
                || (a > -Math.PI / 2 - straightDZ && a < -Math.PI / 2 + straightDZ)) {
            isInDZ = true;
        }
        return isInDZ;
    }

    private boolean isInRotationDeadzone(double a) {
        boolean isInDZ = false;
        if ((a > -Math.PI && a < (-Math.PI + rotationDZ)) || (a > -rotationDZ && a < rotationDZ)
                || (a > (Math.PI - rotationDZ) && a < Math.PI)) {
            isInDZ = true;
        }
        return isInDZ;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        boolean ret = false;
        if (ret) {
            Logger.log("Command: [" + this.getName() + "] done");
        }
        return ret;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Slow speed", () -> useSlowSpeed, null);
        builder.addBooleanProperty("Slow turning", () -> useSlowTurning, null);
    }
}
