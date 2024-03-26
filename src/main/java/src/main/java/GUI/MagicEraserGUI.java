package src.main.java.GUI;

import javax.swing.*;

public class MagicEraserGUI extends JButton
{
	public MagicEraserGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(19)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			MagicEraserGUI.launch();
		}
	}

	public static void launch()
	{
		System.out.println("magic eraser tool has been launched");
	}
}
