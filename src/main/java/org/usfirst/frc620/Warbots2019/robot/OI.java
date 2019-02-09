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

import org.usfirst.frc620.Warbots2019.drivetrain.DriveDistance;
import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;
import org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber.*; 
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton spinOutOfControlButton;
    public Joystick driverController;
    public JoystickButton moveElevatorToTop;
    public Joystick coDriverController;
    public JoystickButton driveStraightButton;
    public JoystickButton TurnAngleButton;
    public JoystickButton DriveDistanceButton;
    public JoystickButton DepositCargoButton;
    public JoystickButton DepositHatchButton;
    public JoystickButton openTazGrabberButton;
    public JoystickButton aButton;
    public JoystickButton bButton;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        
        driverController = new Joystick(0);
        
        spinOutOfControlButton = new JoystickButton(driverController, 1);
        spinOutOfControlButton.whileHeld(new DriveDistance(Robot.driveTrain, 1.0, 0.1));

        //NEED TO CHECK IF THE BUTTON NUMBER IS RIGHT, I ASSUME THAT BUTTON X IS NUMBER 13?
        //I don't know where you got 13 from. I think it might be 3? You can check in the drive station.
        driveStraightButton = new JoystickButton(driverController, 3);
        driveStraightButton.whenPressed(new DriveDistance(Robot.driveTrain, 10, 0.1));

        // A Button
        aButton = new JoystickButton(driverController, 1);
        aButton.whenPressed(new open());

        // B Button 
        bButton = new JoystickButton(driverController, 2);
        bButton.whenPressed(new Close());

        //closeTazButton = new JoystickButton(driverController, 11);
        //closeTazButton.whenPressed(new stow());

        //coDriver buttons
        //coDriverController = new Joystick(1);

        //moveElevatorToTop = new JoystickButton(coDriverController, 1);
        //moveElevatorToTop.whenPressed(new MoveElevatorTo(Robot.elevator, 100));

        //NEED TO CHECK IF THE BUTTON NUMBER IS RIGHT, I ASSUME THAT BUTTON X IS NUMBER 13?
        //I don't know where you got 13 from. I think it might be 3? You can check in the drive station.

         //SmartDashboard.putData("Spin Out Of Control", new SpinOutOfControl());
         //SmartDashboard.putData("Drive With Joystick", new DriveWithJoystick());
         //SmartDashboard.putData("Dance", new Dance());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriverController() {
        return driverController;
    }

    //public Joystick getCoDriverController() {
    //    return coDriverController;
    //}


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

