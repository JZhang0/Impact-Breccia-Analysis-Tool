/*
 * Copyright (C) 2024 Yifei Zhang, Nicolas Louis Jacobs, Yuhan Zhang - All Rights Reserved
* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package src.main.java.GUI;

import src.main.java.Settings;
import utils.File.History;
import utils.File.IconLocator;
import utils.File.Save;
import utils.GUI.MainImage;

import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.FileSystems;

public class SaveGUI extends JButton
{
	private static int filetype;
	private static Boolean[] analysis_selection = {false, false, false, false, false, false, false};

	public SaveGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(11)));
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
		if (MainImage.exists() && GUI.canCreateGUI() && MainImage.checkBinary())
		{
			GUI.createGUI();
			SaveGUI.launch();
		}
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Export Result", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel label_path = new JLabel("Export Path: ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets.left = 10;
		constraints.insets.top = 10;
		dialog.add(label_path, constraints);

		JTextField text_field = new JTextField(50);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = Settings.SUPPORTED_FILE_FORMATS.length;
		dialog.add(text_field, constraints);

		JButton button_Browse = new JButton("Browse");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = Settings.SUPPORTED_FILE_FORMATS.length + 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(button_Browse, constraints);

		JLabel label_filetype = new JLabel("Export type: ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.insets.right = 10;
		dialog.add(label_filetype, constraints);
		constraints.insets.left = 0;
		constraints.insets.right = 0;

		ButtonGroup filetype_group = new ButtonGroup();
		JToggleButton[] filetype_buttons = new JToggleButton[Settings.SUPPORTED_FILE_FORMATS.length];
		for (int i = 1; i < Settings.SUPPORTED_FILE_FORMATS.length + 1; i++)
		{
			filetype_buttons[i-1] = new JToggleButton("<html><center>" + Settings.SUPPORTED_FILE_FORMATS[i-1] + "</center></html>");

			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 1;
			constraints.gridwidth = 1;

			int finalI = i - 1;
			filetype_buttons[i-1].addActionListener(e ->
			{
				if (filetype_buttons[finalI].isSelected())
					filetype = finalI;
			});

			dialog.add(filetype_buttons[i-1], constraints);
			filetype_group.add(filetype_buttons[i-1]);
		}

		JLabel label_analysistype = new JLabel("Export type: ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.insets.left = 10;
		constraints.insets.right = 10;
		dialog.add(label_analysistype, constraints);
		constraints.insets.left = 0;
		constraints.insets.right = 0;

		JToggleButton[] analysistype_buttons = new JToggleButton[Settings.SUPPORTED_ANALYSIS_TYPES.length];
		for (int i = 1; i < Settings.SUPPORTED_ANALYSIS_TYPES.length + 1; i++)
		{
			analysistype_buttons[i-1] = new JToggleButton("<html><center>" + Settings.SUPPORTED_ANALYSIS_TYPES[i-1] + "</center></html>");

			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 2;
			constraints.gridwidth = 1;

			int finalI = i - 1;
			analysistype_buttons[i-1].addActionListener(e ->
			{
				if (analysistype_buttons[finalI].isSelected()){
					analysis_selection[finalI] = true;
				}
				else{
					analysis_selection[finalI] = false;
				}
			});

			dialog.add(analysistype_buttons[i-1], constraints);
		}


		JButton button_save = new JButton("Export");
		button_save.setBackground(new Color(0, 104, 132));
		button_save.setForeground(Color.WHITE);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);


		//Set defaults
		text_field.setText(FileSystems.getDefault().getPath("").toAbsolutePath().toString());
		text_field.setEditable(false);
		filetype = 3;
		filetype_buttons[3].setSelected(true);

		// Save
		button_save.addActionListener(e ->
		{
			if (!text_field.getText().isEmpty())
			{
				GUI.changeCursor(2);

				GUI.destroyGUI();
				dialog.dispose();
				History.saveHistory();
				Save.export(text_field.getText() + "\\", filetype, analysis_selection);

				GUI.changeCursor(-1);
			}
		});

		button_Browse.addActionListener(e ->
		{
			if (!text_field.getText().isEmpty())
			{
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				if(fc.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION){
					text_field.setText(fc.getSelectedFile().toString());
				}
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

		dialog.setSize(1000, 200);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
