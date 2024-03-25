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
					case "A": //AUTOMATE
						AutomateGUI.act();
						break;
					case "B": //MANUAL FILL BUCKET
						FillBucketGUI.act();
						break;
					case "C": //CONTRAST
						ContrastGUI.act();
						break;
					case "D": //DESPECKLE
						DespeckleGUI.act();
						break;
					case "E": //MANUAL ERASER
						EraserGUI.act();
						break;
					case "G": //GAUSS BLUR
						GaussGUI.act();
						break;
					case "H": //AUTO HOLE FILLER
						AutoFillBucketGUI.act();
						break;
					case "I": //INVERT
						InvertGUI.act();
						break;
					case "K": //CROP
						CropGUI.act();
						break;
					case "O": //OUTLIERS
						OutliersGUI.act();
						break;
					case "P": //PENCIL
						PencilGUI.act();
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
					case "U": //SUBTRACT BACKGROUND
						SubBackgroundGUI.act();
						break;
					case "V": //ERASER BUCKET
						EraserBucketGUI.act();
						break;
					case "Y": //REDO
						RedoGUI.act();
						break;
					case "Z": //UNDO
						UndoGUI.act();
						break;
					case "Tab": //PRIMARY OVERLAY
						OverlayGUI.primary();
						break;
					case "Shift": //SECONDARY OVERLAY
						OverlayGUI.secondary();
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
