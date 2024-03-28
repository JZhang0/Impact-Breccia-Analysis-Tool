package src.main.java.GUI;

import utils.Processing.BackgroundRemoval;
import utils.File.FileIO;
import utils.GUI.AnchorImage;
import utils.GUI.MainImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SubBackgroundGUI
{
	public static void act()
	{
		if (MainImage.exists() && AnchorImage.exists())
		{
			GUI.createGUI();
			SubBackgroundGUI.launch();
		}
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Subtract background", true);
		dialog.setLayout(new GridLayout(1, 1));
		JButton button = new JButton("Confirm");
		dialog.add(button);

		//Save
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.destroyGUI();
				dialog.dispose();
                BackgroundRemoval.save();
			}
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to invert colours
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
                AnchorImage.setImageMat(MainImage.getImageMat());
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
				
				FileIO.export("default", false, false);
			}
		});

		dialog.setSize(200, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}