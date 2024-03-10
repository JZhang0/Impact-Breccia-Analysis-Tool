package src.main.java.GUI;

import utils.Processing.Invert;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InvertGUI extends JButton
{
	public InvertGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(3)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (MainImage.exists())
				{
					InvertGUI.launch();
				}
			}
		});
	}

	private static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Invert colours", true);
		dialog.setLayout(new GridLayout(1, 1));
		JButton button = new JButton("Confirm");
		dialog.add(button);

		//Render the inverted colours
		GUI.render(MainImage.matToByte(Invert.invert()));

		//Save
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Invert.save();
				dialog.dispose();
			}
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to invert colours
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.render(MainImage.matToByte(MainImage.getImageMat()));
			}
		});

		dialog.setSize(200, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
