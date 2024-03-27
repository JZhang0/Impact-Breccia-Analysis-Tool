package src.main.java.GUI;

import javax.swing.*;

import utils.File.IconLocator;
import utils.GUI.MainImage;

public class Filler extends JButton
{
	public Filler()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(18)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			Filler.launch();
		}
	}

	public static void launch()
	{
		System.out.println("fill bucket tool has been launched");
	}
}
