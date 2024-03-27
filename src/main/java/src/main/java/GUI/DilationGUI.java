package src.main.java.GUI;

import utils.GUI.MainImage;
import utils.Processing.MorphManager;

import org.opencv.imgproc.Imgproc;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class DilationGUI extends JButton
{
	public DilationGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(8)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, MainImage.getImageWidth(), MainImage.getImageHeight());
			
			MainImage.flipColor();
			MorphManager.applyDilation();
			MainImage.flipColor();

			MorphManager.save("d");
			GUI.render(MainImage.getImageMat());
			GUI.destroyGUI();
		}
	}
}
