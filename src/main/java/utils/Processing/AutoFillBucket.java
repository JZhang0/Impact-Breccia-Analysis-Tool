package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import src.main.java.GUI.MainImage;

public class AutoFillBucket
{
	private static Mat kernel;

	public static void updateKernel(int elementType, int width, int height)
	{
		/*m_elementType = elementType;
		m_kernelSize = width / 2000 + height / 1000;
		System.out.println("Morph m_kernelSize: " + m_kernelSize);
		m_kernel = Imgproc.getStructuringElement(m_elementType, new Size(2 * m_kernelSize + 1, 2 * m_kernelSize + 1), new Point(m_kernelSize, m_kernelSize));
	*/}

	/**
	 * Dilation followed by Erosion. It is useful in closing small holes inside the foreground objects.<p>
	 *
	 * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
	 * @param iterations Numer of operations to apply
	 */
	public static Mat fill(int iterations)
	{
		Mat new_image = new Mat();

		Imgproc.morphologyEx(MainImage.getImageMat(), new_image, Imgproc.MORPH_CLOSE, kernel, new Point(-1, -1), iterations);

		return new_image;
	}
}
