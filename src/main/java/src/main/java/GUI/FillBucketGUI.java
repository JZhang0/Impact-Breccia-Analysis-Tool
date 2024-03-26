package src.main.java.GUI;

import javax.swing.*;

public class FillBucketGUI extends JButton
{
	public FillBucketGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(18)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			FillBucketGUI.launch();
		}
	}

	public static void launch()
	{
		System.out.println("fill bucket tool has been launched");
	}
}
