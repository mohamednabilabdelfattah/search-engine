import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RobotParser {
	public static boolean robotSafe(URL url)
	{
		String strHost = url.getHost();

		String strRobot = "https://" + strHost + "/robots.txt";
		URL urlRobot;
		try { urlRobot = new URL(strRobot);
		} catch (MalformedURLException e) {
			// something weird is happening, so don't trust it
			System.out.println("error 1");
			return false;
		}

		String strCommands;
		try
		{
			InputStream urlRobotStream = urlRobot.openStream();
			byte b[] = new byte[1500];
			int numRead = urlRobotStream.read(b);
			strCommands = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlRobotStream.read(b);
				if (numRead != -1)
				{
					String newCommands = new String(b, 0, numRead);
					strCommands += newCommands;
				}
			}
			urlRobotStream.close();
		}
		catch (IOException e)
		{
			System.out.println("error 2");
			return true; // if there is no robots.txt file, it is OK to search
		}

		if (strCommands.contains("Disallow")) // if there are no "disallow" values, then they are not blocking anything.
		{
			//System.out.println(strCommands);
			String[] split = strCommands.split("\n");
			ArrayList<RobotRule> robotRules = new ArrayList<>();
			String mostRecentUserAgent = null;
			for (int i = 0; i < split.length; i++)
			{
				String line = split[i].trim();
				if (line.toLowerCase().startsWith("user-agent"))
				{
					int start = line.indexOf(":") + 1;
					int end   = line.length();
					mostRecentUserAgent = line.substring(start, end).trim();
					if(!mostRecentUserAgent.equals("*") && !mostRecentUserAgent.contains("Java"))
						mostRecentUserAgent = null;

				}
				else if (line.startsWith("Disallow")) {
					if (mostRecentUserAgent != null) {
						RobotRule r = new RobotRule();
						r.userAgent = mostRecentUserAgent;
						int start = line.indexOf(":") + 1;
						int end   = line.length();
						r.rule = line.substring(start, end).trim();
						robotRules.add(r);
					}
				}
			}

			for (RobotRule robotRule : robotRules)
			{
				String path = url.getPath();
				if (robotRule.rule.length() == 0) {System.out.println("you are allowed");return true;} // allows everything if BLANK
				if (robotRule.rule == "/") {System.out.println("you aren't allowed from all");return false; }      // allows nothing if /

				if (robotRule.rule.length() <= path.length())
				{
					String pathCompare = path.substring(0, robotRule.rule.length());
					if (pathCompare.equals(robotRule.rule)) {System.out.println("error 5");return false;}
				}
			}
		}
		System.out.println("you are allowed");
		return true;
	}
}