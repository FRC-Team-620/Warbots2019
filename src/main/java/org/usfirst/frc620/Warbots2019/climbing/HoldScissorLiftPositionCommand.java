/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.climbing;

import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class HoldScissorLiftPositionCommand extends Command {

  private PIDController controller;

  private ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;
  private DriveTrain driveTrain = Robot.driveTrain;

  public HoldScissorLiftPositionCommand(Angle pitch) {
    requires(scissorLift);

    var pidSource = new LambdaPIDSource(
      PIDSourceType.kDisplacement, 
      () -> driveTrain.getPitch().toDegrees()
    );

    // PIDControllers expect a single motor, so for a full drive train,
    // we have to give it a pretend motor and then plug whatever speed
    // it tells that one motor to spin at into our drive train
    var pidOutput = new DummyPIDOutput();

    // WPI class to manage PID control for us
    controller = new PIDController(0.05, 0.01, 0, pidSource, pidOutput);

    // Angle.toDegrees will report values between -180 degrees and 180 degrees
    controller.setInputRange(-180, 180);

    // Use this for angles to specify that the input value is circular
    // (ie turning past 180 wraps backs around to -180)
    controller.setContinuous();

    // The drive train drive method accepts values between -1 and 1
    controller.setOutputRange(-1, 1);

    // Force the robot to turn to within 3 degrees of the target before ending the
    // command
    controller.setAbsoluteTolerance(1);

    controller.setSetpoint(pitch.toDegrees());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Starting hold scissor lift command");
    controller.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    scissorLift.drive(controller.get());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.oi.getDriverController().getRawButton(2);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    controller.disable();
    System.out.println("Ending hold scissor lift position command");
  }
}
