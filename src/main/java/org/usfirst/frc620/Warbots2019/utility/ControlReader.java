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

    static Properties drv, scr, var, robot;
    static String root, s;
    static String taz, pro, test, comp, driver, scorer, variables;

    public ControlReader(){

        //creates Properties objects and strings for file references
        taz = "taz.properties";
        pro = "prototype.properties";
        test = "testbot.properties";
        comp = "competition.properties";
        driver = "driver.properties";
        scorer = "scorer.properties";
        variables = "variables.proerties";

        drv = new Properties();
        scr = new Properties();
        var = new Properties();
        robot = new Properties();
        s = File.separator;
        root = Filesystem.getOperatingDirectory() + s + "src" + s + "main" + s + "deploy";
        
        getRobotType();

    }

    //returns int from files, -1 if invalid
    public static int getMappedInt(String string, String file){
        int i;
        if(file.equals("driver")){
            i = Integer.parseInt(drv.getProperty(string));
        }else if(file.equals("scorer")){
            i = Integer.parseInt(scr.getProperty(string));
        }else if(file.equals("robot")){
            i = Integer.parseInt(robot.getProperty(string));
        }else{
            i = -1;
        }
        return i;
    }

    //returns String from files, 'invalid file' if invalid
    public static String getMappedString(String string, String file){
        String s;
        if(file.equals("driver")){
            s = drv.getProperty(string);
        }else if(file.equals("scorer")){
            s = scr.getProperty(string);
        }else if(file.equals("robot")){
            s = robot.getProperty(string);
        }else{
            s = "invalid file";
        }
        return s;
    }

    //returns String from variables (no boolean field)

    //returns double from files, -1.0 if invalid
    public static double getMappedDouble(String string, String file){
        double d;
        if(file.equals("driver")){
            d = Double.parseDouble(drv.getProperty(string));
        }else if(file.equals("scorer")){
            d = Double.parseDouble(scr.getProperty(string));
        }else if(file.equals("robot")){
            d = Double.parseDouble(robot.getProperty(string));
        }else{
            d = -1.0;
        }
        return d;
    }

    public static void test(){
        drv.list(System.out);
        scr.list(System.out);
        var.list(System.out);
    }

    public static void getRobotType(){
        try{
            NetworkInterface net = NetworkInterface.getByInetAddress(InetAddress.getByName("10.6.20.2"));
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
        if(ad.equals("00:80:2F:19:7A:06")){//Testbot
            lookForFiles("testbot");
        }else if(ad.equals("00:80:2F:27:20:88 ")){//Taz
            lookForFiles("taz");
        }else if(ad.equals("name3")){//Prototype
            lookForFiles("prototype");
        }else if(ad.equals("name4")){//Competition
            lookForFiles("competition");
        }}catch(Exception e){
            System.out.println("There was an error: " + e.getMessage());
        }
    }
    
    public static String lookForFiles(String robo){
        String selectedrobot = "";  //changes file directory to the robot being used
        if(robo.equals("taz")){
            selectedrobot = taz;
        }else if(robo.equals("prototype")){
            selectedrobot = pro;
        }else if(robo.equals("testbot")){
            selectedrobot = test;
        }else if(robo.equals("competition")){
            selectedrobot = comp;
        }else{
            selectedrobot = test;
        }

        try{    
            //checks for a USB in the RoboRIO
            String usb1 = "" + s + "media" + s + "sda1";
            drv.load(new FileInputStream(new File(usb1 + s + driver)));
            scr.load(new FileInputStream(new File(usb1 + s + scorer)));
            robot.load(new FileInputStream(new File(root + s + selectedrobot)));
            SmartDashboard.putString("Files", "USB Files");
            return "we got the usb";
        }catch(Exception e){
            
            try{   
                //checks for driver files in the deploy directory
                drv.load(new FileInputStream(new File(root + s + driver)));
                scr.load(new FileInputStream(new File(root + s + scorer)));
                robot.load(new FileInputStream(new File(root + s + selectedrobot)));
                SmartDashboard.putString("Files", "Computer Files");
                return "we got the files";
            }catch(Exception f){

                try{    //uses defaults in the deploy directory
                drv.load(new FileInputStream(new File(root + s + driver)));
                scr.load(new FileInputStream(new File(root + s + scorer)));
                robot.load(new FileInputStream(new File(root + s + selectedrobot)));
                SmartDashboard.putString("Files", "Default Files");
                return "we got the defaults";
                }catch(Exception g){
                    return "we got nothing";
                }
            }
        }
    }
}
