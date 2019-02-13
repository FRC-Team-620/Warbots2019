/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.io.File;
import java.io.FileInputStream;
import java.net.*;
import java.util.Properties;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class ControlReader {

    Properties prop;
    String rootDeployDir, rootUSBDir, s;

    public ControlReader(){

        prop = new Properties();
        s = File.separator;
        rootDeployDir = "" + Filesystem.getDeployDirectory();
        
        
        String robotFileName = getRobotType();
        lookForFiles(robotFileName);
        lookForFiles("driver.properties");
        lookForFiles("scorer.properties");
    }

    /**
     * Internal Utility for seeing if a string value exists from any of the containers
     * @param str
     * @return
     */
    private boolean hasName(String str)
    {
        boolean ret = false;
        if( prop.getProperty(str) != null)
        {
            ret = true;
        }
        return ret;
    }
    
    /**
     * Internal Utility for getting a string value from any of the containers
     * @param str
     * @return
     */
    private String getNamedValue(String str)
    {
        String ret = null;
        if( prop.getProperty(str) != null)
        {
            ret = prop.getProperty(str);
        }
        if(hasName("")){
        System.out.print(hasName(""));    
        }
        return ret;
    }

    //returns int from files, -1 if invalid
    public int getMappedInt(String string){
        int i;
        String v = getNamedValue(string);
        try
        {
            i = Integer.parseInt(v);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    //returns String from files, 'invalid file' if invalid
    public String getMappedString(String string){
        String ret = getNamedValue(string);
        return ret;
    }

    //returns String from variables (no boolean field)

    //returns double from files, -1.0 if invalid
    public double getMappedDouble(String string){
        double d;
        String v = getNamedValue(string);
        try
        {
            d = Double.parseDouble(v);
        } catch (Exception e) {
            d = -1.0;
        }
        return d;
    }

    public void getProperties(){

        prop.list(System.out);
    }

    public String getRobotType(){
        String ret = null;
        try{
            System.out.println("it prints from the method");
            NetworkInterface net = NetworkInterface.getByInetAddress(InetAddress.getByName("roboRIO-620-FRC"));
            
            byte[] address = net.getHardwareAddress(); //MAC Address
            StringBuilder sb = new StringBuilder();
            for(byte b : address) {
                sb.append(String.format("%02X", b));
                sb.append("_");
            } 
            sb.deleteCharAt(sb.length()-1);
            String ad = sb.toString();

            // look for file named after MAC address
            String mac = ad.strip(); 
            ret = mac +".properties";
            SmartDashboard.putString("test", ret);
            
        }
        catch(Exception e)
        {
            System.out.println("There was an error: " + e.getMessage());
        }
        return ret;
    }
    
    public String lookForFiles(String filename){
        String ret = "we got nothing";
        System.out.println("to look for file: ["+filename+"]");
        try{    
            //checks for a USB in the RoboRIO
            rootUSBDir = s + "media" + s + "sda1";
            prop.load(new FileInputStream(new File(rootUSBDir + s + filename)));
            SmartDashboard.putString("Files", "USB 1 Files");
            System.out.println ("found ["+rootUSBDir + s + filename+"]");
        }catch(Exception e){
            
            try{    
                //checks for a USB in the RoboRIO
                rootUSBDir = s + "media" + s + "sda2";
                prop.load(new FileInputStream(new File(rootUSBDir + s + filename)));
                SmartDashboard.putString("Files", "USB 2 Files");
                System.out.println ("found ["+rootUSBDir + s + filename+"]");
            }catch(Exception e2){
                
                try{   
                    //checks for driver files in the deploy directory
                    prop.load(new FileInputStream(new File(rootDeployDir + s + filename)));
                    SmartDashboard.putString("Files", "Computer Files");
                    System.out.println ("found ["+rootDeployDir + s + filename+"]");
                }catch(Exception f){
    
                    System.err.println("unable to find file: ["+filename+"]");
                }
            }
            
        }
        return ret;
    }
}
