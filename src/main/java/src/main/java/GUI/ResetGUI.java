package src.main.java.GUI;



import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.GUI.Pan;
import utils.GUI.Zoom;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetGUI extends JButton
{
	public ResetGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(17)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			Zoom.zoomReset();
			Pan.panReset();
		}
	}
}
