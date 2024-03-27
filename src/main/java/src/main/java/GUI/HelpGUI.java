package src.main.java.GUI;

import utils.GUI.MainImage;
import utils.Processing.BackgroundRemoval;
import utils.Processing.Invert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HelpGUI extends JButton
{
	public HelpGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(4)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				act();
			}
		});
	}

	public static void act()
	{
		HelpGUI.launch();
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Helpful Information", true);
		dialog.setLayout(new GridLayout(1, 1));

		dialog.setSize(200, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
