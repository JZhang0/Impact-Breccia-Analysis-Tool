package utils.GUI;

import src.main.java.GUI.GUI;
import src.main.java.GUI.ToggleImage;
import utils.Definition.ColorBGRValue;
import utils.File.FileIO;
import utils.Processing.MatManager;

import javax.sound.midi.MidiChannel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;

import org.opencv.core.Scalar;

public class ClickFloodFill {
    private static boolean isEdited = false;
    private static Scalar color = ColorBGRValue.BGR_WHITE;

	//This Point contains the initial location on the screen that the user pressed
	private static Point initial_position = new Point(0, 0);

    //This Point contains the relative location of the mouse cursor on the image
    private static Point relative_position = new Point(0, 0);

    public static void setMode(Scalar newColor){
        color = newColor;
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
				if (SwingUtilities.isLeftMouseButton(e))
				{
					initial_position = e.getPoint();
				}
			}

			//Stop panning the image
			@Override
			public void mouseReleased(MouseEvent e)
			{
				//When the mouse is released, the pan is over
				//Add the total pan made to the buffer
				if (SwingUtilities.isLeftMouseButton(e))
				{
                    if(!GUI.isEditing(-1) && e.getPoint().equals(initial_position)){
                        Point image_anchor_topLeft = GUI.getImageTopLeft();

                        Point mouse_screen_location = MouseInfo.getPointerInfo().getLocation();
                        Point panel_screen_location = main_panel.getLocationOnScreen();
                        relative_position = new Point(mouse_screen_location.x - panel_screen_location.x - image_anchor_topLeft.x, mouse_screen_location.y - panel_screen_location.y - image_anchor_topLeft.y);

                        double render_scale = GUI.getImageRenderScale();
                        relative_position.x = (int) (relative_position.x / render_scale);
                        relative_position.y = (int) (relative_position.y / render_scale);

                        if(relative_position.x >= 0 && relative_position.x <= MainImage.getImageWidth() && relative_position.y >= 0 && relative_position.y <= MainImage.getImageHeight()){
                            System.out.println("FloodFill position on actual image: " + relative_position.x + ", " + relative_position.y);
                            FloodFillImage.setImageMat(MatManager.floodFill(FloodFillImage.getImageMat(), relative_position.x, relative_position.y, color));
							ToggleImage.reset();
                            GUI.render(FloodFillImage.getImageMat());
                            isEdited = true;
                        }
                    }
				}
				else if (SwingUtilities.isMiddleMouseButton(e))
				{
					Zoom.zoomReset();
					Pan.panReset();
				}
			}
		});
	}

    public static void save(String operation){
        MainImage.setImage(FloodFillImage.getImageMat());
        GUI.render(MainImage.getImageMat());

        if(isEdited){
            FileIO.export(operation, false, false);
            isEdited = false;
        }
    }
}
