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

import utils.Definition.ColorBGRValue;
import utils.File.IconLocator;
import utils.GUI.ClickFloodFill;
import utils.GUI.FloodFillImage;
import utils.GUI.MainImage;

public class EraserGUI extends JButton
{
	public EraserGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(19)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && MainImage.checkBinary() && GUI.isEditing(-1))
		{
			GUI.createGUI();
			GUI.setEditing(0);
			GUI.changeCursor(0);
			ClickFloodFill.setMode(ColorBGRValue.BGR_WHITE);
			FloodFillImage.setImageMat(MainImage.getImageMat());

			System.out.println("magic eraser tool has been launched");
		}
		else if(GUI.isEditing(0)){
			GUI.destroyGUI();
			GUI.setEditing(-1);
			GUI.changeCursor(-1);
			ClickFloodFill.save("eraser");
			ToggleImage.reset();

			System.out.println("magic eraser tool has been closed");
		}
	}
}
