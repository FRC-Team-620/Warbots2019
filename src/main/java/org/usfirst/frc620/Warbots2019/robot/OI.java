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

import org.usfirst.frc620.Warbots2019.automation.Dance;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveDistance;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveWithJoystick;
import org.usfirst.frc620.Warbots2019.drivetrain.SpinOutOfControl;
import org.usfirst.frc620.Warbots2019.elevator.MoveElevatorTo;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        coDriverController = new Joystick(1);
        
        moveElevatorToTop = new JoystickButton(coDriverController, 1);
        moveElevatorToTop.whenPressed(new MoveElevatorTo(100));
        
        driverController = new Joystick(0);
        
        spinOutOfControlButton = new JoystickButton(driverController, 1);
        spinOutOfControlButton.whileHeld(new SpinOutOfControl());

        //NEED TO CHECK IF THE BUTTON NUMBER IS RIGHT, I ASSUME THAT BUTTON X IS NUMBER 13?
        driveStraightButton = new JoystickButton(driverController, 13);
        driveStraightButton.whenPressed(new DriveDistance(10, 0.1));


        // SmartDashboard Buttons
        SmartDashboard.putData("Spin Out Of Control", new SpinOutOfControl());
        SmartDashboard.putData("Drive With Joystick", new DriveWithJoystick());
        SmartDashboard.putData("Dance", new Dance());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriverController() {
        return driverController;
    }

    public Joystick getCoDriverController() {
        return coDriverController;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
