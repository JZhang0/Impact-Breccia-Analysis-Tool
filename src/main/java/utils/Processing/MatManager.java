package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class MatManager {
    
	public static Mat createMat(){
		return new Mat();
	}

	/**
	 * Create a Mat object with the same dimension and type as srcImage.
	 * 
	 * @param srcImage The source image from which the properties get copied.
	 */
	public static Mat createMatWithProperty(Mat srcImage){
		return new Mat(srcImage.size(), srcImage.type());
	}

	/**
	 * Resizing srcImage1 to the same as srcImage2.
	 * 
	 * @param srcImage1 Image to be resized.
	 * @param srcImage2 Image from which the size is copied from.
	 */
	public static Mat resize(Mat srcImage1, Mat srcImage2){
		Mat destImage = createMatWithProperty(srcImage1);

		Imgproc.resize(srcImage1, destImage, srcImage2.size());

		return destImage;
	}

	public static Mat recolor(Mat srcImage, Scalar colorLow, Scalar colorHigh, Scalar newColor){
		Mat destImage = createMatWithProperty(srcImage);
		Mat mask = createMatWithProperty(srcImage);

		Core.inRange(srcImage, colorLow, colorHigh, mask);

		destImage.setTo(newColor, mask);

		return destImage;
	}

    public static Mat RGBtoGray(Mat srcImage){
		Mat destImage = createMatWithProperty(srcImage);

		Imgproc.cvtColor(srcImage, destImage, Imgproc.COLOR_RGB2GRAY);

		return destImage;
	}

	public static Mat GraytoRGB(Mat srcImage){
		Mat destImage = createMatWithProperty(srcImage);

		Imgproc.cvtColor(srcImage, destImage, Imgproc.COLOR_GRAY2RGB);

		return destImage;
	}

	public static Mat overlay(Mat srcImage1, Mat srcImage2){
		return overlayWeighted(srcImage1, 1.0, srcImage2, 1.0);
	}

	/**
	 * Calculates the weighted sum of two images as follows: dst(I)=saturate(srcImage1(I) * weight1 + srcImage2(I) ∗ weight2 + gamma).<p>
	 * The weight value ranging from 0.0 - 1.0 indicates the level of transparancy desired, the lower the more transparent.
	 * 
	 * @param srcImage1 First input image.
	 * @param weight1 Weight of the first image elements.
	 * @param srcImage2 Second input image of the same size and channel number as src1.
	 * @param weight2 Weight of the second image elements.
	 * @return Output image that has the same size and number of channels as the input images, and equals to srcImage1 * weight1 + srcImage2 ∗ weight2 + gamma.
	 */
	public static Mat overlayWeighted(Mat srcImage1, double weight1, Mat srcImage2, double weight2){
		srcImage2 = resize(srcImage2, srcImage1);

		Mat destImage = createMat();

		Core.addWeighted(srcImage1, weight1, srcImage2, weight2, 1.0, destImage);

		return destImage;
	}
}
