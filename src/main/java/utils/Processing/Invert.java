package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import src.main.java.GUI.MainImage;
import utils.File.FileIO;

/*
* This class controls the behaviour for inverting the colour of an image.
* */
public class Invert
{
	//Invert the colours of the image
	public static Mat invert()
	{
		Mat new_image = new Mat();
		Core.bitwise_not(MainImage.getImageMat(), new_image);

		return new_image;
	}

	//Save the inverted colour scheme to the image permanently
	public static void save()
	{
		MainImage.setImage(invert());

		FileIO.export("Invert");
	}
}
