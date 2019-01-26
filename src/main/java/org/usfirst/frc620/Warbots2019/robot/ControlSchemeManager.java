/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.robot;

/**
 * Reads in the control.properties resource file using FileUtilities.getFilePath
 * to get a path to files in the src/main/deploy directory
 * 
 * Registers commands for joystick input using joystick buttons
 * 
 * @see https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599739-running-commands-on-joystick-input
 */

/*
This stops a resource leak warning from coming up when using JoystickButton. The warning is meaningless;
it's caused by JoystickButton implementing an interface that it shouldn't actually implement.
*/
@SuppressWarnings("resource")
public class ControlSchemeManager {
}
