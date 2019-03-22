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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScaleHabClosedLoopCommand extends Command 
{
  private final static String ELEVATOR_BASE_SPEED = "hab/elevatorBaseSpeed";
  private final static String SCISSOR_LIFT_BASE_SPEED = "hab/scissorLiftBaseSpeed";
  private final static String ELEVATOR_P = "hab/elevatorKP";
  private final static String SCISSOR_LIFT_P = "hab/scissorLiftKP";
  private final static String PID_P = "hab/pidKP";
  private final static String PID_I = "hab/pidKI";
  private final static String PID_D = "hab/pidKD";

  private double elevatorBaseSpeed;
  private double scissorLiftBaseSpeed;
  private double elevatorKP;
  private double scissorLiftKP;

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
      () -> driveTrain.getPitch().toDegrees()
    );

    // PIDControllers expect a single motor, so for a full drive train,
    // we have to give it a pretend motor and then plug whatever speed
    // it tells that one motor to spin at into our drive train
    var pidOutput = new DummyPIDOutput();

    // WPI class to manage PID control for us
    controller = new PIDController(0, 0, 0, pidSource, pidOutput);

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

    SmartDashboard.putNumber(ELEVATOR_BASE_SPEED, 0);
    SmartDashboard.putNumber(SCISSOR_LIFT_BASE_SPEED, 0.5);
    SmartDashboard.putNumber(ELEVATOR_P, -1);
    SmartDashboard.putNumber(SCISSOR_LIFT_P, 0);
    SmartDashboard.putNumber(PID_P, 0.05);
    SmartDashboard.putNumber(PID_I, 0.001);
    SmartDashboard.putNumber(PID_D, 0.1);
  }

  @Override
  protected void initialize() {
    controller.setP(SmartDashboard.getNumber(PID_P, 0.05));
    controller.setI(SmartDashboard.getNumber(PID_I, 0.001));
    controller.setD(SmartDashboard.getNumber(PID_D, 0.1));

    elevatorBaseSpeed = SmartDashboard.getNumber(ELEVATOR_BASE_SPEED, -0.5);
    scissorLiftBaseSpeed = SmartDashboard.getNumber(SCISSOR_LIFT_BASE_SPEED, 0.5);
    elevatorKP = SmartDashboard.getNumber(ELEVATOR_P, -1);
    scissorLiftKP = SmartDashboard.getNumber(SCISSOR_LIFT_P, 1);

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
    System.out.println("driving scissor lift " + (scissorLiftBaseSpeed + scissorLiftKP * output));
    scissorLift.drive(scissorLiftBaseSpeed + scissorLiftKP * output);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() 
  {
    return Robot.oi.driverController.getRawButton(2)
      || (elevator.getHeight() < 20 && driveTrain.getPitch().toDegrees() >= 0);
  }

  @Override
  protected void end() {
    controller.disable();
    System.out.println("Ending command scale hab closed loop command");
  }
}