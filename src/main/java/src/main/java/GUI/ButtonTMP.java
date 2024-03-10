package src.main.java.GUI;

import src.main.java.Settings;

import javax.swing.*;
import java.awt.event.*;

public class ButtonTMP extends JButton {
	public ButtonTMP()
	{
		ImageIcon icon = new ImageIcon(FilterGUI.getFilepath(7));
		setIcon(icon);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Handle action for button 3
				System.out.println("Button clicked");
			}
		});
	}
}
