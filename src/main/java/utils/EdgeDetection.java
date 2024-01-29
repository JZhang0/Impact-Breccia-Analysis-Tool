package utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class EdgeDetection {

    /**
	 * Finds edges in an image using the Canny algorithm. The smallest value between threshold1 and threshold2 is used for edge linking.
	 * The largest value is used to find initial segments of strong edges.
	 * See https://docs.opencv.org/4.x/da/d22/tutorial_py_canny.html for details.
	 * @param srcImage 8-bit input image.
	 * @param threshold1 first threshold for the hysteresis procedure.
	 * @param threshold2 second threshold for the hysteresis procedure.
	 * @param apertureSize aperture size for the Sobel operator.
	 * @param L2gradient a flag, indicating whether a more accurate norm should be used to calculate the image gradient magnitude.
	 * @return output edge map; single channels 8-bit image, which has the same size as image.
	 */
	public static Mat CannyEdgeDetection(Mat srcImage, double threshold1, double threshold2, int apertureSize, boolean L2gradient){
		Mat destImage = Mat.zeros(srcImage.size(), srcImage.type());

		Imgproc.Canny(srcImage, destImage, threshold1, threshold2, apertureSize, L2gradient);;

		return destImage;
	}

	public static Mat EdgeOverlay(Mat srcImage, Mat edges){
		Mat destImage = Mat.zeros(srcImage.size(), srcImage.type());

		Imgproc.cvtColor(edges, edges, Imgproc.COLOR_GRAY2RGB);

		Core.add(srcImage, edges, destImage);

		destImage.setTo(new Scalar(0, 0, 255), edges);

		return destImage;
	}
}
