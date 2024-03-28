package utils.GUI;

import org.opencv.core.Mat;

public class FloodFillImage {
    //The image itself
	private static Mat image;

	//Return the application's image as a Mat
	public static Mat getImageMat()
	{
		return image;
	}

	public static void setImageMat(Mat img){
		image = img;
	}
}
