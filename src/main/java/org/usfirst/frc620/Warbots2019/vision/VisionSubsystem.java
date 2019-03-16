/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.ControlReader;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;



/**
 * Add your docs here.
 */

public class VisionSubsystem extends Subsystem 
{
  public VisionSubsystem(ControlReader config)
  {
    int numberOfCameras = config.getMappedInt("NumberOfCameras");
    int[] cameraStuff;
    for (int i = 0; i < numberOfCameras; ++i)
    {
        var cam = CameraServer.getInstance().startAutomaticCapture(i);
        System.out.println("Setting resolution: " + cam.setResolution(50, 30));
    }
  
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
