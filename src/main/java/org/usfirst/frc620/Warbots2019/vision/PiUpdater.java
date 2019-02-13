/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

// Don't assume everyone has JUNit installed!
//import static org.junit.Assume.assumeTrue;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class PiUpdater 
{
    public PiUpdater()
    {
        Azimuth = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("azimuth");
        Elevation = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("elevation");
        WantToTrack = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("wantToTrack");
        update();
    }
    private void update()
    {
        while (true)
        {
            try
            {
                Thread.sleep(10);
            }
            catch(Exception e)
            {
                
            }
            if (!VisionInformationTransferClass.getWantToTrack())
            {
                //update will's system per his requirements
                continue;
            }
            Azimuth.setDouble(VisionInformationTransferClass.getAzimuth());
            Elevation.setDouble(VisionInformationTransferClass.getElevation());
            WantToTrack.setBoolean(VisionInformationTransferClass.getWantToTrack());
        }
    }
    NetworkTableEntry Azimuth;
    NetworkTableEntry Elevation;
    NetworkTableEntry WantToTrack;
}
