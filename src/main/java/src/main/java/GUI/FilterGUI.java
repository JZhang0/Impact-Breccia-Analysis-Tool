package src.main.java.GUI;

import src.main.java.Settings;

public class FilterGUI
{
	//Return the filepath of the filter's icon
	public static String getFilepath(int icon_id)
	{
		return "icons/" + Settings.ICONS[icon_id] + ".png";
	}
}
