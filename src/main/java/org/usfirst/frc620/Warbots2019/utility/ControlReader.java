/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class ControlReader{

    static Properties drv1, drv2, var;
    static String s1, s2, s3;

    public ControlReader(){

        //creates Properties objects and strings for file references
        drv1 = new Properties();
        drv2 = new Properties();
        var = new Properties();
        String s = File.separator;
        String d1 = "driver1.properties";
        String d2 = "driver2.properties";
        String v = "variables.properties";

        //attempts to load .properties files to the Properties object
        try{
        //String of main directory
        String root = "" + Filesystem.getOperatingDirectory() + s + "src" + s + "main" + s + "deploy";
        //loads directory plus specific file name
        drv1.load(new FileInputStream(new File(root + s + d1)));
        drv2.load(new FileInputStream(new File(root + s + d2)));
        var.load(new FileInputStream(new File(root + s + v)));
        s1 = root + s + d1;
        s2 = root + s + d2;
        s3 = root + s + v;
        }catch(Exception e){
            System.out.println("There was an Exception " + e.getMessage());
        }

    }

    //returns int from variables
    public static int getMappedInt(String string){
        int str = Integer.parseInt(var.getProperty(string));
        return str;
    }

    //returns String from either driver1 or driver2
    public static String getMappedString(String string, boolean isMainDriver){
        String s;
        if(isMainDriver){
            s = drv1.getProperty(string);
        }else{
            s = drv2.getProperty(string);
        }
        return s;
    }

    //returns String from variables (no boolean field)
    public static String getMappedString(String string){
        try{
            String s = var.getProperty(string);
            return s;
        }catch(Exception e){
            System.out.println(string);
            System.out.println("getMappedString didn't work: " + e.getMessage());
            return "Taz";
        }
    }

    //returns double from variables
    public static double getMappedDouble(String string){
        double d = Double.parseDouble(var.getProperty(string));
        return d;
    }

    public static void test(){
        drv1.list(System.out);
        drv2.list(System.out);
        var.list(System.out);
    }

}
