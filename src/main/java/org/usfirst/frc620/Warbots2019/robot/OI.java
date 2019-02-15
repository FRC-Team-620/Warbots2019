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

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
//import org.usfirst.frc620.Warbots2019.drivetrain.DriveDistance;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.*;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import edu.wpi.first.wpilibj.command.Command;
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
    public Joystick scorerController;
    public JoystickButton driveStraightButton;
    public JoystickButton TurnAngleButton;
    public JoystickButton DriveDistanceButton;
    public JoystickButton DepositCargoButton;
    public JoystickButton DepositHatchButton;
    public JoystickButton openTazGrabberButton;
    public JoystickButton aButton;
    public JoystickButton bButton;
    public JoystickButton xButton;
    public JoystickButton yButton;
    public JoystickButton rbButton;
    public JoystickButton lbButton;
    public JoystickButton backButton;
    public JoystickButton startButton;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * The Operator Interface of the robot, lets the driver and scorer interact with the robot through commands
     * Takes a ControlReader to look .properties config files
     * 
     * Designed and edited by Ethan Wolman and Fred Parker
     * 
     * @param config
     */
    public OI(ControlReader config) {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        String drvAPressed = config.getMappedString("driver.A.pressed");
        System.out.println("Driver A: [" + drvAPressed + "]");

        driverController = new Joystick(0);
        
        ArrayList<String> availableControls = new ArrayList<String>(Arrays.asList(
            "driver.A.pressed",
            "driver.B.pressed",
            "driver.X.pressed",
            "driver.Y.pressed",
            "driver.LeftBumper.pressed",
            "driver.RightBumper.pressed",
            "driver.Back.pressed",
            "driver.Start.pressed",
            "driver.LeftStick",
            "driver.RightStick",
            "driver.LeftTrigger",
            "driver.RightTrigger",
            "driver.DPadUp",
            "driver.DPadDown",
            "driver.DPadLeft",
            "driver.DPadRight",
            "driver.LeftAxis",
            "driver.RightAxis",
            "driver.LeftDeadzoneX",
            "driver.LeftDeadzoneY",
            "driver.RightDeadzoneX",
            "driver.RightDeadzoneY",
            //Scorer commands 
            "scorer.A.pressed",
            "scorer.B.pressed",
            "scorer.X.pressed",
            "scorer.Y.pressed",
            "scorer.LeftBumper.pressed",
            "scorer.RightBumper.pressed",
            "scorer.Back.pressed",
            "scorer.Start.pressed",
            "scorer.LeftStick",
            "scorer.RightStick",
            "scorer.LeftTrigger",
            "scorer.RightTrigger",
            "scorer.DPadUp",
            "scorer.DPadDown",
            "scorer.DPadLeft",
            "scorer.DPadRight",
            "scorer.LeftAxis",
            "scorer.RightAxis",
            "scorer.LeftDeadzoneX",
            "scorer.LeftDeadzoneY",
            "scorer.RightDeadzoneX",
            "scorer.RightDeadzoneY" 
        ));

        for(int i = 0; i < availableControls.size(); i++)
        {
            String ctrl = availableControls.get(i);
            String val = config.getMappedString(ctrl);
            if(val != null)
            {
                val = val.trim();
                if(val.length() > 0)
                {
                    loadCommandOntoJoystick(ctrl, val);
                }
            }
        }
         //spinOutOfControlButton = new JoystickButton(driverController, 1);
         //spinOutOfControlButton.whileHeld(new DbuiriveDistance(Robot.driveTrain, 1.0, 0.1));

         //NEED TO CHECK IF THE BUTTON NUMBER IS RIGHT, I ASSUME THAT BUTTON X IS NUMBER 13?
         //I don't know where you got 13 from. I think it might be 3? You can check in the drive station.

        // A Button
        //aButton = new JoystickButton(driverController, 1);
        //aButton.whenPressed(new open());

        // B Button 
        bButton = new JoystickButton(driverController, 2);
        bButton.whenPressed(new StopCapture());

        // // X Button
        xButton = new JoystickButton(driverController, 3);
        xButton.whenPressed(new EjectCargo());

        // // Y Button
       // yButton = new JoystickButton(driverController, 4);
        //yButton.whenPressed(new Stow());

         // Right Bumper (rb)
        //rbButton = new JoystickButton(driverController, 5);
        //rbButton.whenPressed(new Capture());

        // // Left Bumper (lb)
        //lbButton = new JoystickButton(driverController, 6);
        //lbButton.whenPressed(new Eject());

        // // Back Button
        //backButton = new JoystickButton(driverController, 7);
        //backButton.whenPressed(new Stop());

        // // Start Button
        startButton = new JoystickButton(driverController, 1);
        startButton.whenPressed(new CaptureCargo());


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
    /**
     * Gets the driver controller
     * @return driverController
     */
    public Joystick getDriverController() {
        return driverController;
    }

    //public Joystick getCoDriverController() {
    //    return coDriverController;
    //}

    /**
     * Loads commands onto joysticks in the OI constructor
     * Only press and release is supported currently
     * @param ctrl
     * @param nameOfTheCMD
     */
    private void loadCommandOntoJoystick(String ctrl, String nameOfTheCMD)
    {
        Joystick controller = null;
        JoystickButton button = null;
        Command cmd = null;
        //Differentiate which controller is getting talked to
        if(ctrl.startsWith("driver"))
        {
            controller = driverController;
        }
        else if(ctrl.startsWith("scorer"))
        {
            controller = scorerController;
        }
        if(controller != null)
        {
            //Successfully determined the controller
            //There are now three events possible - a press event, a release event, or a press and release that is one event
            //We only support the press and release buttons currently
            bob(".A.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".B.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".X.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".Y.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".LeftBumper.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".RightBumper.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".Back.pressed", ctrl, controller, button, nameOfTheCMD);
            bob(".Start.pressed", ctrl, controller, button, nameOfTheCMD);
        }
        //Unsuccessful determination of the controller
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    /** 
     * Returns a newly created command based off a string
     * @param str
     * @return Command ret
     */
    private Command createCommand(String str)
    {
        Command ret = null;
        try
        {
            //str is already the control reader's mapped string
            Class<?> cl = Class.forName(str);
            ret = (Command) cl.getDeclaredConstructor().newInstance();
        }
        catch(Exception e) 
        {
            //Class not found exception
            System.err.println(e);
            e.printStackTrace();
        }
        return ret;        
    }
    /** 
     * Performs the "if" function used to create a command
     * @param str
     * @param ctrl
     * @param controller
     * @param button
     * @param nameOfTheCMD
     */
    private void bob(String str, String ctrl, Joystick controller, JoystickButton button, String nameOfTheCMD)
    {
        Command cmd = null;
        if(ctrl.contains(str))
        {
            button = new JoystickButton(controller, 2);
            //call an internal private method that returns a command instance
            cmd = createCommand(nameOfTheCMD);
            button.whenPressed(cmd);
        }
    }
    
}

