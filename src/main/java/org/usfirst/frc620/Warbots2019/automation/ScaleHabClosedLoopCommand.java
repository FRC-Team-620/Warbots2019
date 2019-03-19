/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import org.usfirst.frc620.Warbots2019.climbing.ScissorLift;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.elevator.Elevator;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.DummyPIDOutput;
import org.usfirst.frc620.Warbots2019.utility.LambdaPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class ScaleHabClosedLoopCommand extends Command 
{
  //TODO: load these from somewhere else
  private static final double elevatorBaseSpeed = 0.8;
  private static final double scissorLiftBaseSpeed = 0.3;
  private static final double elevatorKP = 0.3;
  private static final double scissorLiftKP = 0.4;
  private static final double pidKP = 1;
  private static final double pidKI = 0.01;
  private static final double pidKD = 0;

  private Elevator elevator = Robot.elevator;
  private ScissorLift scissorLift = (ScissorLift) Robot.climbingMechanism;
  private DriveTrain driveTrain = Robot.driveTrain;
  private PIDController controller;

  public ScaleHabClosedLoopCommand() 
  {
    requires(elevator);
    requires(scissorLift);

    var pidSource = new LambdaPIDSource(
      PIDSourceType.kDisplacement, 
      () -> driveTrain.getAngle().toDegrees()
    );

    // PIDControllers expect a single motor, so for a full drive train,
    // we have to give it a pretend motor and then plug whatever speed
    // it tells that one motor to spin at into our drive train
    var pidOutput = new DummyPIDOutput();

    // WPI class to manage PID control for us
    controller = new PIDController(pidKP, pidKI, pidKD, pidSource, pidOutput);

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

    //Uses drive train for navX, but does not prevent other commands form using the
    //drive train. Should NOT call requires(driveTrain).
  }

  @Override
  protected void initialize() {
    controller.enable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    double output = controller.get();
    System.out.println("Pitch: " + driveTrain.getPitch() + " Output: " + output);
    System.out.println("driving elevator " + (elevatorBaseSpeed + elevatorKP * output));
    elevator.drive(elevatorBaseSpeed + elevatorKP * output);
    System.out.println("driving scissor lift " + (scissorLiftBaseSpeed - scissorLiftKP * output));
    scissorLift.drive(scissorLiftBaseSpeed - scissorLiftKP * output);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return elevator.getHeight() == 0 || Robot.oi.driverController.getRawButton(2);
  }

  @Override
  protected void end() {
    controller.disable();
  }
}
