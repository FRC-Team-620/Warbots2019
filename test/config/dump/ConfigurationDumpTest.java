package config.dump;

import java.util.ArrayList;

import org.usfirst.frc620.Warbots2019.utility.ControlReader;
import org.usfirst.frc620.Warbots2019.utility.Logger;

public class ConfigurationDumpTest
{
	/**
	 * Verfiy that three files were loaded
	 */
	private static int checkLoadedFiles(ControlReader config)
	{
		String sig = "checkLoadedFiles()";
		int ret = 1;
		Logger.log(sig+": files loaded:");
		ArrayList<String> files = ControlReader.getLoadedFiles();
		for (int i=0; i<files.size(); i++)
		{
			Logger.log(files.get(i));
		}
		if (files.size() == 3) 
		{
			ret = 0;
		}
		else
		{
			Logger.log("ERROR: wrong number of files loaded["+files.size()+"] vs 3");
		}
		return ret;
	}
	
	/**
	 * Check various types of values coming from each of the three files.
	 */
	private static int checkExistingValues(ControlReader config)
	{
		String sig = "checkExistingValues()";
		int num = 0;
		boolean success = false;
		Logger.log(sig+":");
		if (config.hasName("driver.enabled"))
		{
			if (config.getMappedBoolean("driver.enabled"))
			{
				num++;
				success = true;
			}
		}
		if (!success)
		{
			Logger.log("ERROR: missing valid 'driver.enabled'");
		}
		
		success = false;
		if (config.hasName("scorer.enabled"))
		{
			if (!config.getMappedBoolean("scorer.enabled"))
			{
				num++;
				success = true;
			}
		}
		if (!success)
		{
			Logger.log("ERROR: missing valid 'scorer.enabled'");
		}
		
		success = false;
		if (config.hasName("ScoringMechanism"))
		{
			if (config.getMappedString("ScoringMechanism").equals(""))
			{
				num++;
				success = true;
			}
		}
		if (!success)
		{
			Logger.log("ERROR: missing valid 'ScoringMechanism'");
		}
		
		success = false;
		if (config.hasName("scorer.RightJS.Y"))
		{
			if (config.getMappedString("scorer.RightJS.Y").equals("OI.elevator.speed"))
			{
				num++;
				success = true;
			}
		}
		if (!success)
		{
			Logger.log("ERROR: missing valid 'scorer.RightJS.Y'");
		}
		
		success = false;
		if (config.hasName("driver.speed_deadzone"))
		{
			double v = config.getMappedDouble("driver.speed_deadzone");
			if ((v > 0.087) && (v < 0.088))
			{
				num++;
				success = true;
			}
		}
		if (!success)
		{
			Logger.log("ERROR: missing valid 'scorer.RightJS.Y'");
		}
		
		return (num == 5)?0:1;
	}
	
    public static void main(String... args) 
	{
		int ret = 0;
        ControlReader config = new ControlReader();
		
		ret = checkLoadedFiles(config);
		if (ret == 0)
		{
			ret = checkExistingValues(config);
		}
		
		System.exit(ret);
    }
}