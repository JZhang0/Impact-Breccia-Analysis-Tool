package utils.File;

import src.main.java.GUI.GaussGUI;
import src.main.java.GUI.MainImage;

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
					case "G": //GAUSS BLUR
						new GaussGUI();
						break;
					case "Y": //REDO
						//System.out.println("Y pressed");
						History.redo();
						break;
					case "Z": //UNDO
						//System.out.println("Z pressed");
						History.undo();
						break;
					default:
						break;
				}
			}

			return false;
		});
	}
}
