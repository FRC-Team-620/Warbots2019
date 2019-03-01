/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.robot;

public class AxisSpecification
{

    public enum AnalogControlType
    {
        CONTROL_UNDEFINED,
        CONTROL_LEFT_JOYSTICK,
        CONTROL_RIGHT_JOYSTICK,
        CONTROL_LEFT_TRIGGER,
        CONTROL_RIGHT_TRIGGER,
        CONTROL_DPAD // POV
    };
    
    public enum UserDesignation
    {
        USER_UNDEFINED,
        USER_DRIVER,
        USER_SCORER
    };

    public enum ControlAxis
    {
        AXIS_UNDEFINED,
        AXIS_UP_DOWN,
        AXIS_LEFT_RIGHT
    };
    
    public enum UserControlValueType
    {
        UCVT_UNDEFINED,
        UCVT_ROBOT_SPEED,         // -1.0 to 1.0
        UCVT_ROBOT_ROTATION_RATE, 
        UCVT_ELEVATOR_SPEED,     
        UCVT_DEPLOY_RATE,
        UCVT_POV_0_DEG,
        UCVT_POV_90_DEG,
        UCVT_POV_180_DEG,
        UCVT_POV_270_DEG
    };

    UserDesignation      userDesignation;
    AnalogControlType    controlType;
    ControlAxis          axis;
    UserControlValueType valueType;

    public boolean isValidAxisSpecification(AxisSpecification axisSpec)
    {
        // Only JOYSTICKS need distinction between X/Y axes - and therefore it's OK
        // if axis is not defined for non-joystick controls like DPad.
        boolean needsXY = ((axisSpec.controlType == AnalogControlType.CONTROL_LEFT_JOYSTICK) || 
            (axisSpec.controlType == AnalogControlType.CONTROL_RIGHT_JOYSTICK));
        
        return ((!needsXY || (needsXY && (axisSpec.axis != ControlAxis.AXIS_UNDEFINED))) && 
            (axisSpec.controlType != AnalogControlType.CONTROL_UNDEFINED) && 
            (axisSpec.userDesignation != UserDesignation.USER_UNDEFINED) && 
            (axisSpec.valueType != UserControlValueType.UCVT_UNDEFINED));
    }

    public static AxisSpecification buildAxisSpecification(String ctrl, String cfgValue)
    {
        AxisSpecification axisSpec = new AxisSpecification();
        axisSpec.controlType = AnalogControlType.CONTROL_UNDEFINED;
        axisSpec.userDesignation = UserDesignation.USER_UNDEFINED;
        axisSpec.valueType = UserControlValueType.UCVT_UNDEFINED;
        axisSpec.axis = ControlAxis.AXIS_UNDEFINED;

        // Set control type
        if (ctrl.contains(".LeftJS."))
        {
            axisSpec.controlType = AnalogControlType.CONTROL_LEFT_JOYSTICK;
        }
        else if (ctrl.contains(".RightJS."))
        {
            axisSpec.controlType = AnalogControlType.CONTROL_RIGHT_JOYSTICK;
        }
        else if (ctrl.endsWith(".LeftTrigger"))
        {
            axisSpec.controlType = AnalogControlType.CONTROL_LEFT_TRIGGER;
        }
        else if (ctrl.endsWith(".RightTrigger"))
        {
            axisSpec.controlType = AnalogControlType.CONTROL_RIGHT_TRIGGER;
        }

        // Set axis
        if (ctrl.endsWith(".Y"))
        {
            axisSpec.axis = ControlAxis.AXIS_UP_DOWN;
        }
        else if (ctrl.endsWith(".X"))
        {
            axisSpec.axis = ControlAxis.AXIS_LEFT_RIGHT;
        }

        // Set value type
        if (cfgValue.endsWith("OI.robot.speed"))
        {
            axisSpec.valueType = UserControlValueType.UCVT_ROBOT_SPEED;
        }
        else if (cfgValue.endsWith("OI.robot.rotation_rate"))
        {
            axisSpec.valueType = UserControlValueType.UCVT_ROBOT_ROTATION_RATE;
        }
        else if (cfgValue.endsWith("OI.elevator.speed")) // Example
        {
            axisSpec.valueType = UserControlValueType.UCVT_ELEVATOR_SPEED;
        }
        else if (cfgValue.endsWith("OI.mech.deploy_rate")) // Example
        {
            axisSpec.valueType = UserControlValueType.UCVT_DEPLOY_RATE;
        }
        return axisSpec;
    }

    public String toString()
    {
        String ret = "";

        if (userDesignation == UserDesignation.USER_UNDEFINED)
        {
            ret += "USER_UNDEFINED:";
        }
        else if (userDesignation == UserDesignation.USER_DRIVER)
        {
            ret += "DRIVER:";
        }
        else if (userDesignation == UserDesignation.USER_SCORER)
        {
            ret += "SCORER:";
        };

        if (controlType == AnalogControlType.CONTROL_UNDEFINED)
        {
            ret += "CONTROL_UNDEFINED:";
        }
        else if (controlType == AnalogControlType.CONTROL_LEFT_JOYSTICK)
        {
            ret += "LEFT_JOYSTICK:";
        }
        else if (controlType == AnalogControlType.CONTROL_RIGHT_JOYSTICK)
        {
            ret += "RIGHT_JOYSTICK:";
        }
        else if (controlType == AnalogControlType.CONTROL_LEFT_TRIGGER)
        {
            ret += "LEFT_TRIGGER:";
        }
        else if (controlType == AnalogControlType.CONTROL_RIGHT_TRIGGER)
        {
            ret += "RIGHT_TRIGGER:";
        }
        else if (controlType == AnalogControlType.CONTROL_DPAD)
        {
            ret += "DPAD:";
        }

        if (axis == ControlAxis.AXIS_UNDEFINED)
        {
            ret += "AXIS_UNDEFINED:";
        }
        else if (axis == ControlAxis.AXIS_UP_DOWN)
        {
            ret += "UP_DOWN:";
        }
        else if (axis == ControlAxis.AXIS_LEFT_RIGHT)
        {
            ret += "LEFT_RIGHT:";
        }
        
        if (valueType == UserControlValueType.UCVT_UNDEFINED)
        {
            ret += "UCVT_UNDEFINED";
        }
        else if (valueType == UserControlValueType.UCVT_ROBOT_SPEED)
        {
            ret += "ROBOT_SPEED";
        }
        else if (valueType == UserControlValueType.UCVT_ROBOT_ROTATION_RATE)
        {
            ret += "ROBOT_ROTATION_RATE";
        }
        else if (valueType == UserControlValueType.UCVT_ELEVATOR_SPEED)
        {
            ret += "ELEVATOR_SPEED";
        }
        else if (valueType == UserControlValueType.UCVT_DEPLOY_RATE)
        {
            ret += "DEPLOY_RATE";
        }
    
        return ret;
    }
};

