package robot.config;

import java.util.ArrayList;
		
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

public class RobotConfigurationTest
{
	static Robot robot;
	/**
	 * Check robot loading config
	 */
	private static int checkRobotInit()
	{
		String sig = "checkRobotInit()";
		int ret = 0;
		Logger.log(sig+":");
		robot = new Robot();
		RobotConfigurationTest.robot.robotInit();
		Logger.log("a");
		Thread thr = new Thread(new Runnable(){
			public void run(){
				
				RobotConfigurationTest.robot.startCompetition();
				Logger.log("b");
			}
		});
		thr.start();
		
		Logger.log("c");
		robot.close();
		Logger.log("d");
		try
		{
		    thr.join();
		}
		catch(Exception e)
		{
		}
		Logger.log("e");
		//if (!success)
		//{
		//	Logger.log("ERROR: missing valid 'scorer.RightJS.Y'");
		//}
		return ret;
	}
	/**
	 * Dump static config
	 */
	private static int checkStaticConfigDump()
	{
		String sig = "checkStaticConfigDump()";
		int ret = 0;
		Logger.log(sig+":");
		Robot.dumpConfiguration();
		//if (!success)
		//{
		//	Logger.log("ERROR: missing valid 'scorer.RightJS.Y'");
		//}
		
		return ret;
	}
	
    public static void main(String... args) 
	{
		int ret = 0;

		ret = checkStaticConfigDump();
		if (ret == 0)
		{
			ret = checkRobotInit();
		}
		
		System.exit(ret);
    }
}