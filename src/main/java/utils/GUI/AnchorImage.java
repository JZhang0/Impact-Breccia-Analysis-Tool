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

public class AnchorImage {
	//The image itself
	private static Mat image;

	//This blocks access to any filters until an image has been imported into the application
	private static boolean exists = false, background_subtracted = false;

	//Return the application's image as a Mat
	public static Mat getImageMat()
	{
		return image;
	}

	public static void setImageMat(Mat img){
		exists = true;
		image = img;
	}

	//Has there been an image imported into the application yet?
	public static boolean exists()
	{
		return exists;
	}

	public static void subBacground(boolean bool){
		background_subtracted = bool;
	}

	public static boolean isBackgroundRemoved(){
		return background_subtracted;
	}
}
