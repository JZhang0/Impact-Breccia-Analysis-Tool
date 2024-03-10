package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class BlurFilter
{
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
}
