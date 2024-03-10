package src.main.java.GUI;

import utils.Processing.RGB;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RGBGUI extends JButton
{
	public RGBGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(2)));

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (MainImage.exists())
				{
					RGBGUI.launch();
				}
			}
		});
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "RGB channel splitter", true);
		dialog.setLayout(new GridLayout(1, 4));
		ButtonGroup group = new ButtonGroup();
		JToggleButton[] buttons = new JToggleButton[3];

		buttons[0] = new JToggleButton("<html><center>Preview red<br>channel</center></html>");
		buttons[1] = new JToggleButton("<html><center>Preview green<br>channel</center></html>");
		buttons[2] = new JToggleButton("<html><center>Preview blue<br>channel</center></html>");
		JButton button4 = new JButton("<html><center>Save current<br>channel</center></html>");

		buttons[0].setBackground(Color.red);
		buttons[1].setBackground(Color.green);
		buttons[2].setBackground(Color.blue);
		buttons[2].setForeground(Color.white);

		for (int i = 0; i < 3; i++)
		{
			buttons[i].setFocusable(false);
			group.add(buttons[i]);
			dialog.add(buttons[i]);

			int finalI = i;
			buttons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (buttons[finalI].isSelected())
						GUI.render(MainImage.matToByte(RGB.split(finalI)));
					else
						GUI.render(MainImage.matToByte(MainImage.getImageMat()));

				}
			});
		}

		button4.setFocusable(false);
		dialog.add(button4);

		//Save
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				RGB.save();
				dialog.dispose();
			}
		});

		//Detect when the dialog closes and reset the channel preview if no channel has been saved
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				RGB.resetChannel();
				GUI.render(MainImage.matToByte(MainImage.getImageMat()));
			}
		});

		dialog.setSize(400, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
