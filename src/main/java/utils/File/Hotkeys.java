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
