package src.main.java.GUI;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import utils.File.FileIO;

/*
* This class represents the image that is currently being processed by the application
* */
public class MainImage
{
	//The filename of the image
	private static String filename = "";

	//The timestamp of when the image was first imported into the application
	private static long timestamp = 0;

	//The image itself
	private static Mat image;

	//This blocks access to any filters until an image has been imported into the application
	private static boolean exists = false;

	//Update the image being processed by the application to be new_image
	public static void setImage(Mat new_image)
	{
		image = new_image;
		exists = true;
	}

	//Return the application's image as a byte array
	public static byte[] getImageByte()
	{
		return matToByte(image);
	}

	//Return the application's image as a Mat
	public static Mat getImageMat()
	{
		return image;
	}

	//Convert a Mat into a byte array
	public static byte[] matToByte(Mat image)
	{
		MatOfByte matOfByte = new MatOfByte();
		Imgcodecs.imencode(".jpg", image, matOfByte);
		return matOfByte.toArray();
	}

	//Has there been an image imported into the application yet?
	public static boolean exists()
	{
		return exists;
	}

	//Return the filename of the image
	public static String getFilename()
	{
		return filename;
	}

	//Set the filename of the image
	public static void setFilename(String new_filename)
	{
		filename = new_filename;
	}

	//Get the timestamp of when the image was imported into the application
	public static long getTimestamp()
	{
		return timestamp;
	}

	//Set the image's timestamp
	public static void setTimestamp(long new_timestamp)
	{
		timestamp = new_timestamp;
	}
}
