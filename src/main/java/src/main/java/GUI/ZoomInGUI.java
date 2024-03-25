package src.main.java.GUI;


import utils.GUI.Zoom;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomInGUI extends JButton
{
	public ZoomInGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(15)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			Zoom.zoomIn();
		}
	}
}
