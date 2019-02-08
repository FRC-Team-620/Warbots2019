/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.automation;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * Add your docs here.
 */
public class RaspberrryPi 
{
    public RaspberrryPi ()
    {
        inst = NetworkTableInstance.getDefault();
        raspberryPiCommunications = inst.getTable("raspberryPiCommuncationsTable");

        blingLightsCommunicationsSubtable = raspberryPiCommunications.getSubTable("blingLightsCommuniationsSubtable");
        blingLightsCommunicationsSubtable.getEntry("color");
        blingLightsCommunicationsSubtable.getEntry("isEnabled");

        trackerSteeringSubtable = raspberryPiCommunications.getSubTable("trackerSteeringSubtable");
        trackerSteeringSubtable.getEntry("azimuth");
        trackerSteeringSubtable.getEntry("elevation");
        trackerSteeringSubtable.getEntry("wantToTrack");

        trackerOutputSubtable = raspberryPiCommunications.getSubTable("trackerOutputSubtable");
        trackerOutputSubtable.getEntry("lidarDistance");
        trackerOutputSubtable.getEntry("azimuth"); //what's the other thing?
        trackerOutputSubtable.getEntry("isTargetVisible");
    }

    public void SteerTracker (double azimuth, double elevation) //units
    {

    }

    public void setWantToTrack (boolean wantToTrack)
    {

    }

    public void updateServos ()
    {

    }

    public double getLidarDistance() //units
    {
        return 0.0;
    }

    public double getAzimuth() //units
    {
        return 0.0;
    }

    public boolean getIsTargetVisible ()
    {
        return false;
    }

    private NetworkTableInstance inst;
    private NetworkTable raspberryPiCommunications;
    private NetworkTable blingLightsCommunicationsSubtable;
    private NetworkTable trackerSteeringSubtable;
    private NetworkTable trackerOutputSubtable;
}
