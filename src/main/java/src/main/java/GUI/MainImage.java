package src.main.java.GUI;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class MainImage
{
	private static Mat image;

	public static void setImage(Mat new_image)
	{
		image = new_image;
	}

	public static byte[] getImageByte()
	{
		return matToByte(image);
	}

	public static Mat getImageMat()
	{
		return image;
	}

	public static byte[] matToByte(Mat image)
	{
		MatOfByte matOfByte = new MatOfByte();
		Imgcodecs.imencode(".jpg", image, matOfByte);
		return matOfByte.toArray();
	}
}
