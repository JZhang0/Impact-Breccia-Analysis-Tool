package utils.GUI;

import org.opencv.core.Mat;

public class AnchorImage {
	//The image itself
	private static Mat image;

	//This blocks access to any filters until an image has been imported into the application
	private static boolean exists = false;

	//Return the application's image as a Mat
	public static Mat getImageMat()
	{
		return image;
	}

	public static void setImageMat(Mat img){
		exists = true;
		image = img;
	}

	//Has there been an image imported into the application yet?
	public static boolean exists()
	{
		return exists;
	}
}
