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

import javax.swing.*;

import utils.File.IconLocator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpGUI extends JButton
{
	public HelpGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(4)));
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
		HelpGUI.launch();
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Helpful Information", true);
		dialog.setLayout(new GridLayout(1, 1));

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.WHITE);

		String[] keyActions = {
            "P: AUTOMATE",
            "C: CONTRAST",
            "K: DESPECKLE",
            "G: GAUSS BLUR",
            "D: DILATION",
            "I: INVERT",
            "E: EROSION",
            "R: RGB CHANNEL SPLITTER",
            "S: SAVE",
            "T: THRESHOLD",
            "H: HELP",
            "X: REDO",
            "Z: UNDO",
            "Tab: PRIMARY OVERLAY",
            "Shift: SECONDARY OVERLAY",
            "Minus: ZOOM OUT",
            "Equals: ZOOM IN",
            "0: RESET PAN AND ZOOM"
        };

        for (String keyAction : keyActions) {
            panel.add(new JLabel(keyAction));
        }

		dialog.add(panel);
		dialog.setSize(300, 800);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
