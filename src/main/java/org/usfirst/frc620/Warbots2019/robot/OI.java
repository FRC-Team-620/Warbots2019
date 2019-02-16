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
//import org.usfirst.frc620.Warbots2019.drivetrain.DriveDistance;
import org.usfirst.frc620.Warbots2019.mechanisms.cargo.*;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups th allow control of the robot.
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


    /**
     *
     */

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
     * The Operator Interface of the robot, lets the driver and scorer interact with
     * the robot through commands Takes a ControlReader to look .properties config
     * files
     * 
     * Designed and edited by Ethan Wolman and Fred Parker
     * 
     * @param config
     */
    public OI(ControlReader config) 
    {
        boolean driverEnabled = config.getMappedBoolean("driver.enabled");
        boolean scorerEnabled = config.getMappedBoolean("scorer.enabled");
        String drvAPressed = config.getMappedString("driver.a.pressed");
        System.out.println("Driver A: [" + drvAPressed + "]");
        
        if (driverEnabled)
        {
            driverController = new Joystick(0);
        }
        if (scorerEnabled)
        {
            scorerController = new Joystick(1);
        }
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

        //
        // Loop through the possible controls
        //
        for(int i = 0; i < availableControls.size(); i++)
        {
            String ctrl = availableControls.get(i);
            
            if ((driverEnabled && ctrl.startsWith("driver")) ||
                (scorerEnabled && ctrl.startsWith("scorer")))
            {
            
                String val = config.getMappedString(ctrl);
                if(val != null)
                {
                    val = val.trim();
                
                    if(val.length() > 0)
                    {
                        System.out.println("Val = " + val + " Val.length = " + val.length());
                        loadCommandOntoJoystick(ctrl, val);
                    }
                }
            }
        }
     
         //spinOutOfControlButton = new JoystickButton(driverController, 1);
         //spinOutOfControlButton.whileHeld(new DbuiriveDistance(Robot.driveTrain, 1.0, 0.1));

         //NEED TO CHECK IF THE BUTTON NUMBER IS RIGHT, I ASSUME THAT BUTTON X IS NUMBER 13?
         //I don't know where you got 13 from. I think it might be 3? You can check in the drive station.

        // A Button
        aButton = new JoystickButton(driverController, 1);
        aButton.whileHeld(new CaptureCargoCommand());

     /*   // B Button 
        bButton = new JoystickButton(driverController, 2);
        bButton.whileHeld(new StopCaptureCommand());

        //X Button
        xButton = new JoystickButton(driverController, 3);
        xButton.whileHeld(new EjectCargoCommand());

        // // Y Button
        //yButton = new JoystickButton(driverController, 4);
        //yButton.whenPressed(new ControlCargo());

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
        //startButton = new JoystickButton(driverController, 1);
        //startButton.whenPressed(new CaptureCargo());


        //coDriver buttons
        //coDriverController = new Joystick(1);

        //moveElevatorToTop = new JoystickButton(coDriverController, 1);
        //moveElevatorToTop.whenPressed(new MoveElevatorTo(Robot.elevator, 100));

        //NEED TO CHECK IF THE BUTTON NUMBER IS RIGHT, I ASSUME THAT BUTTON X IS NUMBER 13?
        //I don't know where you got 13 from. I think it might be 3? You can check in the drive station.

         //SmartDashboard.putData("Spin Out Of Control", new SpinOutOfControl());
         //SmartDashboard.putData("Drive With Joystick", new DriveWithJoystick());
         //SmartDashboard.putData("Dance", new Dance());
*/
    }

    /**
     * Gets the driver controller
     * @return driverController
     */
    public Joystick getDriverController() 
    {
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
        Command cmd = null;
        String t = "driver";
        //Differentiate which controller is getting talked to
        if(ctrl.startsWith("driver"))
        {
            controller = driverController;
        }
        else if(ctrl.startsWith("scorer"))
        {
            controller = scorerController;
            t = "scorer";
        }
        if(controller != null)
        {
            //Successfully determined the controller
            //  Handle "binary" controls - buttons.
            //  There are three events possible - a press event, a release event, or a press and release that is one event
            //  We only support the press and release buttons currently
            //
            // NOTE: 't + ".A.pressed"' and 'ctrl' are redundant?
            // Switch the two parameters
            bob(ctrl, t + ".A.pressed", controller, nameOfTheCMD, 1);
            bob(ctrl, t + ".B.pressed",  controller, nameOfTheCMD, 2);
            bob(ctrl, t + ".X.pressed", controller, nameOfTheCMD, 3);
            bob(ctrl, t + ".Y.pressed", controller, nameOfTheCMD, 4);
            bob(ctrl, t + ".LeftBumper.pressed", controller, nameOfTheCMD, 5);
            bob(ctrl, t + ".RightBumper.pressed", controller, nameOfTheCMD, 6);
            bob(ctrl, t + ".Back.pressed", controller, nameOfTheCMD, 7);
            bob(ctrl, t + ".Start.pressed", controller, nameOfTheCMD, 8);
            
            // TODO Handle "analog" controls: joysticks, D-Pad, Triggers
            //    These don't map commands to Joystick, but that put commands
            //    on the scheduler and then those command watch the HID controls
            //    in order to know what to do.
        }
        else
            System.out.println("Unsuccessful determination of the controller: Control = " + ctrl);
        //Unsuccessful determination of the controller
    
    }
    
    /** 
     * Returns a newly created command based off a string
     * @param str
     * @return Command ret
     */
    private Command createCommand(String str)
    {
        Command ret = null;
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Package[] pkgs = cl.getDefinedPackages();
        
        for(int i = 0; i < pkgs.length; i++)
        {
            System.out.println("Package = " + pkgs[i].toString());
            System.out.println("Bob: " + pkgs[i].getName());
            try
            {
                Class<?> clw = Class.forName(pkgs[i].getName() + "." + str);
                ret = (Command) clw.getDeclaredConstructor().newInstance();
                System.out.println("Successfully allocated the command!");
                break;
            }catch(Exception e){ 
                //We are expecting a lot of exceptions, so no system.err.println or printStackTrace is needed
                
            }
        
        }
        return ret;        
    }
    
    /** 
     * Map a "binary" control (i.e. button press) to a one-off command object.
     * This does not map variable controls like XBox joystick or trigger.
     * @param str - control string, as came from configuration file
     * @param ctrl - name of command class (minus package)
     * @param controller - The joystick to have Commands applied to
     * @param nameOfTheCMD - name of command class (minus package)
     * @param buttonNumber - HID number of the button 
     */
    private void bob(String str, String ctrl, Joystick controller, String nameOfTheCMD, int buttonNumber)
    {
        JoystickButton button = null;
        Command cmd = null;
        if(ctrl.contains(str))
        {
            System.out.println("Control has " + str);
            button = new JoystickButton(controller, buttonNumber);
            cmd = createCommand(nameOfTheCMD);
            if(cmd != null)
                button.whenPressed(cmd);
        }
        else
        {
            System.out.println("Control does not have " + str);
        }
    }
}