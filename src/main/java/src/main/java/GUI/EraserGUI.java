package src.main.java.GUI;

import javax.swing.*;

import utils.File.IconLocator;
import utils.GUI.MainImage;

public class EraserGUI extends JButton
{
	public EraserGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(19)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			EraserGUI.launch();
		}
	}

	public static void launch()
	{
		System.out.println("magic eraser tool has been launched");
	}
}
