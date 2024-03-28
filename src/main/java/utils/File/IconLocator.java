package utils.File;

import src.main.java.Settings;

public class IconLocator
{
	//Return the filepath of the filter's icon
	public static String getIconPath(int icon_id)
	{
		return "icons/" + Settings.ICONS[icon_id] + ".png";
	}
}