package src.main.java.GUI;

import utils.GUI.Overlay;

public class OverlayGUI
{
	//What type of overlay is currently being displayed?
	//0 - no overlay
	//1 - primary overlay (most recent image)
	//2 - secondary overlay (oldest image using this crop)
	public static int overlay_type = 0;

	//Overlay the previous image onto the current one
	public static void primary()
	{
		if (MainImage.exists())
		{
			if (overlay_type != 1)
			{
				System.out.println("Primary overlay has been enabled.");
				overlay_type = 1;
				Overlay.primary();
			}
			else
			{
				reset();
			}
		}
	}

	//Overlay the oldest image onto the current image
	public static void secondary()
	{
		if (MainImage.exists())
		{
			if (overlay_type != 2)
			{
				System.out.println("Secondary overlay has been enabled.");
				overlay_type = 2;
				Overlay.secondary();
			}
			else
			{
				reset();
			}
		}
	}

	//Remove the overlay image and render the regular one instead
	public static void reset()
	{
		if (MainImage.exists() && overlay_type != 0)
		{
			System.out.println("Overlay has been disabled.");
			overlay_type = 0;
			GUI.render(MainImage.getImageMat());
		}
	}
}
