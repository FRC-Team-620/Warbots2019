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
    public static void SteerTracker (Angle azimuth, Angle elevation)
    {
        Boolean wantToTrackTemp = WantToTrack;
        WantToTrack = false;
        Azimuth = azimuth.toDegrees();
        Elevation = elevation.toDegrees();
        WantToTrack = wantToTrackTemp;
    }

    public static void setWantToTrack (boolean wantToTrack)
    {
        WantToTrack = wantToTrack;
    }

    static boolean WantToTrack;
    static double Azimuth;
    static double Elevation; 
}
