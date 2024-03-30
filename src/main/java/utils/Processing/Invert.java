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

import utils.File.FileIO;
import utils.GUI.MainImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;

/*
* This class controls the behaviour for inverting the colour of an image.
* */
public class Invert
{
	//Invert the colours of the image
	public static Mat invert()
	{
		Mat new_image = new Mat();
		Core.bitwise_not(MainImage.getImageMat(), new_image);

		return new_image;
	}

	//Save the inverted colour scheme to the image permanently
	public static void save()
	{
		MainImage.setImage(invert());

		FileIO.export("Invert", false, false);
	}
}
