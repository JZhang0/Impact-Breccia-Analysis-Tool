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
package utils.File;

import src.main.java.GUI.*;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Hotkeys
{
	public Hotkeys()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
			if (e.getID() == KeyEvent.KEY_PRESSED)
			{
				switch (KeyEvent.getKeyText(e.getKeyCode()))
				{
					case "P": //AUTOMATE
						AutomateGUI.act();
						break;
					case "C": //CONTRAST
						ContrastGUI.act();
						break;
					case "K": //DESPECKLE
						DespeckleGUI.act();
						break;
					case "G": //GAUSS BLUR
						GaussGUI.act();
						break;
					case "D": //Dilation
						DilationGUI.act();
						break;
					case "I": //INVERT
						InvertGUI.act();
						break;
					case "E": //EROSION
						ErosionGUI.act();
						break;
					case "R": //RGB CHANNEL SPLITTER
						RGBGUI.act();
						break;
					case "S": //SAVE
						SaveGUI.act();
						break;
					case "T": //THRESHOLD
						ThresholdGUI.act();
						break;
					case "H": //SUBTRACT BACKGROUND
						HelpGUI.act();
						break;
					case "X": //REDO
						RedoGUI.act();
						break;
					case "Z": //UNDO
						UndoGUI.act();
						break;
					case "Tab": //PRIMARY OVERLAY
						ToggleImage.toggle_main_image();
						break;
					case "Shift": //SECONDARY OVERLAY
						ToggleImage.toggle_edge_overlay();
						break;
					case "Minus": //ZOOM OUT
						ZoomOutGUI.act();
						break;
					case "Equals": //ZOOM IN
						ZoomInGUI.act();
						break;
					case "0": //RESET PAN AND ZOOM
						ResetGUI.act();
						break;
					default:
						break;
				}
			}

			return false;
		});
	}
}
