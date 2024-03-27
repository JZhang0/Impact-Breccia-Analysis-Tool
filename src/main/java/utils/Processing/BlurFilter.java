package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import utils.File.FileIO;
import utils.GUI.MainImage;

/*
* This class defines the behavior for applying a Gaussian blur to an image.
* */
public class BlurFilter
{
	//This is the value by which we apply the Gaussian blur
	private static int gaussian_kernel = 0;
	private static int median_kernel = 0;

	//The kernel value of the Gauss class just holds the index of how big the Gauss blur will be.
	//We are here mapping this index to the actual blur effect.
	//Eg: 1 -> 1x1, 2 -> 3x3, 3 -> 5x5, 4 -> 7x7, etc.
	private static int decodeKernel(int kernel)
	{
		return (kernel * 2) - 1;
	}

	public static Mat GaussianBlur(Mat srcImage, Size ksize){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		Imgproc.GaussianBlur(srcImage, destImage, ksize, 0.0, 0.0);

		return destImage;
	}

	public static Mat MedianBlur(Mat srcImage, int ksize){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}

	//Adjust the Gaussian blur of the image
	public static Mat addGauss(int kernel_value)
	{
		gaussian_kernel = kernel_value;

		if(gaussian_kernel > 0){
			return GaussianBlur(MainImage.getImageMat(), new Size(decodeKernel(gaussian_kernel), decodeKernel(gaussian_kernel)));
		}
		else{
			return MainImage.getImageMat();
		}
	}

	public static Mat addMedian(int kernel_value)
	{
		median_kernel = kernel_value;

		if(median_kernel > 0){
			return MedianBlur(MainImage.getImageMat(), decodeKernel(median_kernel));
		}
		else{
			return MainImage.getImageMat();
		}
	}

	//Get the kernel value
	public static int getGaussianKernel()
	{
		return gaussian_kernel;
	}

	public static int getMedianKernel()
	{
		return median_kernel;
	}

	public static void resetGaussian()
    {
        gaussian_kernel = 0;
    }

	public static void resetMedian()
    {
        median_kernel = 0;
    }

	public static Mat auto_gaussian()
	{
		int ksize = (int) Math.floor((double)MainImage.getImageWidth() / 2000 + (double)MainImage.getImageHeight() / 1000);

		//Adjust the contrast
		return addGauss(ksize);
	}

	public static Mat auto_median()
	{
		int ksize = (int) Math.floor((double)MainImage.getImageWidth() / 2000 + (double)MainImage.getImageHeight() / 1000);

		//Adjust the contrast
		return addMedian(ksize);
	}

	//Save this Gaussian blur to the main image
	public static void save_gaussian()
	{
		MainImage.setImage(addGauss(gaussian_kernel));
		resetGaussian();

		FileIO.export("Gauss");
	}

	public static void save_median()
	{
		MainImage.setImage(addMedian(median_kernel));
		resetMedian();

		FileIO.export("Despeckle");
	}
}
