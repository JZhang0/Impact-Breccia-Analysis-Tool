package src.main.java.GUI;

import javax.swing.*;

import utils.Definition.ColorBGRValue;
import utils.File.IconLocator;
import utils.GUI.ClickFloodFill;
import utils.GUI.FloodFillImage;
import utils.GUI.MainImage;
import utils.GUI.Toggle;

public class EraserGUI extends JButton
{
	public EraserGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(19)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && MainImage.checkBinary() && GUI.isEditing(-1))
		{
			GUI.createGUI();
			GUI.setEditing(0);
			GUI.changeCursor(0);
			ClickFloodFill.setMode(ColorBGRValue.BGR_WHITE);
			FloodFillImage.setImageMat(MainImage.getImageMat());

			System.out.println("magic eraser tool has been launched");
		}
		else if(GUI.isEditing(0)){
			GUI.destroyGUI();
			GUI.setEditing(-1);
			GUI.changeCursor(-1);
			ClickFloodFill.save("eraser");
			ToggleImage.reset();

			System.out.println("magic eraser tool has been closed");
		}
	}
}
