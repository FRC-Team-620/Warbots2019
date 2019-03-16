/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class DriveWithJoystickCommand extends Command {

    boolean useSlowSpeed = false;
    boolean useSlowTurning = false;

    double speedCoeff = 1.0;
    double turnCoeff = -1.0;

    double centerDZ;
    double rotationDZ;
    double straightDZ;

    public DriveWithJoystickCommand() {
        SmartDashboard.putNumber("fastSpeedCoefficient", 1.0);
        SmartDashboard.putNumber("fastTurnCoefficient", .75);
        SmartDashboard.putNumber("slowSpeedCoefficient", 0.5);
        SmartDashboard.putNumber("slowTurnCoefficient", .38);
        SmartDashboard.putNumber("speedDeadZone", 0.2);
        SmartDashboard.putNumber("turnDeadZone", 0.2);
        SmartDashboard.putNumber("speedCurve", 1.2);
        SmartDashboard.putNumber("turnCurve", 1.2);   

        Logger.log("New Command: "+this.getName());
        ControlReader config = Robot.config;
        centerDZ = config.getMappedDouble("driver.center_deadzone");
        rotationDZ = config.getMappedDouble("driver.rotation_deadzone");
        straightDZ = config.getMappedDouble("driver.speed_deadzone");
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //Shuffleboard.selectTab();
        //SmartDashboard.putNumber("speedCoefficient", speedCoeff);
        //SmartDashboard.putNumber("turnCoefficient", turnCoeff);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        speedCoeff = useSlowSpeed? 
            SmartDashboard.getNumber("slowSpeedCoefficient", 0.5) : 
            SmartDashboard.getNumber("fastSpeedCoefficient", 1.0);
        turnCoeff = useSlowTurning?
            SmartDashboard.getNumber("slowTurnCoefficient", .38) : 
            SmartDashboard.getNumber("fastTurnCoefficient", .75);

        double speedDeadZoneShuffleboard = SmartDashboard.getNumber("speedDeadZone", 0.2);
        double turnDeadZoneShuffleboard = SmartDashboard.getNumber("turnDeadZone", 0.2);
        double speedCurveShuffleboard = SmartDashboard.getNumber("speedCurve", 1.2);
        double turnCurveShuffleboard = SmartDashboard.getNumber("turnCurve", 1.2);   

        // These speed/rotation -1.0 to 1.0
        double y_value = Robot.oi.getRobotSpeed();
        y_value = curve(y_value, speedDeadZoneShuffleboard, speedCurveShuffleboard);
        double x_value = Robot.oi.getRobotRotationRate();
        x_value = curve(x_value, turnDeadZoneShuffleboard, turnCurveShuffleboard);
        // double angle = Math.atan2(y_value, x_value);

        // This should be mapped to an OI value thing, like
        // speed and rotation rate are, so that there can be a button mapping
        // like there is for the y_value and x_value above.
        if (Robot.oi.getDriverController().getRawButtonPressed(5))
            useSlowSpeed = !useSlowSpeed;
        if (Robot.oi.getDriverController().getRawButtonPressed(6))
            useSlowTurning = !useSlowTurning;

        if (Robot.oi.getDriverController().getRawButton(9))
            turnCoeff = -1;

        Robot.driveTrain.drive(speedCoeff * y_value, turnCoeff * x_value);

        //------------------------------------------------------

        // System.out.println("The x is " + x_value + " the y is " + y_value);
        // System.out.println("The angle is " + angle);
        // System.out.println("The straight is " + (-Math.PI/2 - straightDZ));
        
        //CenterDeadzone
        // if (isInCenterDeadzone(x_value, y_value)){
        //     // Doesn't move, x and y value are zero
        //     // System.out.println("Is in CENTERDZ");
        //     Robot.driveTrain.stop();
        // }
        // else if(isInStraightDeadzone(angle)){
        //     //System.out.println("is in straightdz");
        //     Robot.driveTrain.drive(speedCoeff * y_value, 0);
        // }
        // else if(isInRotationDeadzone(angle)){
        //     // System.out.println("is in rotationdz");
        //     // Uses x_value for the turning speed
        //     Robot.driveTrain.drive(0, turnCoeff * x_value);
        // }
        // else{
        //     Robot.driveTrain.drive(speedCoeff * y_value, turnCoeff * x_value);
        // }
    }

    private double curve(double x, double deadzone, double curve) 
    {
        double absVal = Math.abs(x);
        if (absVal < deadzone)
            absVal = 0;
        else
            absVal = Math.pow((absVal - deadzone) / (1 - deadzone), curve);

        return Math.copySign(absVal, x);
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
        if (ret)
        {
            Logger.log("Command: ["+this.getName()+"] done");
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
