package utils.GUI;

import src.main.java.GUI.GUI;
import utils.Definition.ColorBGRValue;
import utils.Processing.EdgeDetection;
import utils.Processing.MatManager;
import utils.Processing.MorphManager;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Toggle
{
	//Overlay the previous image onto the current one
	public static void render_anchor_image()
	{
		if(AnchorImage.exists())
			GUI.render(AnchorImage.getImageMat());
	}

	//Overlay the first image onto the current one
	public static boolean render_edge_overlay()
	{
		if(MainImage.checkGray()){
			MorphManager mm = MorphManager.getInstance();
			MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, MainImage.getImageWidth(), MainImage.getImageHeight());

			GUI.createGUI();
			Mat edges = EdgeDetection.autoCannyEdgeDetection(MainImage.getImageMat(), 3, true, ColorBGRValue.BGR_RED);
			edges = mm.dilation(edges, 1);
			
			Mat edge_overlay = MatManager.overlay(AnchorImage.getImageMat(), edges);
			GUI.render(edge_overlay);
			GUI.destroyGUI();

			return true;
		}
		else{
			return false;
		}
	}
}
