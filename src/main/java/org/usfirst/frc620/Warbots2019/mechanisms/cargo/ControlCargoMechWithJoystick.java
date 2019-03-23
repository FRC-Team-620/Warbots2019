/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.mechanisms.cargo;

import org.usfirst.frc620.Warbots2019.automation.CargoGroundIntakeMode;
import org.usfirst.frc620.Warbots2019.automation.CargoStationIntakeMode;
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class ControlCargoMechWithJoystick extends Command 
{
  private boolean alienMouthExtended;
  private boolean hatchScraperWristExtended;
  private CargoMech cargoMech = (CargoMech) Robot.scoringMechanism;

  public ControlCargoMechWithJoystick() 
  {
    Logger.log("New command: ControlCargoMechWithJoystick");
    requires(cargoMech);
    alienMouthExtended = false;
    hatchScraperWristExtended = true;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    Joystick joystick = Robot.oi.scorerController;

    if (joystick.getRawButtonPressed(1))
      Scheduler.getInstance().add(new CargoGroundIntakeMode());
    if (joystick.getRawButtonPressed(2))
      Scheduler.getInstance().add(new CargoStationIntakeMode());
    if (joystick.getRawButtonPressed(3))
      Scheduler.getInstance().add(new GrabberEjectCommand());

    if (joystick.getRawAxis(2) > 0.7 ||
        (joystick.getRawAxis(5) < -0.7))
      cargoMech.captureCargo();
    else if (joystick.getRawAxis(3) > 0.7 ||
        (joystick.getRawAxis(5) > 0.7 && !cargoMech.hasCargo()))
      cargoMech.ejectCargo();
    else
      cargoMech.stopCapture();

    if (joystick.getRawButtonPressed(10))
      alienMouthExtended = !alienMouthExtended;

    if (joystick.getPOV() == 0)
      hatchScraperWristExtended = false;
    else if (joystick.getPOV() == 180)
      hatchScraperWristExtended = true;

    cargoMech.setAlienMouth(alienMouthExtended);
    cargoMech.setHatchScraperWrist(hatchScraperWristExtended);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  public void initSendable(SendableBuilder builder) 
  {
    super.initSendable(builder);
    builder.addBooleanProperty("alienMouthExtended", () -> alienMouthExtended, null);
    builder.addBooleanProperty("hatchScraperWristExtended", () -> hatchScraperWristExtended, null);
  }
}
