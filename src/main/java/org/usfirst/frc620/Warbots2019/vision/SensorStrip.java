/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.vision;

/**
 * Add your docs here.
 */
public class SensorStrip 
{
    public SensorStrip (int numberOfSensors)
    {
        sensors = new LightSensor[numberOfSensors];
        for (int i = 0; i < numberOfSensors; i++)
        {
            sensors[i] = new LightSensor();
        }
    }
    public void updateLinePositionPct () throws Exception
    {
        boolean firstSequenceStart = false;
        int count = 0;
        int score = 0;
        double endScore = 0;
        for (int i = 0; i < sensors.length; i++)
        {
            if (sensors[i].isOnLine())
            {
                if (i >= 2 && firstSequenceStart == true && sensors[i - 1].isOnLine() == false) throw new Exception("2+ lines in sight");

                firstSequenceStart = true;
                score += i;
                count++;
            }
            endScore = i - 1;
        }
        if (count == 0) throw new Exception("No line in sight");

        endScore = (double) score / count;
        endScore /= sensors.length;
        linePositionPct = endScore;
    }
    public double getLinePositionPct ()
    {
        return linePositionPct;
    }

    private LightSensor[] sensors;
    private double linePositionPct;
}
