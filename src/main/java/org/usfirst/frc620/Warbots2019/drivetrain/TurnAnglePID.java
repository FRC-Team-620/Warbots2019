/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class TurnAnglePID extends Command {

  private DriveTrain driveTrain;

  private PIDController pidController;
  private DummyPIDOutput pidOutput;

  private Angle amountToTurn;

  public TurnAnglePID(DriveTrain driveTrain, Angle amountToTurn) 
  {
    this.driveTrain = driveTrain;

    requires(this.driveTrain);

    //PIDControllers expect a single sensor, so if the data comes from
    //the drive train, we have to make a pretend sensor that pulls data
    //from the drive train.
    PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> this.driveTrain.getAngle().toDegrees());

    //PIDControllers expect a single motor, so for a full drive train,
    //we have to give it a pretend motor and then plug whatever speed
    //it tells that one motor to spin at into our drive train
    pidOutput = new DummyPIDOutput();

    //WPI class to manage PID control for us
    pidController = new PIDController(0.1, 0, 0, pidSource, pidOutput);

    //Angle.toDegrees will report values between -180 degrees and 180 degrees
    pidController.setInputRange(-180, 180);
    
    //Use this for angles to specify that the input value is circular
    //(ie turning past 180 wraps backs around to -180)
    pidController.setContinuous();

    //The drive train drive method accepts values between -1 and 1
    pidController.setOutputRange(-1, 1);

    //Force the robot to turn to within 5 degrees of the target before ending the command
    pidController.setAbsoluteTolerance(5);

    this.amountToTurn = amountToTurn;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
    //calculate the final direction based on the current direction the robot is facing
    double finalAngle = this.driveTrain.getAngle().plus(amountToTurn).toDegrees();

    //set that final direction as the target
    pidController.setSetpoint(finalAngle);

    pidController.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    //Read the speed that the PID Controller is giving to our fake
    //motor, and tell our actual drive train to turn at that speed
    this.driveTrain.drive(0, pidOutput.getOutput());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return pidController.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    pidController.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
