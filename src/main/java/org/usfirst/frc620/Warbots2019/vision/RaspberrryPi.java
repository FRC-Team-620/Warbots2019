/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
//Use this, giving errors
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
        trackerSteeringSubtable.getEntry("horizontalServoIn");
        trackerSteeringSubtable.getEntry("veticalServoIn");
        trackerSteeringSubtable.getEntry("horizontalServoOut");
        trackerSteeringSubtable.getEntry("veticalServoOut");

        trackerOutputSubtable = raspberryPiCommunications.getSubTable("trackerOutputSubtable");
        trackerOutputSubtable.getEntry("lidarDistance");
        trackerOutputSubtable.getEntry("azimuth"); //what's the other thing? lineOfSightToWallAngle?
        trackerOutputSubtable.getEntry("isTargetVisible");

        Thread piUpdater = new Thread(new PiUpdater());
        piUpdater.start();
    }

    public void updateBlingLights (BlingLightColorEnum color) // how to get whether robot is enabled?
    {
        if (color == BlingLightColorEnum.red) blingLightsCommunicationsSubtable.getEntry("color").setString("red");
        else blingLightsCommunicationsSubtable.getEntry("color").setString("blue");
    }

    public void SteerTracker (Angle azimuth, Angle elevation)
    {
        VisionInformationTransferClass.SteerTracker(azimuth, elevation);
    }

    public void setWantToTrack (boolean wantToTrack)
    {
        VisionInformationTransferClass.setWantToTrack(wantToTrack);
    }

    public double getLidarDistance() throws Exception
    {
        double dist = trackerOutputSubtable.getEntry("lidarDistance").getDouble(-5.0);
        if (dist == -5.0)
        {
            throw new Exception("Network table entry lidarDistance not found");
        }
        return dist;
    }

    public Angle getAzimuth() throws Exception
    {
        double az = trackerOutputSubtable.getEntry("azimuth").getDouble(-5.0);
        if (az == -5.0)
        {
            throw new Exception("Network table entry azimuth not found");
        }
        return Angle.fromDegrees(az);    
    }

    public boolean getIsTargetVisible ()
    {
        boolean isVisible = trackerOutputSubtable.getEntry("isTargetVisible").getBoolean(false); //Should I assume default to be false or true? How do I tell if not found entry from invisble target?
        return isVisible;
    }

    private NetworkTableInstance inst;
    private NetworkTable raspberryPiCommunications;
    private NetworkTable blingLightsCommunicationsSubtable;
    private NetworkTable trackerSteeringSubtable;
    private NetworkTable trackerOutputSubtable;

    public enum BlingLightColorEnum
    {
        red, blue
    }
}
