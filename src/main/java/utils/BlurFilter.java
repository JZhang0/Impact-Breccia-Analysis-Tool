package utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
// import org.opencv.highgui.HighGui;
// import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class BlurFilter
{
	public static Mat GaussianBlur(Mat srcImage, Size ksize, double sigmaX, double sigmaY){
		Mat destImage = Mat.zeros(srcImage.size(), srcImage.type());

		Imgproc.GaussianBlur(srcImage, destImage, ksize, sigmaX, sigmaY);

		return destImage;
	}

	public static Mat MedianBlur(Mat srcImage, int ksize){
		Mat destImage = Mat.zeros(srcImage.size(), srcImage.type());

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}

	// public static void main(String[] args) {
	// 	nu.pattern.OpenCV.loadLocally();

	// 	Mat sample, edges, dest;

	// 	sample = Imgcodecs.imread("sample\\2. JLT_Lower Unit_240px=10mm_trimmed.tif");

	// 	edges = EdgeDetection.CannyEdgeDetection(sample, 50, 125, 3, true);

	// 	dest = EdgeDetection.EdgeOverlay(sample, edges);

	// 	HighGui.imshow("edges", edges);
	// 	HighGui.resizeWindow("edges", 800, 600);
	// 	HighGui.imshow("sample", dest);
	// 	HighGui.resizeWindow("sample", 800, 600);
	// 	HighGui.waitKey();

	// 	System.exit(0);
	// }
}
