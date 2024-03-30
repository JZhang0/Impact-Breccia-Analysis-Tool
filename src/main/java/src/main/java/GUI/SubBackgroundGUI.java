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

import utils.Processing.BackgroundRemoval;
import utils.File.FileIO;
import utils.GUI.AnchorImage;
import utils.GUI.MainImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SubBackgroundGUI
{
	public static void act()
	{
		if (MainImage.exists() && AnchorImage.exists())
		{
			GUI.createGUI();
			SubBackgroundGUI.launch();
		}
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Subtract background", true);
		dialog.setLayout(new GridLayout(1, 1));
		JButton button = new JButton("Confirm");
		dialog.add(button);

		//Save
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUI.changeCursor(2);

				GUI.destroyGUI();
				dialog.dispose();
                BackgroundRemoval.save();

				GUI.changeCursor(-1);
			}
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to invert colours
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.changeCursor(2);

                AnchorImage.setImageMat(MainImage.getImageMat());
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
				
				FileIO.export("default", false, false);

				GUI.changeCursor(-1);
			}
		});

		dialog.setSize(200, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}