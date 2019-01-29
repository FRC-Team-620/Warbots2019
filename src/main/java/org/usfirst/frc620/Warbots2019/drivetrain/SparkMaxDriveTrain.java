// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc620.Warbots2019.drivetrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class SparkMaxDriveTrain extends DriveTrain {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // These are all the hardware bits we added to this subsystem in robotbuilder
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private SpeedController leftFrontMotor;
    private SpeedController leftRearMotor;
    private SpeedControllerGroup leftMotors;
    private SpeedController rightFrontMotor;
    private SpeedController rightRearMotor;
    private SpeedControllerGroup rightMotors;
    private DifferentialDrive differentialDrive;
    private Encoder leftEncoder;
    private Encoder rightEncoder;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // This is an example of state
    private double lastLeftEncoderSpeedRead = 0;
    private double lastRightEncoderSpeedRead = 0;
    private double acceleration = 0;

    public SparkMaxDriveTrain(int leftMotor1CanID, int leftMotor2CanID, 
        int rightMotor1CanID, int rightMotor2CanID)
    {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftFrontMotor = new CANSparkMax(leftMotor1CanID, MotorType.kBrushless);
        leftFrontMotor.setInverted(false);
        
        leftRearMotor = new CANSparkMax(leftMotor2CanID, MotorType.kBrushless);
        leftRearMotor.setInverted(false);
        
        leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor  );
        addChild("Left Motors",leftMotors);
        
        
        rightFrontMotor = new CANSparkMax(rightMotor1CanID, MotorType.kBrushless);
        rightFrontMotor.setInverted(false);
        
        rightRearMotor = new CANSparkMax(rightMotor2CanID, MotorType.kBrushless);
        rightRearMotor.setInverted(false);
        
        rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor  );
        addChild("Right Motors",rightMotors);
        
        
        differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        
        leftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        addChild("Left Encoder",leftEncoder);
        leftEncoder.setDistancePerPulse(1.0);
        leftEncoder.setPIDSourceType(PIDSourceType.kRate);
        
        rightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        addChild("Right Encoder",rightEncoder);
        rightEncoder.setDistancePerPulse(1.0);
        rightEncoder.setPIDSourceType(PIDSourceType.kRate);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new DriveWithJoystick());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        //Read speed data from the encoders
        double leftEncoderSpeed = leftEncoder.getRate();
        double rightEncoderSpeed = rightEncoder.getRate();

        //Calculate acceleration rates
        //By default, the main control loop runs every 20 milliseconds, or 50 times per second
        double leftAcceleration = (leftEncoderSpeed - lastLeftEncoderSpeedRead) * 50;
        double rightAcceleration = (rightEncoderSpeed - lastRightEncoderSpeedRead) * 50;

        acceleration = Math.abs((leftAcceleration + rightAcceleration) / 2);
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double speed, double turnRate)
    {
        //Curvature drive means it takes a speed and a turn rate
        //The boolean at the end allows the robot to turn in place
        //if set to true, but it makes the robot harder to maneuver
        //precisely.
        differentialDrive.arcadeDrive(speed, -1 * turnRate);
        /*
        The other software options that work with the drive train
        hardware that we're using this year are arcadeDrive,
        which is just a worse version of curvatureDrive, and
        tankDrive, where the driver controls each side of the
        robot independently.
        */
    }

    public double getTotalDistanceTravelled()
    {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
    }

    public double getAcceleration()
    {
        return acceleration;
    }
}

