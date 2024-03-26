package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import src.main.java.GUI.MainImage;
import utils.File.FileIO;

public class Despeckler
{
	//The size of the clusters to remove
	private static int kernel= 1;

	public static Mat despeckle(int new_kernel)
	{
		Mat new_image = new Mat();

		Imgproc.medianBlur(MainImage.getImageMat(), new_image, decodeKernel(new_kernel));
		kernel = new_kernel;

		return new_image;
	}

	public static Mat auto()
	{
		return despeckle(3);
	}

	//Save the image to the MainImage
	public static void save()
	{
		//Update the main image
		MainImage.setImage(despeckle(kernel));

		//Reset parameters
		kernel = 1;

		//Export for history
		FileIO.export("Despeckle");
	}

	public static int getKernel()
	{
		return kernel;
	}

	//The kernel value of the Despeckle class just holds the index of how big the median blur will be.
	//We are here mapping this index to the actual blur effect.
	//Eg: 1 -> 1x1, 2 -> 3x3, 3 -> 5x5, 4 -> 7x7, etc.
	private static int decodeKernel(int kernel)
	{
		return (kernel * 2) - 1;
	}

	public static Mat despeckle(Mat srcImage, int ksize){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}

	public static Mat despeckle(Mat srcImage, int width, int height){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		int ksize = 2 * (width / 2000 + height / 1000) + 1;
        System.out.println("Despeckle ksize: " + ksize);

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}
}