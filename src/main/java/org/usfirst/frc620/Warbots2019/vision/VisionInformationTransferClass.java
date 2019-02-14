/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

import org.usfirst.frc620.Warbots2019.utility.Angle;

/**
 * Add your docs here.
 */
class VisionInformationTransferClass 
{
    public synchronized static void SteerTracker (Angle azimuth, Angle elevation)
    {
        Boolean wantToTrackTemp = WantToTrack;
        WantToTrack = false;
        Azimuth = azimuth.toDegrees();
        Elevation = elevation.toDegrees();
        WantToTrack = wantToTrackTemp;
    }

    public synchronized static void setWantToTrack (boolean wantToTrack)
    {
        WantToTrack = wantToTrack;
    }

    public synchronized static boolean getWantToTrack ()
    {
        return WantToTrack;
    }

    public synchronized static double getAzimuth ()
    {
        return Azimuth;
    }

    public synchronized static double getElevation ()
    {
        return Elevation;
    }

    private static boolean WantToTrack;
    private static double Azimuth;
    private static double Elevation; 
}
