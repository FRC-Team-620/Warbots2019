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

public class DriveWithJoystickCommand extends Command {

    double centerDZ;
    double rotationDZ;
    double straightDZ;

    public DriveWithJoystickCommand() {
        ControlReader config = Robot.config;
        centerDZ = config.getMappedDouble("driver.center_deadzone");
        rotationDZ = config.getMappedDouble("driver.rotation_deadzone");
        straightDZ = config.getMappedDouble("driver.speed_deadzone");
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        // These speed/rotation -1.0 to 1.0
        double y_value = Robot.oi.getRobotSpeed();
        double x_value = -Robot.oi.getRobotRotationRate();
        // System.out.println("  y: "+y_value+"           x: "+x_value);
        double angle = Math.atan2(y_value, x_value);

        double speedCoeff = 1.0;
        double turnCoeff = -1.0;

        //------------------------------------------------------
        // This should be mapped to an OI value thing, like
        // speed and rotation rate are, so that there can be a button mapping
        // like there is for the y_value and x_value above.
        if (Robot.oi.getDriverController().getRawButton(5))
            speedCoeff *= .4;

        if (Robot.oi.getDriverController().getRawButton(6))
            turnCoeff *= .4;
        //------------------------------------------------------

        // System.out.println("The x is " + x_value + " the y is " + y_value);
        // System.out.println("The angle is " + angle);
        // System.out.println("The straight is " + (-Math.PI/2 - straightDZ));
        
        //CenterDeadzone
        if (isInCenterDeadzone(x_value, y_value)){
            // System.out.println("center dz");
            // Doesn't move, x and y value are zero
            // System.out.println("Is in CENTERDZ");
            Robot.driveTrain.drive(0, 0);
        }
        else if(isInStraightDeadzone(angle)){
            // System.out.println("str dz");
            //System.out.println("is in straightdz");
            Robot.driveTrain.drive(speedCoeff * y_value, 0);
        }
        else if(isInRotationDeadzone(angle)){
            // System.out.println("rot dz");
            // System.out.println("is in rotationdz");
            // Uses x_value for the turning speed
            Robot.driveTrain.drive(0, turnCoeff * x_value);
        }
        else{
            // System.out.println("   no dz "+speedCoeff);
            Robot.driveTrain.drive(speedCoeff * y_value, turnCoeff * x_value);
        }
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
        return false;
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
}
