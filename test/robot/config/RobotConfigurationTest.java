package robot.config;

// JDK code
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// WPILib code
import edu.wpi.first.wpilibj.RobotBase;

// JMHS code
import org.usfirst.frc620.Warbots2019.robot.Robot;
import org.usfirst.frc620.Warbots2019.sim.SimRobot;
import org.usfirst.frc620.Warbots2019.utility.Logger;

public class RobotConfigurationTest
{

	/**
	 * Check robot loading config
	 */
	private static int checkRobotInit()
	{
		String sig = "checkRobotInit()";
		int ret = 0;
		Logger.log(sig+":");
		
		// This is equivalent to Robot.startRobot(Robot::new);, in the main()
		// but ours exits after 5 iterations
		SimRobot.testRobot(SimRobot::new, 5);
		
        Logger.log(sig+": sim robot run done");
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
		Runtime.getRuntime().halt(ret);
    }
}