package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import utils.File.FileIO;
import utils.GUI.MainImage;

import java.util.ArrayList;

public class RGB
{
	private static int channel = -1; //The channel that the image has been split to

	//Split the channels to query_channel
	public static Mat split(int query_channel)
	{
		if (query_channel > 2 || query_channel < 0)
			return MainImage.getImageMat();

		ArrayList<Mat> channels = new ArrayList<>(3);
		Core.split(MainImage.getImageMat(), channels);

		channel = query_channel;

		return MatManager.GraytoRGB(channels.get(query_channel));
	}

	public static int getChannel()
	{
		return channel;
	}

	public static Mat auto()
	{
		return split(0);
	}

	//Save the current query_channel as the image for further processing
	public static void save()
	{
		if (channel > -1)
		{
			MainImage.setImage(split(channel));
			MainImage.setSplit(true);

			FileIO.export("RGB", true, false);
		}
	}

	//Reset the internally saved channel
	public static void resetChannel()
	{
		channel = -1;
	}
}
