/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.ControlReader;

import edu.wpi.cscore.UsbCamera;
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
    UsbCamera[] usbCameras = new UsbCamera[numberOfCameras];
    for (int i = 0; i < numberOfCameras; i++)
    {
        usbCameras[i] = CameraServer.getInstance().startAutomaticCapture(i);
        System.out.println("Setting resolution: " + usbCameras[i].setResolution(50, 30));
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
