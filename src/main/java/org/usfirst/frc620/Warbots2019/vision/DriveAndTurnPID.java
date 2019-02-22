/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.Angle;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc620.Warbots2019.drivetrain.TurnAnglePID;
import org.usfirst.frc620.Warbots2019.drivetrain.DriveDistance;
import org.usfirst.frc620.Warbots2019.robot.StateManager;
import org.usfirst.frc620.Warbots2019.robot.StateManager.StateKey;



public class DriveAndTurnPID extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveAndTurnPID(Angle a, double d) 
  {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    StateManager.getInstance().setDoubleValue(StateKey.COMMANDED_DRIVEDISTANCE, d);
    addParallel(new TurnAnglePID(a));
    addParallel(new DriveDistance());
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
