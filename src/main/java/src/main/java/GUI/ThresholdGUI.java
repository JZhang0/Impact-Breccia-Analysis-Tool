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
import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.Processing.Thresholding;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ThresholdGUI extends JButton
{
	private static JLabel label;
	private static JSlider slider;
	private static JTextField text_field;
	private static JButton button_auto_bright, button_auto_dark, button_save;

	//This is equivalent to the thresh value saved in the Threshold class
	private static int threshold_adjustment_value;

	private static boolean getSetting = false;

	private static void getThresholdSetting(){
		getSetting = true;
		threshold_adjustment_value = Thresholding.getThresh();

		text_field.setText(String.valueOf(threshold_adjustment_value));
		slider.setValue(threshold_adjustment_value);

		GUI.render(Thresholding.adjustThreshold(threshold_adjustment_value));
		getSetting = false;
	}

	public ThresholdGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(5)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			ThresholdGUI.launch();
		}
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Threshold adjustment", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		//Label
		label = new JLabel("Threshold value ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(label, constraints);

		//Slider
		slider = new JSlider(Settings.THRESHOLD_MIN, Settings.THRESHOLD_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		dialog.add(slider, constraints);

		//Textfield
		text_field = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(text_field, constraints);

		//Automate button
		button_auto_bright = new JButton("Automate 1");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_auto_bright, constraints);

		//Automate button
		button_auto_dark = new JButton("Automate 2");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_auto_dark, constraints);

		//Confirmation button
		button_save = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);

		//Set defaults
		getThresholdSetting();

		//Updating the slider
		slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
				threshold_adjustment_value = slider.getValue();
				text_field.setText(String.valueOf(threshold_adjustment_value));

				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting() && !getSetting){
					GUI.changeComponentCursor(2, dialog);

					GUI.render(Thresholding.adjustThreshold(threshold_adjustment_value));

					GUI.changeComponentCursor(-1, dialog);
				}
			}
        });

		//Override the documentListener so we can control what happens when the textfield changes
		text_field.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                threshold_adjustment_value = Integer.parseInt(text_field.getText());
				if (text_field.getText().matches("\\d+") && threshold_adjustment_value >= Settings.THRESHOLD_MIN && threshold_adjustment_value <= Settings.THRESHOLD_MAX)
				{
					slider.setValue(threshold_adjustment_value);
				}
            }
        });

		//Automate
		button_auto_bright.addActionListener(e ->
		{
			GUI.changeComponentCursor(2, dialog);

			GUI.render(Thresholding.auto_bright());
			getThresholdSetting();

			GUI.changeComponentCursor(-1, dialog);
		});

		//Automate
		button_auto_dark.addActionListener(e ->
		{
			GUI.changeComponentCursor(2, dialog);

			GUI.render(Thresholding.auto_dark());
			getThresholdSetting();

			GUI.changeComponentCursor(-1, dialog);
		});

		//Save
		button_save.addActionListener(e ->
		{
			GUI.changeComponentCursor(2, dialog);

			Thresholding.save();
			GUI.destroyGUI();
			dialog.dispose();

			GUI.changeComponentCursor(-1, dialog);
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to invert colours
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.changeComponentCursor(2, dialog);

				Thresholding.reset();
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();

				GUI.changeComponentCursor(-1, dialog);
			}
		});

		dialog.setSize(400, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
