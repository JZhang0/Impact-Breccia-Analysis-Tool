package src.main.java.GUI;

import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.Processing.Automate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;

import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AutomateGUI extends JButton
{
	private static JButton button_auto_1, button_auto_2, button_save;

	public AutomateGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(14)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI() && true)
		{
			GUI.createGUI();
			AutomateGUI.launch();
		}
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Automatic processing", true);
		dialog.setLayout(new GridLayout(1, 3));
		GridBagConstraints constraints = new GridBagConstraints();
		
		//Automate 1 button
		button_auto_1 = new JButton("Algorithm Dark");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(button_auto_1, constraints);

		//Automate 2 button
		button_auto_2 = new JButton("Algorithm Bright");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_auto_2, constraints);

		//Confirmation button
		button_save = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);

		button_auto_1.addActionListener(e ->
		{
			Automate.run(1);
			GUI.render(MainImage.getImageMat());
		});

		button_auto_2.addActionListener(e ->
		{
			Automate.run(2);
			GUI.render(MainImage.getImageMat());
		});

		button_save.addActionListener(e ->
		{
			Automate.save();
			GUI.destroyGUI();
			dialog.dispose();
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to launch the automate alg
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				Automate.reset();
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
			}
		});

		dialog.setSize(400, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
