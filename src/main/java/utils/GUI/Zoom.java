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
package utils.GUI;

import src.main.java.Settings;
import src.main.java.GUI.GUI;

import javax.swing.JPanel;

//Controls the GUI's zoom functionality
public class Zoom
{
	//The factor that we are zooming by
	private static double zoom_factor = 1.0;

	//Zoom in on the current image
	public static void zoomIn()
	{
		zoom_factor *= Settings.ZOOM_FACTOR;
		GUI.render();
	}

	//Zoom out on the current image
	public static void zoomOut()
	{
		zoom_factor /= Settings.ZOOM_FACTOR;
		GUI.render();
	}

	//Reset the current image's zoom level
	public static void zoomReset()
	{
		zoom_factor = 1.0;
		GUI.render();
	}

	//Calculate the height of the displayed image using this zoom level
	public static double getZoom()
	{
		return zoom_factor;
	}

	//Add the mouse listeners to main_panel
	public static void addMouseListeners(JPanel main_panel)
	{
		main_panel.addMouseWheelListener(e ->
		{
			if (e.getWheelRotation() < 0)
			{
				zoomIn();
			}
			else
			{
				zoomOut();;
			}
		});
	}
}
