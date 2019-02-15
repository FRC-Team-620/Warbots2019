/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */

public class StateManager extends SendableBase
{
    private static StateManager instance;
    private HashMap<StateKey, Double> hashmap;

    //Enumeration
    public enum StateKey{
        X_POSITION, Y_POSITION, ELEVATOR_HEIGHT, FIELD_ANGLE, 
        COMMANDED_DRIVEDISTANCE,    //distance for drivestraight command
        COMMANDED_TURNANGLE         //angle for turnAngle
    }
    //Singleton design pattern
    //synchronized makes it work when different things try to access it at the same time
    public static synchronized StateManager getInstance()
    {
        if (instance == null)
            instance = new StateManager();
        
        return instance;
    }
    //gets a double value from the speific enum key value i.e. X_POSITION, FIELD_ANGLE, etc
    public double getDoubleValue(StateKey key){
        return hashmap.get(key).doubleValue();
    }
    //sets a double value into a specific enum 
    public void setDoubleValue(StateKey key, double val){
        hashmap.put(key, val);
    }
    //Add stuff to the network tables
    @Override
    public void initSendable(SendableBuilder builder) {
    }
    
    private StateManager(){
        hashmap = new HashMap<StateKey, Double>();
    }
}
