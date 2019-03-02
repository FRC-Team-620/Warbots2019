/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc620.Warbots2019.utility.Logger;

/**
 * Implementation of DriveTrain
 */
public class SparkDriveTrain extends DriveTrain {

    //These are all the hardware bits we added to this subsystem in robotbuilder
    private Spark leftFrontMotor;
    private Spark leftRearMotor;
    private SpeedControllerGroup leftMotors;
    private Spark rightFrontMotor;
    private Spark rightRearMotor;
    private SpeedControllerGroup rightMotors;
    private DifferentialDrive differentialDrive;

    private static double kEncoderCount = 360;
    private static double kGearRatio = 0.9;
    private static double kWheelDiamter = 0.333;
    private static double kEncoderCountsPerFoot; 
    private static double totalDistance;

    private final Encoder leftEncoder;
    private final Encoder rightEncoder;
    private NavX navX;

    // This is an example of state
    private double lastLeftEncoderSpeedRead = 0;
    private double lastRightEncoderSpeedRead = 0;
    private double acceleration = 0;

    public SparkDriveTrain(int leftMotor1Port, int leftMotor2Port, 
        int rightMotor1Port, int rightMotor2Port, NavX.Port navXPort) 
    {
        setName("SparkDriveTrain");

        kEncoderCountsPerFoot = (kWheelDiamter * Math.PI * kGearRatio)/kEncoderCount;

        leftFrontMotor = new Spark(leftMotor1Port);
        addChild("Left Front Motor",leftFrontMotor);
        leftFrontMotor.setInverted(false);
        
        leftRearMotor = new Spark(leftMotor2Port);
        addChild("Left Rear Motor",leftRearMotor);
        leftRearMotor.setInverted(false);
        
        leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
        addChild("Left Motors",leftMotors);
         
        rightFrontMotor = new Spark(rightMotor1Port);
        addChild("Right Front Motor",rightFrontMotor);
        rightFrontMotor.setInverted(false);
        
        rightRearMotor = new Spark(rightMotor2Port);
        addChild("Right Rear Motor",rightRearMotor);
        rightRearMotor.setInverted(false);
        
        rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
        addChild("Right Motors",rightMotors);
                
        differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        leftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        System.out.println("Constructed left encoder: " + leftEncoder);
        addChild("Left Encoder",leftEncoder);
        leftEncoder.setDistancePerPulse(kEncoderCountsPerFoot);
        leftEncoder.setPIDSourceType(PIDSourceType.kRate);
        
        rightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        addChild("Right Encoder",rightEncoder);
        rightEncoder.setDistancePerPulse(kEncoderCountsPerFoot);
        rightEncoder.setPIDSourceType(PIDSourceType.kRate);

        // Initialize NavX
        navX = new NavX(navXPort);

        // Create Shuffleboard Tab
        // ShuffleboardTab tab = Shuffleboard.getTab("DriveTrain");
        // tab.add("kEncoderCountsPerFoot", kEncoderCountsPerFoot);
        // tab.add("totalDistance", totalDistance);
    }

    public SparkDriveTrain()
    {
        //TODO: Load these values from config
        this(1, 2, 3, 4, NavX.Port.SPIMXP);
    }

    @Override
    public void initDefaultCommand() 
    {
        setDefaultCommand(new DriveWithJoystickCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop, or every 1/20th of a second

        //Read speed data from the encoders
        double leftEncoderSpeed = leftEncoder.getRate();
        double rightEncoderSpeed = rightEncoder.getRate();
        //System.out.println(String.format("Left = %f, Right = %f", leftEncoderSpeed, rightEncoderSpeed));
        //Calculate acceleration rates
        //By default, the main control loop runs every 20 milliseconds, or 50 times per second
        double leftAcceleration = (leftEncoderSpeed - lastLeftEncoderSpeedRead) * 50;
        double rightAcceleration = (rightEncoderSpeed - lastRightEncoderSpeedRead) * 50;

        acceleration = Math.abs((leftAcceleration + rightAcceleration) / 2);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double speed, double turnRate)
    {
        //Curvature drive means it takes a speed and a turn rate
        //The boolean at the end allows the robot to turn in place
        //if set to true, but it makes the robot harder to maneuver
        //precisely.

        differentialDrive.arcadeDrive(speed, -turnRate);
        /*
        The other software options that work with the drive train
        hardware that we're using this year are arcadeDrive,
        which is just a worse version of curvatureDrive, and
        tankDrive, where the driver controls each side of the
        robot independently.
        */
    }

    @Override
    public void stop() {
        differentialDrive.stopMotor();
    }

    @Override
    public void curvatureDrive(double speed, double curvature) {
System.out.println("This is curvature drive, sparkDriveTrain");
        differentialDrive.curvatureDrive(speed, curvature, false);
    }

    public double getTotalDistanceTravelled()
    {
        totalDistance = (-leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
        // SmartDashboard.putNumber("totalDistance", totalDistance);
        return totalDistance;
    }

    public void resetTotalDistanceTravelled()
    {
        leftEncoder.reset();
        rightEncoder.reset();
        totalDistance = 0;
    }

    public double getAcceleration()
    {
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

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        super.initSendable(builder);
        navX.initSendable(builder);
    }
}

