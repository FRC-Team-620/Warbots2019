/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

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
        HorizontalServoIn = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("horizontalServoIn");
        VerticalServoIn = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("veticalServoIn");
        HorizontalServoOut = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("horizontalServoOut");
        VerticalServoOut = NetworkTableInstance.getDefault().getTable("raspberryPiCommuncationsTable").getSubTable("trackerSteeringSubtable").getEntry("veticalServoOut");

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
            VerticalServoOut.setDouble(VerticalServoIn.getDouble(0));
            HorizontalServoOut.setDouble(HorizontalServoIn.getDouble(0));
            
            if (VisionInformationTransferClass.getWantToTrack() != OldWantToTrack) 
            {
                WantToTrack.setBoolean(VisionInformationTransferClass.getWantToTrack());
                OldWantToTrack = VisionInformationTransferClass.getWantToTrack();
            }

            if (!VisionInformationTransferClass.getWantToTrack())
            {
                Azimuth.setDouble(VisionInformationTransferClass.getAzimuth());
                Elevation.setDouble(VisionInformationTransferClass.getElevation());
            }
        }
    }
    boolean OldWantToTrack;
    NetworkTableEntry Azimuth;
    NetworkTableEntry Elevation;
    NetworkTableEntry WantToTrack;
    NetworkTableEntry HorizontalServoIn;
    NetworkTableEntry VerticalServoIn;
    NetworkTableEntry HorizontalServoOut;
    NetworkTableEntry VerticalServoOut;
}
