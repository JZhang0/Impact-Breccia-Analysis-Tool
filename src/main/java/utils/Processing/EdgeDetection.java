package utils.Processing;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class EdgeDetection {

	private static double medianMat(Mat srcImage){
        Scalar mean = Core.mean(srcImage);

		return mean.val[0];
    }

    /**
	 * Find edges in an image using the Canny algorithm. The smallest value between minVal and maxVal is used for edge linking.
	 * The largest value is used to find initial segments of strong edges.<p>
	 * 
	 * We need two threshold values, minVal and maxVal. Any edges with intensity gradient more than maxVal are sure to be edges and those 
	 * below minVal are sure to be non-edges, so discarded. Those who lie between these two thresholds are classified edges or non-edges 
	 * based on their connectivity. If they are connected to "sure-edge" pixels, they are considered to be part of edges. Otherwise, they 
	 * are also discarded. <p>
	 * 
	 * See https://docs.opencv.org/4.x/da/d22/tutorial_py_canny.html for details.
	 * @param srcImage 8-bit grayscale input image.
	 * @param minVal minimum intensity gradient for an edge to be considered and edge.
	 * @param maxVal any edge with intensity gradient above this value will be considered sure edge, recommended to be 3 times minVal.
	 * @param apertureSize aperture size for the Sobel operator.
	 * @param L2gradient a flag, indicating whether a more accurate norm should be used to calculate the image gradient magnitude.
	 * @return output edge map; single channels 8-bit image, which has the same size as image.
	 */
	public static Mat cannyEdgeDetection(Mat srcImage, double minVal, double maxVal, int apertureSize, boolean L2gradient, Scalar edgeColor){
		Mat edges = MatManager.createMatWithProperty(srcImage);

		Imgproc.Canny(srcImage, edges, minVal, maxVal, apertureSize, L2gradient);

		edges = MatManager.GraytoRGB(edges);
		edges.setTo(edgeColor, edges);

		return edges;
	}

	public static Mat autoCannyEdgeDetection(Mat srcImage, int apertureSize, boolean L2gradient, Scalar edgeColor){
		Mat edges = MatManager.createMatWithProperty(srcImage);

		double mean = medianMat(srcImage);
		int lower = (int)(Math.max(0, 0.7 * mean));
		int upper = (int)(Math.min(255, 1.6 * mean));
		System.out.println("EdgeDetection | lower: " + lower + ", upper: " + upper);

		Imgproc.Canny(srcImage, edges, lower, upper, apertureSize, L2gradient);

		edges = MatManager.GraytoRGB(edges);
		edges.setTo(edgeColor, edges);

		return edges;
	}
}
