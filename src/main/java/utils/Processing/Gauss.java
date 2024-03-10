package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;
import src.main.java.GUI.MainImage;
import utils.File.FileIO;

/*
* This class defines the behaviour for applying a Gaussian blur to an image.
* */
public class Gauss
{
	//This is the value by which we apply the Gaussian blur
	private static int kernel = 1;

	//Adjust the Gaussian blur of the image
	public static Mat addGauss(int kernel_value)
	{
		Mat new_image = new Mat();
		Imgproc.GaussianBlur(MainImage.getImageMat(), new_image, new Size(decodeKernel(kernel), decodeKernel(kernel)), 0);

		kernel = kernel_value;

		return new_image;
	}

	//Get the kernel value
	public static int getKernel()
	{
		return kernel;
	}

	//The kernel value of the Gauss class just holds the index of how big the Gauss blur will be.
	//We are here mapping this index to the actual blur effect.
	//Eg: 1 -> 1x1, 2 -> 3x3, 3 -> 5x5, 4 -> 7x7, etc.
	private static int decodeKernel(int kernel)
	{
		return (kernel * 2) - 1;
	}

	//Save this Gaussian blur to the main image
	public static void save()
	{
		MainImage.setImage(addGauss(kernel));
		kernel = 1;

		FileIO.export("Gauss");
	}
}
