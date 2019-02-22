/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class CargoMech extends ScoringMechanism {

  private WPI_TalonSRX intakeWheels;
  private DigitalInput limitSwitch;
  private Solenoid mainPiston;
  // for TestBot
  public CargoMech(int intakeWheelsCanID, int PCMCanID, int wristPistonChannel) {
    
    intakeWheels = new WPI_TalonSRX(intakeWheelsCanID);

    ControlReader config = Robot.config;
    cmspeed = config.getMappedDouble("CargoMechMotorSpeed");
    if (cmspeed < 0)
      cmspeed = 1;

    //wrist = new Solenoid(PCMCanID, wristPistonChannel);
    
 
    //wrist = new Solenoid(PCMCanID, wristPistonChannel)
  }

  //for this years robot
  
/*
    SpeedController mainWheels = new Spark(mainMotorPort);
    
    limitSwitch = new DigitalInput(limitSwitchPort);
    //Uses the port for the wheels to instansiate the mainWheels
    intakeWheels = new SpeedControllerGroup(mainWheels);
    //I don't know if we're using a piston or motor to deploy and stow CargoMech
    mainPiston = new Solenoid(PCMCanID, mainPistonPort);
    */
  
  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    System.out.print("Working");
  }

  ControlReader config = Robot.config;
  double cmspeed = config.getMappedDouble("CargoMechMotorSpeed");
  public void idle() {
    stow();
  }

  public boolean hasCargo() {
    return limitSwitch.get();
  }

  public void captureCargo() {
  intakeWheels.set(-cmspeed);
  }

  public void stopCapture() {
  intakeWheels.set(0);
  }

  public void ejectCargo() {
    intakeWheels.set(cmspeed);
  }

  public void deploy() {
    mainPiston.set(true);
  }

  public void stow() {
    mainPiston.set(false);
  }

  @Override
  public void stop() {
  }

  @Override
  public boolean isDeployed() {
    return true;
  }

  @Override
  public boolean isStowed() {
    return false;
  }
}