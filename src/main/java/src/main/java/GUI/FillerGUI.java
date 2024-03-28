package src.main.java.GUI;

import javax.swing.*;

import utils.Definition.ColorBGRValue;
import utils.File.IconLocator;
import utils.GUI.ClickFloodFill;
import utils.GUI.FloodFillImage;
import utils.GUI.MainImage;

public class FillerGUI extends JButton
{
	public FillerGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(18)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && MainImage.checkBinary() && GUI.isEditing(-1))
		{
			GUI.createGUI();
			GUI.setEditing(1);
			GUI.changeCursor(1);
			ClickFloodFill.setMode(ColorBGRValue.BGR_BLACK);
			FloodFillImage.setImageMat(MainImage.getImageMat());

			System.out.println("fill bucket tool has been launched");
		}
		else if(GUI.isEditing(1)){
			GUI.destroyGUI();
			GUI.setEditing(-1);
			GUI.changeCursor(-1);
			ClickFloodFill.save("filler");

			System.out.println("fill bucket tool has been closed");
		}
	}
}
