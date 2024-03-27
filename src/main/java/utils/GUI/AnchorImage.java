package utils.GUI;

import org.opencv.core.Mat;

import utils.File.FileIO;

public class AnchorImage {
	//The image itself
	private static Mat image;

	//This blocks access to any filters until an image has been imported into the application
	private static boolean exists = false;

    public static void getFromMain(){
        image = MainImage.getImageMat();
        exists = true;
        FileIO.saveFile("sample\\out\\anchor.png", image, "png", 9);
    }

	//Return the application's image as a Mat
	public static Mat getImageMat()
	{
		return image;
	}

	//Has there been an image imported into the application yet?
	public static boolean exists()
	{
		return exists;
	}
}
