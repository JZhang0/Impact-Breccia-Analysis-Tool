package src.main.java.GUI;

import utils.File.IconLocator;
import utils.File.History;
import utils.GUI.MainImage;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RedoGUI extends JButton
{
	public RedoGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(13)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.changeCursor(2);

			History.redo();

			GUI.changeCursor(-1);
		}
	}
}
