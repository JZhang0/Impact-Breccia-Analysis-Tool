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
import utils.Processing.MorphManager;

import org.opencv.imgproc.Imgproc;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class ErosionGUI extends JButton
{
	public ErosionGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(7)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.changeCursor(2);

			GUI.createGUI();
			MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, MainImage.getImageWidth(), MainImage.getImageHeight());
			
			MainImage.flipColor();
			MorphManager.applyErosion();
			MainImage.flipColor();
			
			MorphManager.save("e");
			GUI.render(MainImage.getImageMat());
			GUI.destroyGUI();

			GUI.changeCursor(-1);
		}
	}
}
