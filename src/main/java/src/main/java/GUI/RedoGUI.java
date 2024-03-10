package src.main.java.GUI;

import utils.File.History;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RedoGUI extends JButton
{
	public RedoGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(13)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (MainImage.exists())
				{
					History.redo();
				}
			}
		});
	}
}
