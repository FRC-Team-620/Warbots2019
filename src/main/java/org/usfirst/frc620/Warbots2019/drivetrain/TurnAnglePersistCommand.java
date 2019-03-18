/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.GetAngleCommand;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.robot.StateManager.StateKey;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class TurnAnglePersistCommand extends Command {

  private PIDController pidController;
  private DummyPIDOutput pidOutput;
  private Angle amountToTurn;
  private DriveTrain driveTrain;

  Angle finalAngle;
  boolean rotationCW = true;
  static double kPTurn = 0.01;
  static double kITurn = 0.0;
  static double kDTurn = 0.0;
  static double kFTurn = 0.00;
  static double kToleranceDegrees = 5.0f;
  static double prevPTurn = 0.01;
  static double prevITurn = 0.0;
  static double prevDTurn = 0.0;
  static double prevFTurn = 0.00;
  static double prevToleranceDegrees = 5.0f;
  static double prevAngle;

  public TurnAnglePersistCommand(Angle amountToTurn) {
    Logger.log("New Command: "+this.getName());
    if (amountToTurn.toDegrees() < 0.0)
    {
        rotationCW = false;
    }
    requires(Robot.driveTrain);
    finalAngle = Angle.fromDegrees(amountToTurn.toDegrees()+getNormalizedAngle());
    // Instantiates Configuration

    // PIDControllers expect a single sensor, so if the data comes from
    // the drive train, we have to make a pretend sensor that pulls data+
    // from the drive train.
    //PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        //() -> Robot.driveTrain.getAngle().toDegrees());

        PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> getNormalizedAngle());

    // PIDControllers expect a single motor, so for a full drive train,
    // we have to give it a pretend motor and then plug whatever speed
    // it tells that one motor to spin at into our drive train
    pidOutput = new DummyPIDOutput();

    // WPI class to manage PID control for us
    pidController = new PIDController(kPTurn, kITurn, kDTurn, pidSource, pidOutput);

    // Angle.toDegrees will report values between -180 degrees and 180 degrees
    pidController.setInputRange(-180, 180);

    // Use this for angles to specify that the input value is circular
    // (ie turning past 180 wraps backs around to -180)
    pidController.setContinuous();

    // The drive train drive method accepts values between -1 and 1
    pidController.setOutputRange(-0.7, 0.7);

    // Force the robot to turn to within 3 degrees of the target before ending the
    // command
    pidController.setAbsoluteTolerance(3);
    //this.amountToTurn = amountToTurn;

    // SmartDashboard.putData("TurnAnglePID", pidController);
  }

  public TurnAnglePersistCommand() {
    this(Angle.fromDegrees(StateManager.getInstance().getDoubleValue(StateKey.COMMANDED_TURNANGLE)));
  }

  public double getNormalizedAngle()
  {
    return Robot.driveTrain.getAngle().toDegrees();//(((int)Robot.driveTrain.getAngle().toDegrees())%360)-180.0;
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // calculate the final direction based on the current direction the robot is
    // facingM
    // set that final direction as the target
    // System.out.println("The current angle is " + currentAngle.toDegrees() + "The
    // final angle is " + finalAngle.toDegrees());
    
  prevPTurn = kPTurn;
  prevITurn = kITurn;
  prevDTurn = kDTurn;
  prevFTurn = kFTurn;
  prevToleranceDegrees = kToleranceDegrees;
    SmartDashboard.putData(this);
    pidController.setSetpoint(finalAngle.toDegrees());
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Read the speed that the PID Controller is giving to our fake
    // motor, and tell our actual drive train to turn at that speed
    
    pidController.enable();

    double cmdValue = pidOutput.getOutput();
    if (rotationCW)
    {
      cmdValue *= -1.0;
    }
    Robot.driveTrain.drive(0, cmdValue);
    System.out.println("gyro angle: ["+Robot.driveTrain.getAngle().toDegrees()+"] norm: ["+getNormalizedAngle()+"] final ["+finalAngle.toDegrees()+"] Delta = " + (finalAngle.toDegrees() - getNormalizedAngle())+" cmdValue: "+cmdValue);
    //Robot.driveTrain.drive(0, pidOutput.getOutput());
    //System.out.println(pidOutput.getOutput());
    //System.out.println("Current Angle "+ Robot.driveTrain.getAngle().toDegrees() + " Turn SetPoint" + pidController.getSetpoint() + " Turn Output " + pidOutput.getOutput());
    // System.out.println(pidOutput.getOutput() + " " + Robot.driveTrain.getAngle().toDegrees());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    
    boolean ret = false; //driveTrain.getAngle() == finalAngle;
    
    if (ret)
    {
        Logger.log("Command: ["+this.getName()+"] done");
        pidController.disable();
    }
    double turn = StateManager.getInstance().getDoubleValue(StateKey.COMMANDED_TURNANGLE);
    if( pidController.onTarget() && ((turn != this.finalAngle.toDegrees()) || 
        prevPTurn != kPTurn || prevITurn != kITurn || prevITurn != kITurn || 
        prevDTurn != kDTurn || prevFTurn != kFTurn || prevToleranceDegrees != kToleranceDegrees))
    {
        finalAngle = Angle.fromDegrees(turn + getNormalizedAngle());
        prevPTurn = kPTurn;
        prevITurn = kITurn;
        prevDTurn = kDTurn;
        prevFTurn = kFTurn;
        prevToleranceDegrees = kToleranceDegrees;
        PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> getNormalizedAngle());

        // WPI class to manage PID control for us
        pidController = new PIDController(kPTurn, kITurn, kDTurn, pidSource, pidOutput);

        // Angle.toDegrees will report values between -180 degrees and 180 degrees
        pidController.setInputRange(-180, 180);

        // Use this for angles to specify that the input value is circular
        // (ie turning past 180 wraps backs around to -180)
        pidController.setContinuous();

        // The drive train drive method accepts values between -1 and 1
        pidController.setOutputRange(-0.7, 0.7);

        // Force the robot to turn to within 3 degrees of the target before ending the
        // command
        pidController.setAbsoluteTolerance(3);   
        pidController.setSetpoint(finalAngle.toDegrees());
        
    }
    return ret;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() 
  {
    System.out.println("Working");
    pidController.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  public static ConfigurableImpl asConfigurable(){
    ConfigurableImpl configurable;
    configurable = new ConfigurableImpl();
    return configurable;
  }
}
