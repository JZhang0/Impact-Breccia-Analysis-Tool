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

	// 	Mat sample = Imgcodecs.imread("sample\\2. JLT_Airfall Layer_238px=10mm_trimmed.tif");

	// 	Mat newImage = MedianBlur(sample, 5);

	// 	HighGui.imshow("New Image", newImage);
	// 	HighGui.resizeWindow("New Image", 1200, 1000);
	// 	HighGui.waitKey();

	// 	System.exit(0);
	// }
}
