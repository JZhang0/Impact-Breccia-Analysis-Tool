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
				if (MainImage.exists())
				{
					SaveGUI.launch();
				}
			}
		});
	}

	public static void launch()
	{
		System.out.println("Save launched WIP");
	}
}
