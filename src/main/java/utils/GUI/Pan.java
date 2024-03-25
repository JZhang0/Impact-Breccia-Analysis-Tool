package utils.GUI;

import src.main.java.GUI.GUI;

import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//This class handles all functionalities for panning the image
public class Pan
{
	//This Point contains the offset that we want to add when displaying the current image
	private static Point pan_offset = new Point(0, 0);

	//This Point contains the initial location on the screen that the user pressed when they begin to pan the image
	private static Point initial_position = new Point(0, 0);

	//This Point contains the total combined offset of the previous pans
	//Add the difference between the initial_position and the current location to this Point to get pan_offset
	private static Point buffer_offset = new Point(0, 0);

	//Return the current X axis offset of the pan
	public static int getX()
	{
		return pan_offset.x;
	}

	//Return the current Y axis offset of the pan
	public static int getY()
	{
		return pan_offset.y;
	}

	//Reset the current image's pan
	public static void panReset()
	{
		buffer_offset.x = 0;
		buffer_offset.y = 0;

		pan_offset.x = 0;
		pan_offset.y = 0;

		initial_position.x = 0;
		initial_position.y = 0;

		GUI.render();
	}

	//Add the mouse listeners to main_panel
	public static void addMouseListeners(JPanel main_panel)
	{
		//Starting and stopping a pan
		main_panel.addMouseListener(new MouseAdapter() {
			//Start to pan the image
			@Override
			public void mousePressed(MouseEvent e)
			{
				//When the mouse is pressed, get the location of the press which the current pan will be made relative to
				initial_position = e.getPoint();
			}

			//Stop panning the image
			@Override
			public void mouseReleased(MouseEvent e)
			{
				//When the mouse is released, the pan is over
				//Add the total pan made to the buffer
				buffer_offset.x += e.getX() - initial_position.x;
				buffer_offset.y += e.getY() - initial_position.y;
			}
		});

		//Performing a pan
		main_panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e)
			{
				//Update the pan to render every time the user moves the cursor
				pan_offset.x = e.getX() - initial_position.x + buffer_offset.x;
				pan_offset.y = e.getY() - initial_position.y + buffer_offset.y;

				//Rerender the image to show the pan
				GUI.render();
			}
		});
	}
}
