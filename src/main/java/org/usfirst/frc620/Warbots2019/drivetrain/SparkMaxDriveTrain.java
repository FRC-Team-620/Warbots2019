/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class SparkMaxDriveTrain extends DriveTrain {

    // These are all the hardware bits we added to this subsystem in robotbuilder
    private CANSparkMax leftFrontMotor;
    private CANSparkMax leftRearMotor;
    private CANSparkMax rightFrontMotor;
    private CANSparkMax rightRearMotor;
    private DifferentialDrive differentialDrive;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private NavX navX;
  
    // This is an example of state
    private double lastLeftEncoderSpeedRead = 0;
    private double lastRightEncoderSpeedRead = 0;
    private double acceleration = 0;

    public SparkMaxDriveTrain(int leftMotor1CanID, int leftMotor2CanID, 
        int rightMotor1CanID, int rightMotor2CanID, NavX.Port navXPort) 
    {
        setName("SparkMaxDriveTrain");

        double ramp = .5;
        var idleMode = IdleMode.kBrake;
        int currentLimit = 20;

        leftFrontMotor = new CANSparkMax(leftMotor1CanID, MotorType.kBrushless);
        leftFrontMotor.setInverted(false);
        leftFrontMotor.setIdleMode(idleMode);
<<<<<<< HEAD
        leftFrontMotor.setRampRate(ramp);
=======
        //leftFrontMotor.setOpenLoopRampRate(ramp);
>>>>>>> 1258d79b62ee481d9ce931f89e498d4e8b847610
        leftFrontMotor.setSmartCurrentLimit(currentLimit);

        leftRearMotor = new CANSparkMax(leftMotor2CanID, MotorType.kBrushless);
        leftRearMotor.setIdleMode(idleMode);
        leftRearMotor.follow(leftFrontMotor, false);
        leftRearMotor.setSmartCurrentLimit(currentLimit); 

        rightFrontMotor = new CANSparkMax(rightMotor1CanID, MotorType.kBrushless);
        rightFrontMotor.setInverted(true);
        rightFrontMotor.setIdleMode(idleMode);
<<<<<<< HEAD
        rightFrontMotor.setRampRate(ramp);
=======
        //rightFrontMotor.setOpenLoopRampRate(ramp);
>>>>>>> 1258d79b62ee481d9ce931f89e498d4e8b847610
        rightFrontMotor.setSmartCurrentLimit(currentLimit);

        rightRearMotor = new CANSparkMax(rightMotor2CanID, MotorType.kBrushless);
        rightRearMotor.setIdleMode(idleMode);
        rightRearMotor.follow(rightFrontMotor, false);
        rightFrontMotor.setSmartCurrentLimit(currentLimit);

        differentialDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
        differentialDrive.setRightSideInverted(false);
        addChild("Differential Drive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        leftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        addChild("Left Encoder", leftEncoder);
        leftEncoder.setDistancePerPulse(1.0);
        leftEncoder.setPIDSourceType(PIDSourceType.kRate);

        rightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        addChild("Right Encoder", rightEncoder);
        rightEncoder.setDistancePerPulse(1.0);
        rightEncoder.setPIDSourceType(PIDSourceType.kRate);

         // Initialize NavX
         // TODO initializing the NavX on the prototype is crashing
        // navX = new NavX(navXPort);
        // addChild(navX);

        // Create Shuffleboard Tab
        // ShuffleboardTab tab = Shuffleboard.getTab("DriveTrain");
        // tab.add("lastLeftEncoderSpeedRead", lastLeftEncoderSpeedRead);
        // tab.add("lastRightEncoderSpeedRead", lastRightEncoderSpeedRead);  
    }

    @Override
    public void initDefaultCommand() {
     
        var defaultCommand = new DriveWithJoystickCommand();
        setDefaultCommand(defaultCommand);
        addChild(defaultCommand);
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        // Read speed data from the encoders
        double leftEncoderSpeed = leftEncoder.getRate();
        double rightEncoderSpeed = rightEncoder.getRate();

        // Calculate acceleration rates
        // By default, the main control loop runs every 20 milliseconds, or 50 times per
        // second
        double leftAcceleration = (leftEncoderSpeed - lastLeftEncoderSpeedRead) * 50;
        double rightAcceleration = (rightEncoderSpeed - lastRightEncoderSpeedRead) * 50;

        acceleration = Math.abs((leftAcceleration + rightAcceleration) / 2);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double speed, double turnRate) {
System.out.println("Driving (" + speed + ", " + (-turnRate) + ")");
        differentialDrive.arcadeDrive(speed, -1 * turnRate);
    }

    @Override
    public void stop() {
        differentialDrive.stopMotor();
    }

    @Override
    public void curvatureDrive(double speed, double curvature) {
        differentialDrive.curvatureDrive(speed, curvature, false);
    }

    public double getTotalDistanceTravelled()
    {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
    }

    public void resetTotalDistanceTravelled()
    {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double getAcceleration() {
        return acceleration;
    }

    @Override
    public Angle getAngle() {
        return navX.getAngle();
    }

    @Override
    public Angle getPitch() {
        return navX.getPitch();
    }
}
