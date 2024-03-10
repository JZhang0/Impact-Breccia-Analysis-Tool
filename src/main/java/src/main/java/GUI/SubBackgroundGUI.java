package src.main.java.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubBackgroundGUI extends JButton
{
	public SubBackgroundGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(4)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (MainImage.exists())
				{
					SubBackgroundGUI.launch();
				}
			}
		});
	}

	public static void launch()
	{
		System.out.println("SubBackgroundGUI launched WIP");
	}
}
