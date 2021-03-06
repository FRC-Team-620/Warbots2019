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
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc620.Warbots2019.utility.Logger;

public class DriveStraightPIDCommand extends Command {
  private double m_distance;
  private Angle m_angle;
  private PIDController turnController;
  double turnToAngleRate;
  private DummyPIDOutput pidTurnOutput;
  private PIDController driveController;
  double driveToDistanceRate;
  private DummyPIDOutput pidDriveOutput;

  

  static final double kPTurn = 0.03;
  static final double kITurn = 0.00;
  static final double kDTurn = 0.00;
  static final double kFTurn = 0.00;

  static final double kPDrive = 0.3;
  static final double kIDrive = 0.00;
  static final double kDDrive = 0.00;
  static final double kFDrive = 0.00;

  static final double kToleranceDegrees = 10f;
  static final double kToleranceDistance = 1f;

  public DriveStraightPIDCommand() {
    //Instantiates Configuration
    Logger.log("New Command: "+this.getName());
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);

    PIDSource pidTurnSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> Robot.driveTrain.getAngle().toDegrees());

    PIDSource pidDriveSource = new LambdaPIDSource(PIDSourceType.kDisplacement,
        () -> Robot.driveTrain.getTotalDistanceTravelled());

    pidTurnOutput = new DummyPIDOutput();
    pidDriveOutput = new DummyPIDOutput();

    turnController = new PIDController(kPTurn, kITurn, kDTurn, pidTurnSource, pidTurnOutput);
    driveController = new PIDController(kPDrive, kIDrive, kDDrive, pidDriveSource, pidDriveOutput);

    turnController.setInputRange(-180, 180);
    driveController.setInputRange(-1, 1);

    turnController.setContinuous();
    // drive Controller is not continuos

    turnController.setOutputRange(-0.3, 0.3);
    driveController.setOutputRange(-1, 1);

    turnController.setAbsoluteTolerance(kToleranceDegrees);
    driveController.setAbsoluteTolerance(kToleranceDistance);



  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    m_distance = StateManager.getInstance().getDoubleValue(StateKey.COMMANDED_DRIVEDISTANCE);
    m_angle = Robot.driveTrain.getAngle();
    turnController.setSetpoint(m_angle.toDegrees());
    driveController.setSetpoint(m_distance);
    turnController.enable();
    driveController.enable();
    Robot.driveTrain.resetTotalDistanceTravelled();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.drive(pidDriveOutput.getOutput(), pidTurnOutput.getOutput());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean ret = turnController.onTarget() && driveController.onTarget();
    if (ret)
    {
        Logger.log("Command: ["+this.getName()+"] done");
    }
    return ret;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    turnController.disable();
    driveController.disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

  public static ConfigurableImpl asConfigurable(){
    final ConfigurableImpl configurable;
    configurable = new ConfigurableImpl();
    return configurable;
  }
}
