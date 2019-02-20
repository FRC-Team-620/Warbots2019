/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.robot.StateManager.StateKey;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAnglePID extends Command {

  private PIDController pidController;
  private DummyPIDOutput pidOutput;
  private Angle amountToTurn;
  private Angle angle;

  double finalAngle;

  static final double kPTurn = 0.03;
  static final double kITurn = 0.00;
  static final double kDTurn = 0.07;
  static final double kFTurn = 0.00;
  static final double kToleranceDegrees = 5.0f;


  public TurnAnglePID(Angle amountToTurn) 
  {
    requires(Robot.driveTrain);
    System.out.println("The turnpid is working!!!");

    //PIDControllers expect a single sensor, so if the data comes from
    //the drive train, we have to make a pretend sensor that pulls data+
    //from the drive train.
    PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> Robot.driveTrain.getAngle().toDegrees());

    //PIDControllers expect a single motor, so for a full drive train,
    //we have to give it a pretend motor and then plug whatever speed
    //it tells that one motor to spin at into our drive train
    pidOutput = new DummyPIDOutput();

    //WPI class to manage PID control for us
    pidController = new PIDController(kPTurn, kITurn, kDTurn, pidSource, pidOutput);

    //Angle.toDegrees will report values between -180 degrees and 180 degrees
    pidController.setInputRange(0, 360);
    
    //Use this for angles to specify that the input value is circular
    //(ie turning past 180 wraps backs around to -180)
    pidController.setContinuous();

    //The drive train drive method accepts values between -1 and 1
    pidController.setOutputRange(-1, 1);

    //Force the robot to turn to within 3 degrees of the target before ending the command
    pidController.setAbsoluteTolerance(3);
    this.amountToTurn = amountToTurn;

    SmartDashboard.putData(pidController);
  }

  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() 
  {
    //calculate the final direction based on the current direction the robot is facing
    finalAngle = Robot.driveTrain.getAngle().plus(amountToTurn).toDegrees();
    System.out.print("");
    //set that final direction as the target
    //System.out.println("The current angle is " + currentAngle.toDegrees() + "The final angle is " + finalAngle.toDegrees());
    //pidController.setSetpoint(finalAngle.toDegrees());
    pidController.enable();
  }

  public TurnAnglePID()
  {
    this(Angle.fromDegrees(StateManager.getInstance().getDoubleValue(StateKey.COMMANDED_TURNANGLE)));
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    //Read the speed that the PID Controller is giving to our fake
    //motor, and tell our actual drive train to turn at that speed
    
    Robot.driveTrain.drive(0, pidOutput.getOutput());
    System.out.println(pidOutput.getOutput() + " " + Robot.driveTrain.getAngle().toDegrees());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return pidController.onTarget();
    //return false;
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
