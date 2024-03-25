package src.main.java.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveGUI extends JButton
{
	public SaveGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(11)));
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
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			SaveGUI.launch();
		}
	}

	public static void launch()
	{
		System.out.println("Save launched WIP");
	}
}
