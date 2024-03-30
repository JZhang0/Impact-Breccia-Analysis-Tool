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
package utils.GUI;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.File.FileIO;
import utils.Processing.MatManager;

/*
* This class represents the image that is currently being processed by the application
* */
public class MainImage
{
	//The filename of the image
	private static String filename = "";

	//The image itself
	private static Mat image;

	//This blocks access to any filters until an image has been imported into the application
	private static boolean exists = false, doneThreshold = false, doneSplit = false;

	//Update the image being processed by the application to be new_image
	public static void setImage(Mat new_image)
	{

		image = new_image;
		exists = true;
	}

	public static void flipColor(){
		image = MatManager.flipColor(image);
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
	// Return the application's image width
	public static int getImageWidth()
	{
		return image.cols();
	}
	// Return the application's image height
	public static int getImageHeight()
	{
		return image.rows();
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

	public static boolean checkGray(){
		return doneSplit;
	}

	public static void setSplit(boolean bool){
		doneSplit = bool;
	}

	public static boolean checkBinary(){
		return doneSplit && doneThreshold;
	}

	public static void setThreshold(boolean bool){
		doneThreshold = bool;
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
}
