// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoystick extends Command {
    double centerDZ;
    double rotationDZ;
    double straightDZ;

    public DriveWithJoystick() 
    {
        ControlReader config  = Robot.config;
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
    protected void execute() 
    {
        // These speed/rotation -1.0 to 1.0
        double y_value = Robot.oi.getRobotSpeed();
        double x_value = Robot.oi.getRobotRotationRate();
        double angle = Math.atan2(y_value, x_value);
        // System.out.println("The x is " + x_value + " the y is " + y_value);
        // System.out.println("The angle is " + angle);
        // System.out.println("The straight is " + (-Math.PI/2 - straightDZ));
        
        //CenterDeadzone
        if (isInCenterDeadzone(x_value, y_value)){
            //Doesn't moves, x and y value are zero
            //System.out.println("Is in CENTERDZ");
            Robot.driveTrain.drive(0, 0);
        }
        else if(isInStraightDeadzone(angle)){
            //System.out.println("is in straightdz");
            Robot.driveTrain.drive(y_value, 0);
        }
        else if(isInRotationDeadzone(angle)){
            // System.out.println("is in rotationdz");
            //Uses x_value for the turning speed
            Robot.driveTrain.drive(0, x_value);
        }
        else{
            Robot.driveTrain.drive(y_value, x_value);
        }
    }
    

    private boolean isInCenterDeadzone(double x, double y)
    {
        return (x <= centerDZ && x >= -centerDZ && y <= centerDZ && y >= -centerDZ);
    }

    private boolean isInStraightDeadzone(double a){
        boolean isInDZ = false;
        if((a > Math.PI/2 - straightDZ && a < Math.PI/2 + straightDZ) || (a > -Math.PI/2 - straightDZ && a < -Math.PI/2 + straightDZ)){
            isInDZ = true;
        }
        return isInDZ;
    }

    private boolean isInRotationDeadzone(double a){
        boolean isInDZ = false;
        if((a > -Math.PI && a < (-Math.PI + rotationDZ)) || (a > -rotationDZ && a < rotationDZ) || (a > (Math.PI - rotationDZ) && a < Math.PI)){
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
