package utils.Processing;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import utils.File.FileIO;


public class BlurFilter
{
	public static Mat adjustBlur(Mat image)
	{
		return GaussianBlur(image, null, 5, 5);
	}

	public static Mat GaussianBlur(Mat srcImage, Size ksize, double sigmaX, double sigmaY){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		Imgproc.GaussianBlur(srcImage, destImage, ksize, sigmaX, sigmaY);

		return destImage;
	}

	public static Mat MedianBlur(Mat srcImage, int ksize){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}

	/*public static void main(String[] args) {
		nu.pattern.OpenCV.loadLocally();

		Mat sample, edges, dest;

		sample = FileIO.readFile("sample\\Impact Rocks\\MI_CC_08 - Melt-Poor Breccia\\MI_CC_08_trimmed_scaled_474px=20mm.tif");

		edges = EdgeDetection.CannyEdgeDetection(sample, 250, 500, 3, true, new Scalar(255, 255, 255));
		HighGui.imshow("edges", edges.clone());
		HighGui.resizeWindow("edges", 800, 600);
		
		edges = MatManager.recolor(edges, new Scalar(255, 255, 255), new Scalar(255, 255, 255), new Scalar(0, 0, 255));
		dest = MatManager.overlay(sample, edges);
		// FileIO.saveFile("sample\\sample.tif", dest);
		HighGui.imshow("sample", dest.clone());
		HighGui.resizeWindow("sample", 800, 600);
		HighGui.waitKey();

		System.exit(0);
	}*/
}
