package src.main.java.GUI;

import utils.GUI.MainImage;
import utils.GUI.Toggle;

public class ToggleImage
{
	//What type of overlay is currently being displayed?
	//0 - no overlay
	//1 - main overlay (most recent image)
	//2 - edge overlay (oldest image using this crop)
	public static int overlay_type = 0;

	//Overlay the previous image onto the current one
	public static void toggle_main_image()
	{
		if (MainImage.exists())
		{
			if (overlay_type != 1)
			{
				overlay_type = 1;
				Toggle.render_anchor_image();
			}
			else
			{
				reset();
			}
		}
	}

	//Overlay the oldest image onto the current image
	public static void toggle_edge_overlay()
	{
		if (MainImage.exists())
		{
			if (overlay_type != 2)
			{
				if(Toggle.render_edge_overlay()){
					overlay_type = 2;
				}
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
			overlay_type = 0;
			GUI.render(MainImage.getImageMat());
		}
	}
}
