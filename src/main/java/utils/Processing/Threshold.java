package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import src.main.java.GUI.MainImage;
import src.main.java.Settings;
import utils.File.FileIO;

//Adjusting the threshold of the image
public class Threshold
{
	//The value by which we adjust the threshold
	private static int thresh = 0;

	//Adjust the threshold of the MainImage
	public static Mat adjustThreshold(int thresh_value)
	{
		Mat new_image = new Mat();
		Imgproc.threshold(MainImage.getImageMat(), new_image, thresh_value, Settings.THRESHOLD_MAX, Imgproc.THRESH_BINARY);
		thresh = thresh_value;

		return new_image;
	}

	//Return the current threshold value
	public static int getThresh()
	{
		return thresh;
	}

	//Save this filter to MainImage's current image
	public static void save()
	{
		MainImage.setImage(adjustThreshold(thresh));
		thresh = 0;

		FileIO.export("Threshold");
	}
}
