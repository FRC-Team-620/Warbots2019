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

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevatorTo extends Command {

    private Elevator elevator;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_setpoint;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public MoveElevatorTo(Elevator elevator, double setpoint) {

        this.elevator = elevator;
        m_setpoint = setpoint;

        requires(this.elevator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=INITIALIZE

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=INITIALIZE
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
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
