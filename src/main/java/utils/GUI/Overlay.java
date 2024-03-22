package utils.GUI;

import src.main.java.GUI.GUI;
import src.main.java.GUI.MainImage;
import src.main.java.Settings;
import utils.File.History;

import org.opencv.core.Mat;
import org.opencv.core.Core;

public class Overlay
{
	//Overlay the previous image onto the current one
	public static void primary()
	{
		GUI.render(averageImages(MainImage.getImageMat(), History.getPreviousImage(2), Settings.OVERLAY_CURRENT_IMAGE_WEIGHT, 100 - Settings.OVERLAY_CURRENT_IMAGE_WEIGHT));
	}

	//Overlay the first image onto the current one
	public static void secondary()
	{
		GUI.render(averageImages(MainImage.getImageMat(), History.getOriginalImage(), Settings.OVERLAY_CURRENT_IMAGE_WEIGHT, 100 - Settings.OVERLAY_CURRENT_IMAGE_WEIGHT));
	}

	//Create an overlay of the two supplied images
	public static Mat averageImages(Mat img1, Mat img2, int weight1, int weight2)
	{
		//Scale down the weights
		double scale = 1.0 / (weight1 + weight2);
		double scaledWeight1 = weight1 * scale;
		double scaledWeight2 = weight2 * scale;

		//Create a new Mat to hold the overlay image
		Mat result = new Mat();

		//Overlay the two images onto each other
		Core.addWeighted(img1, scaledWeight1, img2, scaledWeight2, 0, result);

		return result;
	}
}
