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

import org.usfirst.frc620.Warbots2019.automation.AlignmentSystem;
import org.usfirst.frc620.Warbots2019.automation.AutonomousCommand;
import org.usfirst.frc620.Warbots2019.automation.TrackingSystem;
//import org.usfirst.frc620.Warbots2019.drivetrain.DriveDistance;
import org.usfirst.frc620.Warbots2019.drivetrain.TurnAngle;
import org.usfirst.frc620.Warbots2019.drivetrain.NavX.Port;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveTrain;
import org.usfirst.frc620.Warbots2019.drivetrain.NavX;
import org.usfirst.frc620.Warbots2019.drivetrain.SparkDriveTrain;
//import org.usfirst.frc620.Warbots2019.drivetrain.NavX.Port;
import org.usfirst.frc620.Warbots2019.elevator.Elevator;
//import org.usfirst.frc620.Warbots2019.elevator.TalonElevator;
import org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.*;
import org.usfirst.frc620.Warbots2019.utility.Angle;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {
    private static Compressor compressor;
    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Elevator elevator;
    public static AlignmentSystem alignmentSystem;
    public static TrackingSystem trackingSystem;
    public static TazGrabber tazGrabber;
    public static CargoMech cargoMech;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        System.out.println("Robot initiated");

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        //driveTrain = new SparkMaxDriveTrain(1, 2, 3, 4);
         ControlReader config = new ControlReader();
         String driverTrainClass = config.getMappedString("DriveTrain");
        driveTrain = new SparkDriveTrain(1, 2, 3, 4, Port.SPIMXP);
        cargoMech = new CargoMech(0, 4);
        
        if(driverTrainClass != null )
        {
            if(driverTrainClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.drivetrain.SparkDriveTrain"))
        {
            // TODO: Make the subsystem constructors take the ControlReader reference
            //       and have their constructors extract the info they need internally.
            driveTrain = new SparkDriveTrain(1, 2, 3, 4, NavX.Port.SPIMXP);
        } 
        else if(driverTrainClass.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.drivetrain.SparkMaxDriveTrain"))
        {
            
            driveTrain = new SparkDriveTrain(1, 2, 3, 4, NavX.Port.SPIMXP);
        }
        else System.err.println("no drive train specified");
    }
        String ScoringMechanism = config.getMappedString("ScoringMechanism");
        if (ScoringMechanism != null)
        {
            if(ScoringMechanism.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.TazGrabber"))
            {

               tazGrabber = new TazGrabber(5,6,5,7,4,2,0,3,1);
            }
            else if(ScoringMechanism.equalsIgnoreCase("org.usfirst.frc620.Warbots2019.mechanisms.cargo.CargoMech"))
            {
                cargoMech = new CargoMech(0, 4);
            }
        else
        {
            System.err.println("no scoring mech specified");
            //We will have to get the correct inputs for Cargo mech, just guessing.
            cargoMech = new CargoMech(0, 4);
            //tazGrabber = new TazGrabber(5,6,5,7,4,2,0,3,1);
        }
    } 
    
        //elevator = new TalonElevator(5);
        //compressor = new Compressor(6);
        
        
        //compressor.setClosedLoopControl(true);
        //compressor.start();

        //
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.

        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void robotPeriodic(){
    System.out.print("");
    
    }
    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Angle a = new Angle(1.5);
        Scheduler.getInstance().add(new TurnAngle(driveTrain, a, 0.5));
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
        //tazGrabber.deploy();
        System.out.println("Initiated teleop");
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /*
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        if(cargoMech.hasCargo()){
        cargoMech.stopCapture();
        }  
        
        /*if(tazGrabber.hasGameObject())
        {
        tazGrabber.stop(); 
        tazGrabber.stow(); 
        }*/
    }
}
