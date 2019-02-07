/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;


import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc620.Warbots2019.utility.Angle;

public class TurnAngle extends Command {
  private double speed;
  private double angle;
  private double init_rotation;
  private double final_position;

  public TurnAngle(Angle angle, double speed) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.angle = angle.toDegrees();
    requires(Robot.driveTrain);
    this.speed = speed;
  }
   
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    init_rotation = Robot.driveTrain.getAngle().toDegrees();
    final_position = init_rotation + angle;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   if (angle < 0){
      speed = speed * -1;
    }
    Robot.driveTrain.drive(0, speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean ret = Math.abs(Robot.driveTrain.getAngle().toDegrees() - final_position) <= 5;
    if (ret)
      Robot.driveTrain.drive(0, 0);
    return ret;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
