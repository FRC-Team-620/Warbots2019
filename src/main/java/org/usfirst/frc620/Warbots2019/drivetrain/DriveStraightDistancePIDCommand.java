/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;
import org.usfirst.frc620.Warbots2019.utility.Logger;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightDistancePIDCommand extends Command {
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

  static final double kPDrive = 0.1;
  static final double kIDrive = 0.00;
  static final double kDDrive = 0.00;
  static final double kFDrive = 0.00;

  static final double kToleranceDegrees = 5.0f;
  static final double kToleranceDistance = 5f;

  public DriveStraightDistancePIDCommand() {
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

    turnController.setInputRange(0, 360);

    turnController.setContinuous();
    // drive Controller is not continuos

    turnController.setOutputRange(-0.5, 0.5);
    driveController.setOutputRange(0, 0.8);

    turnController.setAbsoluteTolerance(kToleranceDegrees);
    driveController.setAbsoluteTolerance(kToleranceDistance);

    // SmartDashboard.putData("Drive PID", driveController);
    // SmartDashboard.putData("Turn PID", turnController);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveTrain.resetTotalDistanceTravelled();
    m_distance = 10;
    m_angle = Robot.driveTrain.getAngle();
    turnController.setSetpoint(m_angle.toDegrees());
    driveController.setSetpoint(m_distance);
    turnController.enable();
    driveController.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.err.println("Distance Run: " + Robot.driveTrain.getTotalDistanceTravelled() + " Distance Output: "
        + pidDriveOutput.getOutput() + " Distance Target: " + driveController.getSetpoint());
    Robot.driveTrain.drive(-pidDriveOutput.getOutput(), pidTurnOutput.getOutput());
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
    Robot.driveTrain.drive(0, 0);
    turnController.disable();
    driveController.disable();
    turnController.reset();
    driveController.reset();
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
