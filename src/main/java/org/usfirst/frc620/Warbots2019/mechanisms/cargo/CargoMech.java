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
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class CargoMech extends ScoringMechanism {

  public boolean configurable = true;
  private WPI_TalonSRX intakeWheels;
  private DigitalInput limitSwitch;
  private Solenoid wristPiston1;
  private Solenoid wristPiston2;
  private DigitalOutput latch1;
  private DigitalOutput latch2;

  public CargoMech()
  {
    Logger.log("New Command: "+this.getName());
    // populate Configurable
  }
  // for TestBot
  public CargoMech(int intakeWheelsCanID, 
      int PCMCanID, 
      int wristPistonChannel1, 
      int wristPistonChannel2,
      int latchPort1,
      int latchPort2) 
  {
    this();
    intakeWheels = new WPI_TalonSRX(intakeWheelsCanID);

    ControlReader config = Robot.config;
    cmspeed = config.getMappedDouble("CargoMech.MotorSpeed");
    if (cmspeed < 0)
      cmspeed = 1;

    addChild(new SendableTalonWrapper(intakeWheels));

    wristPiston1 = new Solenoid(wristPistonChannel1);
    wristPiston2 = new Solenoid(wristPistonChannel2);

    latch1 = new DigitalOutput(latchPort1);
    latch2 = new DigitalOutput(latchPort2);
  }

  @Override
  public void initDefaultCommand() 
  {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    System.out.print("CargoMech is working");
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
    double intakeVoltage = intakeWheels.getMotorOutputVoltage();
    double intakeCurrent = intakeWheels.getOutputCurrent();
    double intakeResistance = intakeVoltage / intakeCurrent;
    // System.out.println("IntakeResistance is" + intakeResistance);
    // System.out.println("IntakeCurrent is" +intakeCurrent);
    // System.out.println("IntakeVoltage is" + intakeVoltage);
  }

  public void stopCapture() {
    intakeWheels.set(0);
    double intakeVoltage = intakeWheels.getMotorOutputVoltage();
    double intakeCurrent = intakeWheels.getOutputCurrent();
    double intakeResistance = intakeVoltage / intakeCurrent;
    // System.out.println("IntakeResistance is" + intakeResistance);
    // System.out.println("IntakeCurrent is" +intakeCurrent);
    // System.out.println("IntakeVoltage is" + intakeVoltage);
  }

  public void ejectCargo() {
    intakeWheels.set(cmspeed);
    double intakeVoltage = intakeWheels.getMotorOutputVoltage();
    double intakeCurrent = intakeWheels.getOutputCurrent();
    double intakeResistance = intakeVoltage / intakeCurrent;
    // System.out.println("IntakeResistance is" + intakeResistance);
    // System.out.println("IntakeCurrent is" +intakeCurrent);
    // System.out.println("IntakeVoltage is" + intakeVoltage);
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

  public void openLatch()
  {
    latch1.set(true);
    latch2.set(true);
  }

  public void closeLatch()
  {
    latch1.set(false);
    latch2.set(false);
  }
}