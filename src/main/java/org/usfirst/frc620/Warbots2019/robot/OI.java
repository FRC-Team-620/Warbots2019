/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.robot;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups the allow control of the robot.
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

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    public JoystickButton spinOutOfControlButton;
    public Joystick driverController;
    public JoystickButton moveElevatorToTop;
    public Joystick scorerController;
    public JoystickButton bButton;
    public JoystickButton xButton;
    public JoystickButton aButton;

    private AxisSpecification speedSpec;
    private AxisSpecification robotRotationSpec;
    private AxisSpecification elevatorSpeedSpec;

    ArrayList<AxisSpecification>  dynamicControls;

    /**
     * The Operator Interface of the robot lets the driver and scorer interact with
     * the robot through commands.
     * It takes a ControlReader to look at the .properties config file
     * 
     * Designed and edited by Ethan Wolman and Sam Parker
     * 
     * @param config
     */
    public OI(ControlReader config) {
        speedSpec = null;
        robotRotationSpec = null;
        elevatorSpeedSpec = null;
        dynamicControls = new ArrayList<AxisSpecification>();

        boolean driverEnabled = config.getMappedBoolean("driver.enabled");
        boolean scorerEnabled = config.getMappedBoolean("scorer.enabled");

        Logger.log("Driver A: [" + config.getMappedString("driver.A.pressed") + "]");

        if (driverEnabled)
        {
            driverController = new Joystick(0);
            Logger.log("driver enabled");
            if (scorerEnabled) {
                scorerController = new Joystick(1);
                Logger.log("scorer enabled");
            } else {
                Logger.log("scorer not enabled");
            }
        } else {
            Logger.log("driver not enabled");
            if (scorerEnabled) {
                scorerController = new Joystick(0);
                Logger.log("scorer enabled");
            } else
                Logger.log("scorer not enabled");
        }

        ArrayList<String> availableBinaryControls = new ArrayList<String>(Arrays.asList(
            
            // Driver commands
            "driver.A.pressed",
            "driver.B.pressed",
            "driver.X.pressed",
            "driver.Y.pressed",
            "driver.LeftBumper.pressed",
            "driver.RightBumper.pressed",
            "driver.Back.pressed",
            "driver.Start.pressed",

            // Scorer commands 
            "scorer.A.pressed",
            "scorer.B.pressed",
            "scorer.X.pressed",
            "scorer.Y.pressed",
            "scorer.LeftBumper.pressed",
            "scorer.RightBumper.pressed",
            "scorer.Back.pressed",
            "scorer.Start.pressed"
        ));

        ArrayList<String> availableAnalogControls = new ArrayList<String>(Arrays.asList(
            
            // Driver commands
            "driver.LeftJS.X",
            "driver.LeftJS.Y",
            "driver.RightJS.X",
            "driver.RightJS.Y",
            "driver.LeftTrigger",
            "driver.RightTrigger",

            // Scorer commands 
            "scorer.LeftJS.X",
            "scorer.LeftJS.Y",
            "scorer.RightJS.X",
            "scorer.RightJS.Y",
            "scorer.LeftTrigger",
            "scorer.RightTrigger"
        ));

        //
        // Loop through the possible controls
        //
        
        Logger.log("Looping through the possible binary controls");
        for(int i = 0; i < availableBinaryControls.size(); i++)
        {
            String ctrl = availableBinaryControls.get(i);
            
            if ((driverEnabled && ctrl.startsWith("driver")) ||
                (scorerEnabled && ctrl.startsWith("scorer")))
            {
            
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
        }
        
        Logger.log("Looping through the possible analog controls ["+availableAnalogControls.size()+"]");

        for(int i = 0; i < availableAnalogControls.size(); i++)
        {
            String ctrl = availableAnalogControls.get(i);
            Logger.log("dynamic control mapping: ["+i+"] ["+ctrl+"]");
            String cfgValue = config.getMappedString(ctrl);
            if (cfgValue != null)
            {
                Logger.log("            =["+cfgValue+"]");
                AxisSpecification axisSpec = AxisSpecification.buildAxisSpecification(ctrl, cfgValue);

                if (driverEnabled && ctrl.startsWith("driver"))
                {
                    axisSpec.userDesignation = AxisSpecification.UserDesignation.USER_DRIVER;
                }
                else if(scorerEnabled && ctrl.startsWith("scorer"))
                {
                    axisSpec.userDesignation = AxisSpecification.UserDesignation.USER_SCORER;
                }

                if (axisSpec.isValidAxisSpecification(axisSpec))
                {
                    dynamicControls.add(axisSpec);
                }
                Logger.log("  new AxisSpec: "+axisSpec.toString());
            }
            else
            {
                System.err.println("OI Unknown cfg value for "+ctrl);
            }
        }
    }

    /**
     * Gets the driver controller
     * @return driverController
     */
    public Joystick getDriverController() 
    {
        return driverController;
    }

    /**
     * Provides the commanded dynamic speed control from operator
     * regardless of which control is providing the data. This typically is
     * coming from the XBox joystick Y axis, but could be from something
     * else.
     * @return The current relative speed value, from -1.0 to 1.0
     */
    public double getRobotSpeed()
    {
        double ret = 0.0;
        if (speedSpec == null)
        {
            for (int i = 0; i<dynamicControls.size(); i++)
            {
                AxisSpecification t = dynamicControls.get(i);
                if ((t.userDesignation == AxisSpecification.UserDesignation.USER_DRIVER) &&
                    (t.valueType == AxisSpecification.UserControlValueType.UCVT_ROBOT_SPEED))
                {
                    speedSpec = t;
                    break;
                }
            }
            SmartDashboard.putNumber("Robot Speed", ret);
        }
        if (speedSpec != null)
        {
            ret = getAnalogValue(speedSpec);
            //Logger.log("ret: "+ret);
        }
        else
        {
            System.err.println("IO.getRobotSpeed: no speed specification found!");
        }
        return ret;
    }


    /**
     * Provides the commanded dynamic rotation rate control from operator
     * regardless of which control is providing the data. This typically is
     * coming from the XBox joystick X axis, but could be from something
     * else.
     * @return The current relative rotation value, from -1.0 to 1.0
     */
    public double getRobotRotationRate()
    {
        double ret = 0.0;
        if (robotRotationSpec == null)
        {
            for (int i = 0; i<dynamicControls.size(); i++)
            {
                AxisSpecification t = dynamicControls.get(i);

                if ((t.userDesignation == AxisSpecification.UserDesignation.USER_DRIVER) &&
                    (t.valueType == AxisSpecification.UserControlValueType.UCVT_ROBOT_ROTATION_RATE))
                {
                    robotRotationSpec = t;
                    break;
                }
            }
        }
        if (robotRotationSpec != null)
        {
            ret = getAnalogValue(robotRotationSpec);
        }
        else
        {
            System.err.println("getRobotRotationRate: no rotation rate control spec");
        }

        SmartDashboard.putNumber("Robot Rotation Rate", ret);
        return ret;
    }

    public double getElevatorSpeed()
    {
        double ret = 0.0;
        if (elevatorSpeedSpec == null)
        {
            for (int i = 0; i<dynamicControls.size(); i++)
            {
                AxisSpecification t = dynamicControls.get(i);

                if (t.valueType == AxisSpecification.UserControlValueType.UCVT_ELEVATOR_SPEED)
                {
                    Logger.log("getElevatorSpeed: got Elevator speed spec");
                    elevatorSpeedSpec = t;
                    
                    break;
                }
            }
        }
        if (elevatorSpeedSpec != null)
        {
            ret = getAnalogValue(elevatorSpeedSpec);
        }
        else
        {
            System.err.println("getElevatorSpeed: no elevator speed control spec");
        }
        SmartDashboard.putNumber("Elevator Speed", ret);
        return ret;
    }
    
    /**
     * Loads commands onto joysticks in the OI constructor
     * Only press and release is supported currently
     * @param ctrl
     * @param nameOfTheCMD
     */
    private void loadCommandOntoJoystick(String ctrl, String nameOfTheCMD)
    {
        Joystick controller = null;

        String t = "driver";

        // Differentiate which controller is getting talked to
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
            // Successfully determined the controller
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
            
            // Handle "analog" controls: joysticks, D-Pad, Triggers
            //    These don't map commands to Joystick, but that put commands
            //    on the scheduler and then those command watch the HID controls
            //    in order to know what to do.
        }
        else
        {
            // Unsuccessful determination of the controller
            Logger.log("Unsuccessful determination of the controller: Control = " + ctrl);
        }
     
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
            try
            {
                Class<?> clw = Class.forName(pkgs[i].getName() + "." + str);
                ret = (Command) clw.getDeclaredConstructor().newInstance();
                Logger.log("Successfully allocated the command " + ret + " to the robot!");
                break;
            }
            catch(Exception e)
            { 
                // We are expecting a lot of exceptions, so no system.err.println or printStackTrace is needed
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
            button = new JoystickButton(controller, buttonNumber);
            cmd = createCommand(nameOfTheCMD);
            if(cmd != null)
                button.whenPressed(cmd);
        }
    }
    
    /**
     * Determines current analog value from an axis specification
     * @param axisSpec
     * @return The current control's value (-1.0 to 1.0)
     */
    private double getAnalogValue(AxisSpecification axisSpec)
    {
        double ret = 0.0;
        if (axisSpec.controlType == AxisSpecification.AnalogControlType.CONTROL_LEFT_JOYSTICK)
        {
            GenericHID joystick = driverController;
            if (axisSpec.userDesignation == AxisSpecification.UserDesignation.USER_SCORER)
            {
                joystick = scorerController;
            }
            //if (joystick != null)
            //{
            
                if (axisSpec.axis == AxisSpecification.ControlAxis.AXIS_UP_DOWN)
                {
                    // Left JS Y
                    ret = joystick.getRawAxis(1);
                }
                else if (axisSpec.axis == AxisSpecification.ControlAxis.AXIS_LEFT_RIGHT)
                {
                    // Left JS X
                    ret = joystick.getRawAxis(0);
                }
           // }                    
        }
        else if (axisSpec.controlType == AxisSpecification.AnalogControlType.CONTROL_RIGHT_JOYSTICK)
        {
            GenericHID joystick = driverController;
            if (axisSpec.userDesignation == AxisSpecification.UserDesignation.USER_SCORER)
            {
                joystick = scorerController;
            }

            //if (joystick != null)
            //{
                if (axisSpec.axis == AxisSpecification.ControlAxis.AXIS_UP_DOWN)
                {
                    // Right JS Y
                    ret = joystick.getRawAxis(5);
                }
                else if (axisSpec.axis == AxisSpecification.ControlAxis.AXIS_LEFT_RIGHT)
                {
                    // Right JS X
                    ret = joystick.getRawAxis(4);
                }               
            //}     
        }
        else if (axisSpec.controlType == AxisSpecification.AnalogControlType.CONTROL_LEFT_TRIGGER)
        {
            GenericHID joystick = driverController;
            //if (joystick != null)
            //{
                if (axisSpec.userDesignation == AxisSpecification.UserDesignation.USER_SCORER)
                {
                    joystick = scorerController;
                }
                ret = joystick.getRawAxis(3);
            //}
        }
        else if (axisSpec.controlType == AxisSpecification.AnalogControlType.CONTROL_RIGHT_TRIGGER)
        {
            GenericHID joystick = driverController;
            //if (joystick != null)
            //{
                if (axisSpec.userDesignation == AxisSpecification.UserDesignation.USER_SCORER)
                {
                    joystick = scorerController;
                }
                ret = joystick.getRawAxis(2);
            //}
        }
        // Etc...

        return ret;
    }
    /**
     * Reloads the configuration from files TODO so far this is just redoing what is in the constructor, what really needs to be done?
     */
    public void reloadConfig(String filename, ControlReader config)
    {
        dynamicControls = new ArrayList<AxisSpecification>();
        boolean driverEnabled = config.getMappedBoolean("driver.enabled");
        boolean scorerEnabled = config.getMappedBoolean("scorer.enabled");
        //System.out.println("Driver A: [" + config.getMappedString("driver.A.pressed") + "]");


        if (driverEnabled)
        {
            driverController = new Joystick(0);
            System.out.println("driver enabled");
        }
        else
            System.out.println("driver not enabled");
        if (scorerEnabled)
        {
            scorerController = new Joystick(1);
            System.out.println("scorer enabled");
        }
        else
            System.out.println("scorer not enabled");
        ArrayList<String> availableBinaryControls = new ArrayList<String>(Arrays.asList(
            "driver.A.pressed",
            "driver.B.pressed",
            "driver.X.pressed",
            "driver.Y.pressed",
            "driver.LeftBumper.pressed",
            "driver.RightBumper.pressed",
            "driver.Back.pressed",
            "driver.Start.pressed",
            /*"driver.LeftStick",
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
            "driver.RightDeadzoneY",*/
            //Scorer commands 
            "scorer.A.pressed",
            "scorer.B.pressed",
            "scorer.X.pressed",
            "scorer.Y.pressed",
            "scorer.LeftBumper.pressed",
            "scorer.RightBumper.pressed",
            "scorer.Back.pressed",
            "scorer.Start.pressed"
            /*"scorer.LeftStick",
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
            "scorer.RightDeadzoneY" */
        ));

        ArrayList<String> availableAnalogControls = new ArrayList<String>(Arrays.asList(
            "driver.LeftJS.X",
            "driver.LeftJS.Y",
            "driver.RightJS.X",
            "driver.RightJS.Y",
            "driver.LeftTrigger",
            "driver.RightTrigger",
            //Scorer commands 
            "scorer.LeftJS.X",
            "scorer.LeftJS.X",
            "scorer.RightJS.X",
            "scorer.RightJS.X",
            "scorer.LeftTrigger",
            "scorer.RightTrigger"
        ));

        //
        // Loop through the possible controls
        //
        System.out.println("Looping through the possible binary controls");
        for(int i = 0; i < availableBinaryControls.size(); i++)
        {
            String ctrl = availableBinaryControls.get(i);
            
            if ((driverEnabled && ctrl.startsWith("driver")) ||
                (scorerEnabled && ctrl.startsWith("scorer")))
            {
            
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
        }
        
        System.out.println("Looping through the possible analog controls ["+availableAnalogControls.size()+"]");
        for(int i = 0; i < availableAnalogControls.size(); i++)
        {
            String ctrl = availableAnalogControls.get(i);
            System.out.println("dynamic control mapping: ["+i+"] ["+ctrl+"]");
            String cfgValue = config.getMappedString(ctrl);
            if (cfgValue != null)
            {
                System.out.println("            =["+cfgValue+"]");
                AxisSpecification axisSpec = AxisSpecification.buildAxisSpecification(ctrl, cfgValue);

                if (driverEnabled && ctrl.startsWith("driver"))
                {
                    axisSpec.userDesignation = AxisSpecification.UserDesignation.USER_DRIVER;
                }
                else if(scorerEnabled && ctrl.startsWith("scorer"))
                {
                    axisSpec.userDesignation = AxisSpecification.UserDesignation.USER_SCORER;
                }

                if (axisSpec.isValidAxisSpecification(axisSpec))
                {
                    dynamicControls.add(axisSpec);
                }
            }
        }
    }
    public Configurable asConfigurable()
    {
        ConfigurableImpl ret = new ConfigurableImpl();
        addBinaryOIControls("driver", ret);
        addBinaryOIControls("scorer", ret);
        
        final ArrayList<String> analogCommands = new ArrayList<String>(Arrays.asList(
            "OI.robot.speed", "OI.robot.rotation_rate", "OI.elevator.speed", "OI.mech.deploy_rate"));

        ret.addElement(new Element("OI.LeftJS.X", "This is an analog control and therefore this maps " + 
        "to OI analog functionalities. ", analogCommands));
        return ret;
    }   
    private void addBinaryOIControls(String user, ConfigurableImpl ret)
    {
        final ArrayList<String> binaryCommands = new ArrayList<String>(Arrays.asList(
            "tazOpenCommand", "tazStowCommand"));
        
        ret.addElement(new Element(user + ".A.pressed", "This is a binary control (like b, x, y, leftBumper, rightBumper, " + 
             "All the possible binary commands are listed below. ", binaryCommands));
        ret.addElement(new Element(user + ".B.pressed", "B Button (binary command)", null));
        ret.addElement(new Element(user + ".X.pressed", "X Button (binary command)", null));
        ret.addElement(new Element(user + ".Y.pressed", "Y Button (binary command)", null));
        ret.addElement(new Element(user + ".LB.pressed", "Left Bumper (binary command)", null));
        ret.addElement(new Element(user + ".RB.pressed", "Right Bumper (binary command)", null));
        
        
/**    
#B Button
driver.B.pressed = 
#X Button
driver.X.pressed = 
#Y Button
driver.Y.pressed = 
#Left Bumper
driver.LeftBumper.pressed = 
#Right Bumper
driver.RightBumper.pressed = 
#Back Button
driver.Back.pressed = 
#Start Button
driver.Start.pressed = 
#Left Stick 
driver.LeftJS.Y = OI.robot.speed
driver.LeftJS.X = OI.robot.rotation_rate
#Right Stick 
driver.RightJS.Y = OI.elevator.speed
driver.RightJS.X = #OI.robot.rotation_rate 
#Left Trigger
driver.LeftTrigger = 
#Right Trigger
driver.RightTrigger = 
#D-Pad Up
driver.DPadUp = 
#D-Pad Down
driver.DPadDown = 
#D-Pad Left
driver.DPadLeft =
#D-Pad Right
driver.DPadRight =
#Left Stick (Movement)
driver.LeftAxis =
#Right Stick (Movement)
driver.RightAxis =

#Other Variables
#Left Stick Deadzones
driver.LeftDeadzoneX =
driver.LeftDeadzoneY =

        #Right Stick Deadzones
        driver.RightDeadzoneX =     
        driver.RightDeadzoneY = 
        */
    }
}