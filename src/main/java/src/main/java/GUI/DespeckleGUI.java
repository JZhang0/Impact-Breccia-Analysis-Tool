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
import utils.Processing.BlurFilter;

import javax.swing.JFrame;
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

public class DespeckleGUI extends JButton
{
	private static JLabel label;
	private static JSlider slider;
	private static JTextField text_field;
	private static JButton button_auto, button_save;

	private static int despeckle_adjustment_value;

	private static boolean getSetting = false;

	private static void getDespeckleSetting(){
		getSetting = true;
		despeckle_adjustment_value = BlurFilter.getMedianKernel();

		if(despeckle_adjustment_value == 0){
			text_field.setText(String.valueOf(despeckle_adjustment_value));
		}
		else{
			text_field.setText(String.valueOf(despeckle_adjustment_value * 2 - 1));
		}
		slider.setValue(despeckle_adjustment_value);

		GUI.render(BlurFilter.addMedian(despeckle_adjustment_value));
		getSetting = false;
	}

	public DespeckleGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(6)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			launch();
		}
	}



	public static void launch()
	{
		JFrame frame = GUI.getFrame();
		JDialog dialog = new JDialog(frame, "Despeckle", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		//Label
		label = new JLabel("Despeckler size ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(label, constraints);

		//Slider
		slider = new JSlider(Settings.DESPECKLE_MIN, Settings.DESPECKLE_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		dialog.add(slider, constraints);

		//Textfield
		text_field = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(text_field, constraints);

		//Automate button
		button_auto = new JButton("Automate");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_auto, constraints);

		//Confirmation button
		button_save = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);

		//Set defaults
		getDespeckleSetting();

		//Updating the slider
		slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
				despeckle_adjustment_value = slider.getValue();

				if(despeckle_adjustment_value == 0){
					text_field.setText(String.valueOf(despeckle_adjustment_value));
				}
				else{
					text_field.setText(String.valueOf(despeckle_adjustment_value * 2 - 1));
				}

				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting() && !getSetting){
					GUI.render(BlurFilter.addMedian(despeckle_adjustment_value));
				}
			}
        });

		//Override the documentListener so we can control what happens when the textfield changes
		text_field.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                int ksize = Integer.parseInt(text_field.getText()) > 0 ? (Integer.parseInt(text_field.getText()) + 1) / 2 : 0;
				if (text_field.getText().matches("\\d+") && ksize >= Settings.DESPECKLE_MIN && ksize <= Settings.DESPECKLE_MAX)
				{
					slider.setValue(ksize);
				}
            }
        });

		//Automate
		button_auto.addActionListener(e ->
		{
			GUI.changeComponentCursor(2, dialog);

			BlurFilter.auto_median();
			getDespeckleSetting();

			GUI.changeComponentCursor(-1, dialog);
		});

		//Save
		button_save.addActionListener(e ->
		{
			GUI.changeComponentCursor(2, dialog);

			BlurFilter.save_median();
			GUI.destroyGUI();
			dialog.dispose();

			GUI.changeComponentCursor(-1, dialog);
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to despeckle
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.changeComponentCursor(2, dialog);

				BlurFilter.resetMedian();
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();

				GUI.changeComponentCursor(-1, dialog);
			}
		});

		dialog.setSize(500, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
