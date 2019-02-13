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
public class ControlReader1 {

    Properties driver, scorer, robot;
    String rootDeployDir, s;

    public ControlReader1(){

        driver = new Properties();
        scorer = new Properties();
        robot = new Properties();
        s = File.separator;
        rootDeployDir = Filesystem.getOperatingDirectory() + s + "src" + s + "main" + s + "deploy";
        
        String robotFileName = getRobotType();
        lookForFiles(robotFileName, robot);
        lookForFiles("driver.properties", driver);
        lookForFiles("scorer.properties", scorer);
        System.out.println("" + rootDeployDir);
    }

    /**
     * Internal Utility for seeing if a string value exists from any of the containers
     * @param str
     * @return
     */
    private boolean hasName(String str)
    {
        boolean ret = false;
        if( scorer.getProperty(str) != null)
        {
            ret = true;
        }
        else if( driver.getProperty(str) != null)
        {
            ret = true;
        }
        else if( robot.getProperty(str) != null)
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
        if( scorer.getProperty(str) != null)
        {
            ret = scorer.getProperty(str);
        }
        else if( driver.getProperty(str) != null)
        {
            ret = driver.getProperty(str);
        }
        else if( robot.getProperty(str) != null)
        {
            ret = robot.getProperty(str);
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
        System.out.println("getMappedString --->" + ret);
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

    public void test(){

        driver.list(System.out);
        scorer.list(System.out);
        robot.list(System.out);
    }

    public static String getRobotType(){
        String ret = null;
        try{
            //InetAddress inet = InetAddress.getByName("10.6.20.2");
            InetAddress inet = InetAddress.getByName("10.122.186.1");
            NetworkInterface net = NetworkInterface.getByInetAddress(inet);
            System.out.println("Net" +( net==null?"nope":net.toString())+"] ["+(inet==null?"nope":inet.toString())+"]");
            byte[] address = net.getHardwareAddress(); //MAC Address
            StringBuilder sb = new StringBuilder();
            for(byte b : address) {
                sb.append(String.format("%02X", b));
                sb.append("_");
            } 
            sb.deleteCharAt(sb.length()-1);
            String ad = sb.toString();
            SmartDashboard.putString("test", ad);
            System.out.println(ad);

            // look for file named after MAC address
            String mac = ad.strip();   
            ret = mac+".properties";
            System.out.println("" + ret);
        }
        catch(Exception e)
        {
            System.out.println("There was an error: " + e.getMessage());
        }
        System.out.println("getRobotType" + ret);
        return ret;
    }
    
    public String lookForFiles(String robo, Properties props){
        String ret = "we got nothing [" + robo + "]";
        try{    
            //checks for a USB in the RoboRIO
            String usb1 = "" + s + "media" + s + "sda1";
            props.load(new FileInputStream(new File(usb1 + s + robo)));
            SmartDashboard.putString("Files", "USB Files");
            ret = "we got the usb";
        }catch(Exception e){
            
            try{   
                //checks for driver files in the deploy directory
                props.load(new FileInputStream(new File(rootDeployDir + s + robo)));
                SmartDashboard.putString("Files", "Computer Files");
                ret = "we got the files";
            }catch(Exception f){

                try{    //uses defaults in the deploy directory
                    props.load(new FileInputStream(new File(rootDeployDir + s + robo)));
                    SmartDashboard.putString("Files", "Default Files");
                    ret = "we got the defaults";
                }catch(Exception g){
                    // do nothing
                }
            }
        }
        System.out.println("look for files" + ret);
        return ret;
    }
}
