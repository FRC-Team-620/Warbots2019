// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc620.Warbots2019.robot;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc620.Warbots2019.automation.AlignmentSystem;
import org.usfirst.frc620.Warbots2019.automation.TrackingSystem;
//import org.usfirst.frc620.Warbots2019.climbing.PistonLift;
//import org.usfirst.frc620.Warbots2019.climbing.ScissorLift;
import org.usfirst.frc620.Warbots2019.climbing.ClimbingMechanism;
import org.usfirst.frc620.Warbots2019.climbing.PistonLift;
import org.usfirst.frc620.Warbots2019.climbing.ScissorLift;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveStraightDistancePIDCommand;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveStraightPIDCommand;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.drivetrain.NavX;
import org.usfirst.frc620.Warbots2019.drivetrain.SparkDriveTrain;
import org.usfirst.frc620.Warbots2019.drivetrain.SparkMaxDriveTrain;
import org.usfirst.frc620.Warbots2019.drivetrain.TurnAngleCommand;
import org.usfirst.frc620.Warbots2019.drivetrain.TurnAnglePIDCommand;
//import org.usfirst.frc620.Warbots2019.drivetrain.TurnAngle;
import org.usfirst.frc620.Warbots2019.elevator.Elevator;
import org.usfirst.frc620.Warbots2019.elevator.TalonElevator;
import org.usfirst.frc620.Warbots2019.elevator.TwoTalonElevator;
import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech;
import org.usfirst.frc620.Warbots2019.mechanisms.pinchPointGearGrabber.PinchPointGearGrabber;
import org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber;
import org.usfirst.frc620.Warbots2019.sim.SimDriveTrain;
import org.usfirst.frc620.Warbots2019.utility.Angle;
import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.WeightedLinearRegressionCalculator;
import org.usfirst.frc620.Warbots2019.vision.FollowLineWithCameraCommand;
import org.usfirst.frc620.Warbots2019.vision.Line;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    private static Compressor compressor;
    public static DriveTrain driveTrain;
    public static Elevator elevator;
    public static AlignmentSystem alignmentSystem;
    public static TrackingSystem trackingSystem;
    public static ScoringMechanism scoringMechanism;
    public static ClimbingMechanism climbingMechanism;
    public static ControlReader config;
    public static OI oi;
    ConfigurableImpl configurable;
    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() 
    {
        System.out.println("Robot initiated");
        //We now have a Configurable object with all methods implemented, so programs can carry it around like a suitcase
        configurable = new ConfigurableImpl();
        // compressor = new Compressor(6);
        // compressor.setClosedLoopControl(true);
        // compressor.start();
        // driveTrain = new SparkMaxDriveTrain(1, 2, 3, 4);
        configurable.addElement(new Element("name", "Name Of Robot", null));
        configurable.addElement(new Element("driver.enabled", "Whether to insantiate driverJoystick", new ArrayList<String>(Arrays.asList("true", "false"))));
        configurable.addElement(new Element("scorer.enabled", "Whether to insantiate scorerJoystick", new ArrayList<String>(Arrays.asList("true", "false"))));

        config = new ControlReader();

        ArrayList<Configurable> configurables = new ArrayList<Configurable>();
        configurables.add(configurable);

        config.dumpConfigurationFile("/home/lvuser/demo.properties", configurables);
        StateManager stateMan = StateManager.getInstance();
        stateMan.setDoubleValue(StateManager.StateKey.COMMANDED_DRIVEDISTANCE, 0.5);
        stateMan.setDoubleValue(StateManager.StateKey.COMMANDED_TURNANGLE, 5.0);

        System.out.println("Connecting to robot " + config.getRobotType());
     
        String driverTrainClass = config.getMappedString("DriveTrain");
        if (driverTrainClass != null) {
            if (driverTrainClass.equalsIgnoreCase(
                "org.usfirst.frc620.Warbots2019.drivetrain.SparkDriveTrain")) {
                driveTrain = new SparkDriveTrain(1, 2, 3, 4, NavX.Port.SPIMXP);
                System.out.println("Configured with SparkDriveTrain");
            } else if (driverTrainClass.equalsIgnoreCase(
                "org.usfirst.frc620.Warbots2019.drivetrain.SparkMaxDriveTrain")) {
                driveTrain = new SparkMaxDriveTrain(1, 2, 3, 4, NavX.Port.SerialUSB);
                System.out.println("Configured with SparkMaxDriveTrain");
            } else if (driverTrainClass.equalsIgnoreCase(
                "org.usfirst.frc620.Warbots2019.sim.SimDriveTrain")) {
                driveTrain = new SimDriveTrain();
                System.out.println("Configured with SimDriveTrain");
            } else {
                System.err.println("Configured with no drive train ");
            }
        }

        String compressorOption = config.getMappedString("Compressor");
        if (compressorOption != null && compressorOption.equalsIgnoreCase("true"))
        {
            compressor = new Compressor(6);
            
            compressor.setClosedLoopControl(true);
            compressor.start();
        }

        String ScoringMechanism = config.getMappedString("ScoringMechanism");
        if (ScoringMechanism != null) {
            if (ScoringMechanism.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber"))
                scoringMechanism = new TazGrabber(5, 6, 5, 7, 4, 2, 0, 3, 1);
            else if (ScoringMechanism.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.cargo.TalonCargoMech"))
                scoringMechanism = new CargoMech(9, 6, 0);
            else if (ScoringMechanism.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.pinchPointGearGrabber.PinchPointGearGrabber"))
                scoringMechanism = new PinchPointGearGrabber(5, 2, 3);
        } else {
            System.err.println("no scoring mech specified");
        }

        String elevatorClass = config.getMappedString("Elevator");
        if (elevatorClass != null)
        {
            if (elevatorClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.elevator.TwoTalonElevator"))
                elevator = new TwoTalonElevator(7, 8);
            else if (elevatorClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.elevator.TalonElevator"))
                elevator = new TalonElevator(5);
        }
        else
        {
            System.err.println("no elevator specified");
        }

        String climbingMechanismClass = config.getMappedString("ClimbingMechanism");
        if (climbingMechanismClass != null) {
            if (climbingMechanismClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.climbing.PistonLift"))
                climbingMechanism = new PistonLift(6, 0);
            else if (climbingMechanismClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.climbing.ScissorLift"))
                climbingMechanism = new ScissorLift(5);
        } else {
            System.err.println("no climbing mechanism specified");
        }

        int numberOfCameras = config.getMappedInt("NumberOfCameras");
        for (int i = 0; i < numberOfCameras; ++i)
            CameraServer.getInstance().startAutomaticCapture(i);

        oi = new OI(config);

        SmartDashboard.putData(driveTrain);

        // Add Command Buttons to Smart Dashboard
        SmartDashboard.putData(new TurnAngleCommand());
        SmartDashboard.putData(new TurnAnglePIDCommand(Angle.fromDegrees(90)));
        SmartDashboard.putData(new DriveStraightPIDCommand());
        SmartDashboard.putData(new DriveStraightDistancePIDCommand());
        SmartDashboard.putData(new FollowLineWithCameraCommand());
    }
    @Override
    public void disabledInit() {

    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        // Angle a = new Angle(1.5);
        // Scheduler.getInstance().add(new TurnAngle(driveTrain, a, 0.5)); // test change to TurnAngle 180
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // tazGrabber.deploy();
        System.out.println("Initiated teleop");
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //if (autonomousCommand != null)
         //   autonomousCommand.cancel();
    }

    /*
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
