package src.main.java.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CropGUI extends JButton
{
	public CropGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(0)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			CropGUI.launch();
		}
	}

	public static void launch()
	{
		System.out.println("crop");
	}
}
