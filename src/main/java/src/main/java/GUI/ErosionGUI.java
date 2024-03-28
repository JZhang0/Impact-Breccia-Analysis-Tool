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
