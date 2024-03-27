package src.main.java.GUI;



import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.GUI.Zoom;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomOutGUI extends JButton
{
	public ZoomOutGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(16)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			Zoom.zoomOut();
		}
	}
}
