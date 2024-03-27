package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

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
	 * Resize srcImage1 to the same as srcImage2.
	 * 
	 * @param srcImage1 Image to be resized.
	 * @param srcImage2 Image from which the size is copied from.
	 */
	public static Mat resize(Mat srcImage1, Mat srcImage2){
		Mat destImage = createMatWithProperty(srcImage1);

		Imgproc.resize(srcImage1, destImage, srcImage2.size());

		return destImage;
	}

	/**
	 * Flips the color of pixel values in srcImage.
	 * @param srcImage
	 * @return
	 */
	public static Mat flipColor(Mat srcImage){
		Mat destImage = createMatWithProperty(srcImage);

		Core.bitwise_not(srcImage, destImage);

		return destImage;
	}

	public static Mat recolor(Mat srcImage, Scalar colorLow, Scalar colorHigh, Scalar newColor){
		Mat destImage = createMatWithProperty(srcImage);
		Mat mask = new Mat();

		Core.inRange(srcImage, colorLow, colorHigh, mask);

		destImage.setTo(newColor, mask);

		Core.bitwise_not(mask, mask);
		srcImage.copyTo(destImage, mask);

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

	public static Mat RGBtoHSV(Mat srcImage){
		Mat destImage = createMatWithProperty(srcImage);

		Imgproc.cvtColor(srcImage, destImage, Imgproc.COLOR_RGB2HSV);

		return destImage;
	}

	public static Mat HSVtoRGB(Mat srcImage){
		Mat destImage = createMatWithProperty(srcImage);

		Imgproc.cvtColor(srcImage, destImage, Imgproc.COLOR_HSV2RGB);

		return destImage;
	}

	public static Mat floodFill(Mat srcImage, Point seedPoint, Scalar newVal){
		Imgproc.floodFill(srcImage, new Mat(), seedPoint, newVal);

		return srcImage;
	}

	public static Mat overlay(Mat srcImage1, Mat srcImage2){
		return overlayWeighted(srcImage1, 1.0, srcImage2, 1.0);
	}

	/**
	 * Calculate the weighted sum of two images as follows: dst(I)=saturate(srcImage1(I) * weight1 + srcImage2(I) ∗ weight2 + gamma).<p>
	 * The weight value ranging from 0.0 - 1.0 indicates the level of transparancy desired, the lower the more transparent.
	 * 
	 * @param srcImage1 First input image.
	 * @param weight1 Weight of the first image elements.
	 * @param srcImage2 Second input image of the same size and channel number as src1.
	 * @param weight2 Weight of the second image elements.
	 * @param gamma Scalar added to each sum.
	 * @return Output image that has the same size and number of channels as the input images, and equals to srcImage1 * weight1 + srcImage2 ∗ weight2 + gamma.
	 */
	public static Mat overlayWeighted(Mat srcImage1, double weight1, Mat srcImage2, double weight2){
		srcImage2 = resize(srcImage2, srcImage1);

		Mat destImage = createMat();

		Core.addWeighted(srcImage1, weight1, srcImage2, weight2, 1.0, destImage);

		return destImage;
	}

	public static BufferedImage matToImage(Mat mat)
	{
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( mat.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = mat.channels() * mat.cols() * mat.rows();
		byte [] b = new byte[bufferSize];
		mat .get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);  
		return image;
	}
}
