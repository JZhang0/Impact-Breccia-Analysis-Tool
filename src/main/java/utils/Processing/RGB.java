package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import src.main.java.GUI.MainImage;
import utils.File.FileIO;

import java.util.ArrayList;

public class RGB
{
	private static int channel = -1; //The channel that the image has been split to
	private static boolean updated = false; //Has the image already had its channel's split?

	//Split the channels to query_channel
	public static Mat split(int query_channel)
	{
		if (query_channel > 2 || query_channel < 0 || updated)
			return MainImage.getImageMat();

		ArrayList<Mat> channels = new ArrayList<>(3);
		Core.split(MainImage.getImageMat(), channels);

		channel = query_channel;

		return MatManager.GraytoRGB(channels.get(query_channel));
	}

	//Save the current query_channel as the image for further processing
	public static void save()
	{
		if (channel > -1)
		{
			MainImage.setImage(split(channel));
			updated = true;

			FileIO.export("RGB");
		}
	}

	//Reset the internally saved channel
	public static void resetChannel()
	{
		channel = -1;
	}
}
