package src.main.java.GUI;

import utils.File.IconLocator;
import utils.File.History;
import utils.GUI.MainImage;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoGUI extends JButton
{
	public UndoGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(12)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			History.undo();
		}
	}
}
