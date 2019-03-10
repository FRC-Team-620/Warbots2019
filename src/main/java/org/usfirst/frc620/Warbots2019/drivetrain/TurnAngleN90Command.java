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
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class TurnAngleN90Command extends Command {

  private PIDController pidController;
  private DummyPIDOutput pidOutput;
  private Angle amountToTurn;
  private DriveTrain driveTrain;

  Angle finalAngle;

  static final double kPTurn = 0.1;
  static final double kITurn = 0.0;
  static final double kDTurn = 0.0;
  static final double kFTurn = 0.00;
  static final double kToleranceDegrees = 1.0f;

  public TurnAngleN90Command(Angle amountToTurn) {
    Logger.log("New Command: "+this.getName());

    // Instantiates Configuration

    // PIDControllers expect a single sensor, so if the data comes from
    // the drive train, we have to make a pretend sensor that pulls data+
    // from the drive train.
    //PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        //() -> Robot.driveTrain.getAngle().toDegrees());

        PIDSource pidSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> Robot.driveTrain.getAngle().toDegrees());

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
    pidController.setOutputRange(-0.5, 0.5);

    // Force the robot to turn to within 3 degrees of the target before ending the
    // command
    pidController.setAbsoluteTolerance(1);
    //this.amountToTurn = amountToTurn;

    // SmartDashboard.putData("TurnAnglePID", pidController);
  }

  public TurnAngleN90Command() {
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Read the speed that the PID Controller is giving to our fake
    // motor, and tell our actual drive train to turn at that speed
    TurnAnglePIDCommand cmd = new TurnAnglePIDCommand(Angle.fromDegrees(90));
    Scheduler.getInstance().add(cmd);
    //System.out.println("Current Angle "+ Robot.driveTrain.getAngle().toDegrees() + " Turn SetPoint" + pidController.getSetpoint() + " Turn Output " + pidOutput.getOutput());
    // System.out.println(pidOutput.getOutput() + " " + Robot.driveTrain.getAngle().toDegrees());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }
//}8
//8
}
