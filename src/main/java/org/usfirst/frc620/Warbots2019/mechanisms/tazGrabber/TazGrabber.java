/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.tazGrabber;

import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class TazGrabber extends ScoringMechanism {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  //DigitalInput reads a true or false value from any sensor connected to the DIO port
  private DigitalInput limitSwitch;

  //SpeedController is the superclass for anything that can be used like a motor
  private SpeedController intakeWheels;

  //Solenoid's are the things that control pistons. Pistons can either extend or retract
  //(think Minecraft pistons) by pumping air in and out of a tube. There are two pistons
  //on each side of the grabber.
  private Solenoid leftFrontPiston;
  private Solenoid rightFrontPiston;
  private Solenoid leftBackPiston;
  private Solenoid rightBackPiston;

  //The wrist piston retracts the whole grabber into a stowed position or extends it into
  //a deployed position. Pay attention to this one, as the bot this year will use it as well.
  private Solenoid wristPiston;

  //Constructor accepts port numbers for all of the different hardware
  public TazGrabber(int leftIntakeWheelsPort, int rightIntakeWheelsPort, int limitSwitchPort,
      int PCMCanID, int leftFrontPistonChannel, int rightFrontPistonChannel, 
      int leftBackPistonChannel, int rightBackPistonChannel, int wristPistonChannel)
  {
    
    //Left and right wheels are SpeedControllers because we only need to know they can be used like
    //a motor, however, we use Spark on the right to specify which specific type of motor they are.
    SpeedController leftWheels = new Spark(leftIntakeWheelsPort);
    SpeedController rightWheels = new Spark(rightIntakeWheelsPort);
    
    //A SpeedControllerGroup is a special kind of SpeedController that controls two other speed
    //controllers together as if they were one. For instance, if you tell a SpeedControllerGroup
    //to spin forward at speed 0.5, it will then tell the two other speed controllers in it
    //to spin forward at speed 0.5.
    intakeWheels = new SpeedControllerGroup(leftWheels, rightWheels);
    
    limitSwitch = new DigitalInput(limitSwitchPort);

    //Solenoid constructs take a Can ID for the Pneumatics Control Module and a port for the
    //particular piston.
    leftFrontPiston = new Solenoid(PCMCanID, leftFrontPistonChannel);
    rightFrontPiston = new Solenoid(PCMCanID, rightFrontPistonChannel);
    leftBackPiston = new Solenoid(PCMCanID, leftBackPistonChannel);
    rightBackPiston = new Solenoid(PCMCanID, rightBackPistonChannel);

    wristPiston = new Solenoid(PCMCanID, wristPistonChannel);

    //The grabber should start in the closed position.
    close();
    
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  /**
   * This method detects whether there is currently a game object loaded into
   * the Taz grabber.
   * 
   * @return true if there is an object in the grabber, false if not
   */
  public boolean hasGameObject()
  {
    //Limit swith returns true if unpressed, false if pressed.
    //We want this method to have the opposite behavior. It should return true
    //if there is a game object pressing the limit switch and false otherwise.
    //To correct the issue, we have to include a not (!).
    return !limitSwitch.get();
  }

  /**
   * Enables the grabber to begin intaking game objects.
   */
  public void startIntake()
  {
    intakeWheels.set(-1);
  }

  /**
   * Begins ejecting the current game object from the grabber.
   */
  public void startEjection()
  {
    intakeWheels.set(1);
  }

  /**
   * Stops any intake or ejection.
   */
  public void stop()
  {
    intakeWheels.set(0);
  }

  /**
   * Opens the grabber arms
   */
  public void open()
  {
    //We only need to retract two of the pistons to open the grabber
    leftFrontPiston.set(true);
    rightFrontPiston.set(true);
  }

  /**
   * Closes the grabber arms around a game object
   */
  public void close()
  {
    //Extending pistons that are already extended doesn't do anything.
    //We extend every piston here instead of just the ones we use in 
    //open() to make our lives a little easier if we ever want to change
    //which pistons we use.
    leftFrontPiston.set(false);
    rightFrontPiston.set(false);
    leftBackPiston.set(false);
    rightBackPiston.set(false);
  }

  /**
   * Retracts the piston into the stowed position
   */
  public void stow()
  {
    wristPiston.set(false);
  }

  /**
   * Extends the piston into the deployed position
   */
  public void deploy()
  {
    wristPiston.set(true);
  }

  @Override
  public boolean isDeployed() {
    return wristPiston.get();
  }

  @Override
  public boolean isStowed() {
    return !wristPiston.get();
  }
}
