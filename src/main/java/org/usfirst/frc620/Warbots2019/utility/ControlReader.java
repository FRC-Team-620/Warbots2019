/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

// SEP removed dependency on WPILib so we can do unit test
// without needing access to WPILib semantics/runtime issues
//import edu.wpi.first.wpilibj.Filesystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class ControlReader 
{
    public String robotName;
    Properties prop;
    String rootDeployDir;
    String rootUSBDir;
    
    String s;
    ArrayList<String> searchPath;
    static ArrayList<String> loadedFiles;
    /**
     * Constructor method
     * Helps programs look for and read the .properties config files
     */
    public ControlReader()
    {
        loadedFiles = new ArrayList<String>();
        prop = new Properties();
        
        s = File.separator;
        rootDeployDir = "/home/lvuser/deploy"; //"" + Filesystem.getDeployDirectory();
        reloadConfiguration();
    }

    /**
     * Internal Utility for seeing if a string value exists from any of the containers
     * @param str
     * @return ret
     */
    public boolean hasName(String str)
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
     * @return null if it doesn't exist, "" if it's in file but not set to 
     *         anything, or string it's assigned to
     */
    private String getNamedValue(String str)
    {
        String ret = null;
        if( prop.getProperty(str) != null)
        {
            ret = prop.getProperty(str);
        }
        if(!hasName(str))
        {
            System.err.println("ControlReader: doesn't have name " + str);    
        }
        if (ret != null)
        {
            ret = ret.trim();
        }
        return ret;
    }

    /**
     * returns int from files, -1 if invalid
     * @param string
     * @return i
     */
    public int getMappedInt(String string)
    {
        int i;
        String v = getNamedValue(string);
        try
        {
            i = Integer.parseInt(v);
        } 
        catch (Exception e) 
        {
            i = -1;
        }
        return i;
    }

    /**
     * returns int from files, -1 if invalid
     * @param string
     * @return i
     */
    public boolean getMappedBoolean(String string)
    {
        boolean i = false;
        String v = getNamedValue(string);
        try
        {
            i = Boolean.parseBoolean(v);
        } 
        catch (Exception e) 
        {
        }
        return i;
    }
    
    /**
     * Returns the named value string in a file
     * @param string
     * @return ret
     */
    public String getMappedString(String string)
    {
        String ret = getNamedValue(string);
        
        return ret;
    }

    //returns String from variables (no boolean field)

    /**
     * returns double from files, -1.0 if invalid
     * @param string
     * @return 
     */
    public double getMappedDouble(String string)
    {
        double d;
        String v = getNamedValue(string);
        try
        {
            d = Double.parseDouble(v);
        } 
        catch (Exception e) 
        {
            d = -1.0;
        }
        return d;
    }

    public void getProperties()
    {

        prop.list(System.out);
    }
    /**
     * Returns the robot type in a String ret
     * @return 
     */
    public String getRobotType()
    {
        String ret = null;
        try
        {
            //Logger.log("ControlReader: Get Robot Type");
            // On robot, hn will be "roboRIO-620-FRC"
            String hn = InetAddress.getLocalHost().getHostName() ;
            NetworkInterface net = NetworkInterface.getByInetAddress(InetAddress.getByName(hn));
            
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

        }
        catch(Exception e)
        {
            System.err.println("ControlReader: Error Getting Robot Type: " + e.getMessage());
        }
        return ret;
    }

    /**
     * Looks for the .properties files based off a given filename
     * @param filename
     * @return the completion status of the operation
     */
    public boolean lookForFiles(String filename)
    {
        boolean ret = false;
        //Logger.log("ControlReader: Looking for file: ["+filename+"]");
        for(int i = 0; i < searchPath.size(); i++)
        {
            try
            {    
                String fn = searchPath.get(i) + s + filename;
                Logger.log("  looking for file: ["+fn+"]");
                prop.load(new FileInputStream(new File(fn)));
                
                //Logger.log ("ControlReader: found ["+ searchPath.get(i) + s + filename+"]");
                ret = true;
                loadedFiles.add(fn);
                Logger.log("  found file: ["+fn+"]");
                break;
            }
            catch(Exception e)
            {
                //System.err.println("ControlReader: unable to find file: ["+filename+"]");
            }
        }
        return ret;
    }

    /**
     * Dumps all the possible configuration settings to the dump file - this file
     * can serve as a template for new config files or for comparison to other
     * files that may have issues.
     * @param fn
     * @param confs
     */
    public static void dumpConfigurationFile(String fn, ArrayList<Configurable> confs)
    {
        try
        {
            Logger.log("Dumping configuration file ["+fn+"]");
            File file = new File(fn);
            FileWriter writer = new FileWriter(file);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = new Date();
	        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
            writer.write("##########################################################\n");
            writer.write("#  This file was generated at one time - in order to ease\n");
            writer.write("#  comparison with other config files it is advisable that\n");
            writer.write("#  you do not alter the ORDER of things, though you're free\n");
            writer.write("#  to change the values, comment-out, or remove names altogether\n");
            writer.write("##########################################################\n");
            writer.write("# data of template generation: ["+dateFormat.format(date)+"]\n");
            Logger.log("    looping through Configurables ["+confs.size()+"]");
            for (int i = 0; i<confs.size(); i++)
            {
                int j = 0;
                Configurable cfg = confs.get(i);
                ArrayList<String> names = cfg.getNames();
                for (j=0; j<names.size(); j++)
                {
                    Logger.log("    processing Configuable ["+names.get(j)+"]");
                    String comment = cfg.getCommentForName(names.get(j));
                    if (comment != null)
                    {
                        writer.write("# "+comment+"\n");
                        writer.write("#\n");
                    }

                    ArrayList<String> opts = cfg.getPossibleValuesForName(names.get(j));
                    if (opts != null)
                    {

                        writer.write("# Options:\n");
                        for (int k=0; k<opts.size(); k++)
                        {
                            writer.write("#   "+opts.get(k)+"\n");
                        }
                    }
                    else
                    {
                        writer.write("# (no options defined)\n");
                    }
                    writer.write(names.get(j)+" = \n");
                    writer.write("\n");
                }
                writer.write("\n");
            }
            
            writer.close();
        }
        catch(Exception e) 
        {
            System.err.println("dumpConfigurationFile(): "+e.getMessage());
            // Don't care
        }
    }
    /**
     * returns String ArrayList of the paths/filenames that were loaded by dumpConfigurationFile()
     * @param arr
     * @return
     */
    public static ArrayList<String> getLoadedFiles()
    {
        return loadedFiles;
    }    
    /**
     * Reloads the configuration
     * Exact copy of what happens in the consstructor
     */
    public void reloadConfiguration()
    {
        Logger.log("==============================================================");
        Logger.log("===                    Begin Configuration                 ===");
        loadedFiles = new ArrayList<String>();
        prop = new Properties();
        
        s = File.separator;

        String robotFileName = getRobotType();

        //
        // Build list of search paths, in order of precedence.
        // First look on USB stick, if not there, look in deploy
        // directory, then we may be running on a developer's laptop
        // in sim mode/Debug mode, so allow finding defaults in local
        // Windows file system.
        //
        searchPath = new ArrayList<String>(Arrays.asList(
            // USB memory stick
            s + "media" + s + "sda1",
            s + "media" + s + "sda2",
            // Deploy location in the Linux RoboRIO OS
            rootDeployDir,
            // Local windows machine development environment.
            "C:" + s + "Users" + s + "Public" + s + "frc2019" + s + "workspace" + s + 
                "Warbots2019" + s + "src" + s + "main" + s + "deploy",
            // Local Windows machine unit test directory
            "."));

        Logger.log("Robot filename: ["+robotFileName+"]");

        // Look first for MAC-address based robot file
        if (!lookForFiles(robotFileName) && !lookForFiles("any_robot.properties"))
        {
            // This is only for debugging in case there's no MAC-address based file
            System.err.println("ControlReader: Unable to locate MAC-based robot config ["+
                robotFileName+"]");
            if (!lookForFiles("laptop_robot.properties"))
            {
                System.err.println("ControlReader: Unable to locate ANY robot properties");
            }
        }
        
        //
        // The robot properties MUST contain a 'name' property so we can
        // have user-readable file-names for the default driver/scorer properties.
        //
        String name = this.getNamedValue("name");
        if(name == null)
        {
            System.err.println("ControlReader: Config missing name property");
        }
        else
        {
            // Look for robot-specific driver/scorer files in case there's no 
            // user-specific files in USB stick.
            robotName = name;
           
            Logger.log("Robot Name: [" + name+"]");
            lookForFiles(name + ".driver.properties");
            lookForFiles(name + ".scorer.properties");      
        }

        // Look for user-provided files that should be on the USB stick for matches
        lookForFiles("driver.properties");
        lookForFiles("scorer.properties"); 

        for (int i=0; i<loadedFiles.size(); i++)
            Logger.log("  file loaded: ["+loadedFiles.get(i)+"]");

        // Remove comments from values
        for (Enumeration<Object> e=prop.keys(); e.hasMoreElements(); )
        {
            String key = e.nextElement().toString();
            String v = prop.getProperty(key, "");
            if (v.indexOf("#") > -1)
            {
                if (v.indexOf("#") > 0)
                {
                    // Remove chars after comment char
                    prop.put(key, v.substring(0, v.indexOf("#")-1).trim());
                }
                else
                {
                    // Value is entirely a comment
                    prop.put(key, "");
                }
            }
        }
        Logger.log("===                     End Configuration                  ===");
        Logger.log("==============================================================");
    }
}
