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

import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.Processing.Invert;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InvertGUI extends JButton
{
	public InvertGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(3)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			InvertGUI.launch();
		}
	}

	private static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Invert colours", true);
		dialog.setLayout(new GridLayout(1, 1));
		JButton button = new JButton("Confirm");
		dialog.add(button);

		//Render the inverted colours
		GUI.render(Invert.invert());

		//Save
		button.addActionListener(e ->
		{
			GUI.changeComponentCursor(2, dialog);

			Invert.save();
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

				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();

				GUI.changeComponentCursor(-1, dialog);
			}
		});

		dialog.setSize(200, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
