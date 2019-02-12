/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class CargoMech extends Subsystem {

  private SpeedController intakeWheels;
  private DigitalInput limitSwitch;
  private CargoMech cargoMech;

  public CargoMech(int mainMotorPort, int limitSwitchPort) {

    SpeedController mainWheels = new Spark(mainMotorPort);

    limitSwitch = new DigitalInput(limitSwitchPort);
    intakeWheels = new SpeedControllerGroup(mainWheels);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new CaptureCargo());
    System.out.print("Working");
  }

  public void idle() {
    cargoMech.stowMech();
  }

  public boolean hasCargo() {
    return !limitSwitch.get();
  }

  public void captureCargo() {
    GenericHID joystick = Robot.oi.getDriverController();
    double speed = joystick.getRawAxis(5);
    System.out.print(speed);
    intakeWheels.set(speed);
  }

  public void stopCapture() {
    intakeWheels.set(0);
  }

  public void ejectCargo() {
    intakeWheels.set(-1);
  }

  public void deployMech() {
    // mainPiston.set(true);
  }

  public void stowMech() {
    // mainPiston.set(false);
  }
}
