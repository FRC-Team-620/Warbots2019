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

public class DriveStraight extends Command {
  private double m_distance;
  private Angle m_angle;
  PIDController turnController;
  double turnToAngleRate;
  DummyPIDOutput pidTurnOutput;
  PIDController driveController;
  double driveToDistanceRate;
  DummyPIDOutput pidDriveOutput;

  static final double kPTurn = 0.03;
  static final double kITurn = 0.00;
  static final double kDTurn = 0.00;
  static final double kFTurn = 0.00;

  static final double kPDrive = 0.03;
  static final double kIDrive = 0.00;
  static final double kDDrive = 0.00;
  static final double kFDrive = 0.00;

  static final double kToleranceDegrees = 2.0f;
  static final double kToleranceDistance = 0.2f;
  public DriveStraight() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    PIDSource pidTurnSource = new LambdaPIDSource(PIDSourceType.kDisplacement, 
      () -> Robot.driveTrain.getAngle().toDegrees());

    PIDSource pidDriveSource = new LambdaPIDSource(PIDSourceType.kDisplacement, 
      () -> Robot.driveTrain.getTotalDistanceTravelled());

    pidTurnOutput = new DummyPIDOutput();
    pidDriveOutput = new DummyPIDOutput();
    
    turnController = new PIDController(kPTurn, kITurn, kDTurn, pidTurnSource, pidTurnOutput);
    driveController = new PIDController(kPDrive, kIDrive, kDDrive, pidDriveSource, pidDriveOutput);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_distance = StateManager.getInstance().getDoubleValue(StateKey.COMMANDED_DRIVEDISTANCE);
    m_angle = Robot.driveTrain.getAngle();
    Robot.driveTrain.resetTotalDistanceTravelled();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double deltaDistance = m_distance - Robot.driveTrain.getTotalDistanceTravelled();
    Angle deltaAngle = m_angle.minus(Robot.driveTrain.getAngle()).absoluteValue();
    Robot.driveTrain.drive(deltaDistance*0.5, deltaAngle.toTurns()*0.5);
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
