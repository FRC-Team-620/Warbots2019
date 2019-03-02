/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.robot;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
import org.usfirst.frc620.Warbots2019.elevator.Elevator;
import org.usfirst.frc620.Warbots2019.elevator.TalonElevator;
import org.usfirst.frc620.Warbots2019.elevator.TwoTalonElevator;
import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech;
import org.usfirst.frc620.Warbots2019.mechanisms.pinchPointGearGrabber.PinchPointGearGrabber;
import org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber;
import org.usfirst.frc620.Warbots2019.sim.SimDriveTrain;
import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;
import org.usfirst.frc620.Warbots2019.vision.FollowLineWithCameraCommand;
import org.usfirst.frc620.Warbots2019.vision.VisionSubsystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    //public static Compressor compressor;
    public static DriveTrain driveTrain;
    public static Elevator elevator;
    public static ScoringMechanism scoringMechanism;
    public static ClimbingMechanism climbingMechanism;
    public static OI oi;
    public static Compressor compressor;
    public static VisionSubsystem visionSystem;
    
    // Control Reader enables configuration for multiple robots and operators
    public static ControlReader config;

    // SendableChooser lets you select the autonomous command to run from the SmartDashboard
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    /*
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        Logger.log("robotInit: Robot initialized");

        config = new ControlReader();

        Logger.log("robotInit: Connecting to robot " + config.getRobotType());
     
        String driverTrainClass = config.getMappedString("DriveTrain");
        if (driverTrainClass != null) {
            if (driverTrainClass.equalsIgnoreCase(
                "org.usfirst.frc620.Warbots2019.drivetrain.SparkDriveTrain")) {
                driveTrain = new SparkDriveTrain(1, 2, 3, 4, NavX.Port.SPIMXP);
                Logger.log("robotInit: Configured with SparkDriveTrain");
            } else if (driverTrainClass.equalsIgnoreCase(
                "org.usfirst.frc620.Warbots2019.drivetrain.SparkMaxDriveTrain")) {
                driveTrain = new SparkMaxDriveTrain(1, 2, 3, 4, NavX.Port.SerialUSB);
                Logger.log("robotInit: Configured with SparkMaxDriveTrain");
            } else if (driverTrainClass.equalsIgnoreCase(
                "org.usfirst.frc620.Warbots2019.sim.SimDriveTrain")) {
                driveTrain = new SimDriveTrain();
                Logger.log("robotInit: Configured with SimDriveTrain");
            } else {
                Logger.log("robotInit: Configured with no drive train ");
            }
        }
        SmartDashboard.putData(driveTrain);

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
            else if (ScoringMechanism
                    .equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech"))
                scoringMechanism = new CargoMech(9, 6, 4, 6, 11, 12);
            else if (ScoringMechanism
                    .equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.pinchPointGearGrabber.PinchPointGearGrabber"))
                scoringMechanism = new PinchPointGearGrabber(5, 2, 3);
        } else {
            System.out.println("no scoring mech specified");
        }
        SmartDashboard.putData(scoringMechanism);

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
        SmartDashboard.putData(elevator);                                                

        String climbingMechanismClass = config.getMappedString("ClimbingMechanism");
        if (climbingMechanismClass != null) {
            if (climbingMechanismClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.climbing.PistonLift"))
                climbingMechanism = new PistonLift(6, 0);
            else if (climbingMechanismClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.climbing.ScissorLift"))
                climbingMechanism = new ScissorLift(5);
        } else {
            System.err.println("no climbing mechanism specified");
        }
        SmartDashboard.putData(climbingMechanism);

        visionSystem = new VisionSubsystem(config);
        

        oi = new OI(config);

        // Enable Shuffleboard logging
        Shuffleboard.startRecording();
        
        Shuffleboard.addEventMarker("Robot initialized", EventImportance.kTrivial);

        // Add Subsystems to SmartDashboard
        SmartDashboard.putData(driveTrain);

        // Add Command Buttons to Smart Dashboard
        SmartDashboard.putData(new TurnAngleCommand());
        SmartDashboard.putData(new TurnAnglePIDCommand());
        SmartDashboard.putData(new DriveStraightPIDCommand());
        SmartDashboard.putData(new DriveStraightDistancePIDCommand());
        SmartDashboard.putData(new FollowLineWithCameraCommand());
        SmartDashboard.putData(new FollowLineWithCameraCommand());
        // Specify Autonomous Choices
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);

        // Show config data on SmartDashboard
        SmartDashboard.putString("Robot Name", config.robotName);
        ArrayList<String> loadedFiles = ControlReader.getLoadedFiles();
        for (int i=0;i<loadedFiles.size();i++)
        {
            SmartDashboard.putString("Files", loadedFiles.get(i));
        }
      }

    @Override
    public void disabledInit() {
    }

    @Override
    public void robotPeriodic() {
        oi.periodic();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Shuffleboard.addEventMarker("Match start", EventImportance.kNormal);
        
        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        switch (m_autoSelected) {
            case kCustomAuto:
              // Put custom auto code here
              break;
            case kDefaultAuto:
            default:
              // Put default auto code here
              break;
      }
    }
    
    @Override
    public void teleopInit() {
    
        Shuffleboard.addEventMarker("Teleop start", EventImportance.kNormal);
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        // if (autonomousCommand != null)
        //  autonomousCommand.cancel();
    }

    /*
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {

        Scheduler.getInstance().run();
    }

    public static Configurable asConfigurable()
    {
        // We now have a Configurable object with all methods implemented
        // so programs can carry it around like a suitcase
        ConfigurableImpl configurable = new ConfigurableImpl();
        configurable.addElement(new Element("name", "Name Of Robot", null));
        configurable.addElement(new Element("driver.enabled", "Whether to instantiate driverJoystick", new ArrayList<String>(Arrays.asList("true", "false"))));
        configurable.addElement(new Element("scorer.enabled", "Whether to instantiate scorerJoystick", new ArrayList<String>(Arrays.asList("true", "false"))));
        
        // Add subsystems to Robot's Configurable
        configurable.addElement(new Element("DriveTrain", 
            "Fully-scoped name of a DriveTrain implementation.", 
            new ArrayList<String>(Arrays.asList(
                "org.usfirst.frc620.Warbots2019.drivetrain.SparkDriveTrain", 
                "org.usfirst.frc620.Warbots2019.drivetrain.SparkMaxDriveTrain",
                "org.usfirst.frc620.Warbots2019.sim.SimDriveTrain"))));
        //New Element
        configurable.addElement(new Element("ScoringMechanism", 
            "Fully-scoped name of a ScoringMechanism implementation.", 
            new ArrayList<String>(Arrays.asList(
                "org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech", 
                "org.usfirst.frc620.Warbots2019.mechanisms.pinchPointGearGrabber",
                "org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber"))));
        //New Element
        configurable.addElement(new Element("ClimbingMechanism", 
            "Fully-scoped name of a ClimbingMechanism implementation",
            new ArrayList<String>(Arrays.asList(
                "org.usfirst.frc620.Warbots2019.climbing.PistonLift",
                "org.usfirst.frc620.Warbots2019.climbing.ScissorLift"
                ))));
        //New Element
        configurable.addElement(new Element("Elevator", 
            "Fully-scopes name of an Elevator implementation",
            new ArrayList<String>(Arrays.asList(
                "org.usfirst.frc620.Warbots2019.elevator.TalonElevator",
                "org.usfirst.frc620.Warbots2019.elevator.TwoTalonElevator"))));
        
        //New Element
        configurable.addElement(new Element("Compressor",
            "The pump that powers all pneumatic systems",
            new ArrayList<String>(Arrays.asList(
                "true", "false"))));

        configurable.addElement(new Element("Tracking", 
            "Fully-scoped name of a Tracking implementation.", 
            new ArrayList<String>(Arrays.asList("none - REVISIT IF THIS IS NECESSARY"))));

        configurable.addElement(new Element("Alignment", 
            "Fully-scoped name of a Alignment implementation.", 
                new ArrayList<String>(Arrays.asList("none - REVISIT IF THIS IS NECESSARY"))));

        configurable.addElement(new Element("NumberOfCameras", 
            "Number of cameras", 
                new ArrayList<String>(Arrays.asList("0","1"))));
        
        return configurable;
    }

    /**
     * Insantiate ONE version of each subsystem class and call asConfigurable and add it to the list for dumping in the ControlReader
     */
    public static void dumpConfiguration()
    {
        Logger.log("Dumping config for Robot");
        
        //Insantiate ONE version of each subsystem class and call asConfigurable and add it to the list for dumping in the ControlReader
        ArrayList<Configurable> configurables = new ArrayList<Configurable>();
        
        Configurable configurable = asConfigurable();
        configurables.add(configurable);

        // Ask each subsystem to add it's details (need to make sure commands of the
        // same name are overwritten.
        // Call default constructors - we only need them to populate their Configurable
        // instance, with commands, etc.
        configurables.add(DriveTrain.asConfigurable());
        configurables.add(CargoMech.asConfigurable());
        configurables.add(TazGrabber.asConfigurable());
        configurables.add(PinchPointGearGrabber.asConfigurable());
        configurables.add(Elevator.asConfigurable());
        
        // OI goes next - these settings eventually get distributed to
        // the driver and scorer properties files.
        configurables.add(OI.asConfigurable());
        
        if (new File("/home/lvuser").exists())
        {
            ControlReader.dumpConfigurationFile("/home/lvuser/demo.properties", configurables);
        }
        else
        {
            // save to local dir
            ControlReader.dumpConfigurationFile("demo.properties", configurables);
        }
    }
}
