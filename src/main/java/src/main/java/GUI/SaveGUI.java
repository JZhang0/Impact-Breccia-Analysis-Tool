package src.main.java.GUI;

import src.main.java.Settings;
import utils.File.Save;
import utils.GUI.MainImage;
import utils.Processing.Contrast;
import utils.Processing.RGB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		JDialog dialog = new JDialog(GUI.getFrame(), "Export processed image", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		ButtonGroup group = new ButtonGroup();
		JToggleButton[] buttons = new JToggleButton[Settings.SUPPORTED_FILE_FORMATS.length];
		JButton button_save = new JButton("Export");

		final int[] filetype = {4};

		//Label alpha
		JLabel label = new JLabel("Filename: ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(label, constraints);

		//Textfield
		JTextField text_field = new JTextField(50);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = Settings.SUPPORTED_FILE_FORMATS.length - 1;
		dialog.add(text_field, constraints);

		//Add buttons
		for (int i = 0; i < Settings.SUPPORTED_FILE_FORMATS.length; i++)
		{
			buttons[i] = new JToggleButton("<html><center>" + Settings.SUPPORTED_FILE_FORMATS[i] + "</center></html>");

			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 1;
			constraints.gridwidth = 1;

			int finalI = i;
			buttons[i].addActionListener(e ->
			{
				if (buttons[finalI].isSelected())
					filetype[0] = finalI;
			});

			dialog.add(buttons[i], constraints);
			group.add(buttons[i]);
		}

		//Set defaults
		text_field.setText(MainImage.getFilename());
		buttons[filetype[0]].setSelected(true);

		dialog.add(button_save);

		//Save
		button_save.addActionListener(e ->
		{
			if (!text_field.getText().isEmpty())
			{
				Save.export(text_field.getText(), filetype[0]);
				GUI.destroyGUI();
				dialog.dispose();
			}
		});

		//Detect when the dialog closes
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.destroyGUI();
			}
		});

		dialog.setSize(500, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
