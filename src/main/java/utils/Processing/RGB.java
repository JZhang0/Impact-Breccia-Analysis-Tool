/*
 * Copyright (C) 2024 Yifei Zhang, Nicolas Louis Jacobs, Yuhan Zhang - All Rights Reserved
* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
