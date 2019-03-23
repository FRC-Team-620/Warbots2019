/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc620.Warbots2019.mechanisms.ScoringMechanism;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Configurable;
import org.usfirst.frc620.Warbots2019.utility.Configurable.Element;
import org.usfirst.frc620.Warbots2019.utility.ConfigurableImpl;
import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;
import org.usfirst.frc620.Warbots2019.utility.SendableTalonWrapper;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */
public class CargoMech extends ScoringMechanism 
{
  public boolean configurable = true;
  private WPI_TalonSRX intakeWheels;
  private DigitalInput limitSwitch;
  private Solenoid wristPiston1;
  private Solenoid wristPiston2;
  private Servo alienMouth;
  private Servo hatchScraperWrist;

  public CargoMech()
  {
    Logger.log("New Command: "+this.getName());
    // populate Configurable
  }
  // for TestBot
  public CargoMech(int intakeWheelsCanID, 
      int PCMCanID,
      int limitSwitchPort, 
      int wristPistonChannel1, 
      int wristPistonChannel2,
      int alientMouthPort,
      int hatchScraperWristPort) 
  {
    this();
    intakeWheels = new WPI_TalonSRX(intakeWheelsCanID);

    ControlReader config = Robot.config;
    cmspeed = config.getMappedDouble("CargoMech.MotorSpeed");
    if (cmspeed < 0)
      cmspeed = 1;

    addChild(new SendableTalonWrapper(intakeWheels));

    wristPiston1 = new Solenoid(PCMCanID, wristPistonChannel1);
    wristPiston2 = new Solenoid(PCMCanID, wristPistonChannel2);

    alienMouth = new Servo(alientMouthPort);
    hatchScraperWrist = new Servo(hatchScraperWristPort);

    limitSwitch = new DigitalInput(limitSwitchPort);
  }

  @Override
  public void initDefaultCommand() 
  {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    System.out.print("CargoMech is working");

    Command defaultCommand = new ControlCargoMechWithJoystick();
    setDefaultCommand(defaultCommand);

    addChild(defaultCommand);
    addChild(new GrabberDeployCommand());
    addChild(new GrabberStowCommand());
    addChild(new GrabberCaptureCommand());
    addChild(new GrabberEjectCommand());
  }

  ControlReader config = Robot.config;
  double cmspeed = config.getMappedDouble("CargoMech.MotorSpeed");

  public static Configurable asConfigurable()
  {
      // We now have a Configurable object with all methods implemented
      // so programs can carry it around like a suitcase
      ConfigurableImpl configurable = (ConfigurableImpl)ScoringMechanism.asConfigurable();
      configurable.addElement(new Element("CargoMech.MotorSpeed", 
          "CargoMech Setting: Max speed of cargo mech wheels 0-1.0", null));
      return configurable;
  }

  public void idle()
  {
    stow();
  }

  public boolean hasCargo()
  {
    return limitSwitch.get();
  }

  public void captureCargo() 
  {
    intakeWheels.set(-cmspeed);
    // double intakeVoltage = intakeWheels.getMotorOutputVoltage();
    // double intakeCurrent = intakeWheels.getOutputCurrent();
    // double intakeResistance = intakeVoltage / intakeCurrent;
    // System.out.println("IntakeResistance is" + intakeResistance);
    // System.out.println("IntakeCurrent is" +intakeCurrent);
    // System.out.println("IntakeVoltage is" + intakeVoltage);
  }

  public void stopCapture() {
    intakeWheels.set(0);
    // double intakeVoltage = intakeWheels.getMotorOutputVoltage();
    // double intakeCurrent = intakeWheels.getOutputCurrent();
    // double intakeResistance = intakeVoltage / intakeCurrent;
    // System.out.println("IntakeResistance is" + intakeResistance);
    // System.out.println("IntakeCurrent is" +intakeCurrent);
    // System.out.println("IntakeVoltage is" + intakeVoltage);
  }

  public void ejectCargo() {
    intakeWheels.set(cmspeed);
    // double intakeVoltage = intakeWheels.getMotorOutputVoltage();
    // double intakeCurrent = intakeWheels.getOutputCurrent();
    // double intakeResistance = intakeVoltage / intakeCurrent;
    // System.out.println("IntakeResistance is" + intakeResistance);
    // System.out.println("IntakeCurrent is" +intakeCurrent);
    // System.out.println("IntakeVoltage is" + intakeVoltage);
  }

  public void pullForward()
  {
    System.out.println("Pulling forward");
    intakeWheels.set(.5);
  }

  public void deploy() {
    wristPiston1.set(true);
    wristPiston2.set(true);
  }

  public void stow() {
    wristPiston1.set(false);
    wristPiston2.set(false);
  }

  @Override
  public void stop() {
  }

  @Override
  public boolean isDeployed() {
    return wristPiston1.get();
  }

  @Override
  public boolean isStowed() {
    return !isDeployed();
  }

  public void setHatchScraperWrist(boolean extended) 
  {
    hatchScraperWrist.set(extended? 1 : 0);
  }

  public void setAlienMouth(boolean extended)
  {
    alienMouth.set(extended? 1 : 0);
  }

  @Override
  public void initSendable(SendableBuilder builder) 
  {
    // builder.addBooleanProperty("Deployed", this::isDeployed, null);
    // builder.addBooleanProperty("Stowed", this::isStowed, null);
    // builder.addBooleanProperty("Wrist1", wristPiston1::get, null);
    // builder.addBooleanProperty("Wrist2", wristPiston2::get, null);
    // builder.addBooleanProperty("Latch1", latch1::get, null);
    // builder.addBooleanProperty("Latch2", latch2::get, null);
    // builder.addBooleanProperty("HasCargo", this::hasCargo, null);
  }
}