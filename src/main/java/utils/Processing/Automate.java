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

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Automate
{
	private static Mat original_image, contrast_image, gray_image, threshold_image, despeckle_image, morph_image;

	public static void setImage(Mat image){
		original_image = image;
	}

	public static void run(int mode)
	{
		MorphManager mm = MorphManager.getInstance();
		MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, MainImage.getImageMat().rows(), MainImage.getImageMat().cols());

        contrast_image = Contrast.autoContrast(original_image, 5.0);

		// Three channel gray image
        gray_image = MatManager.RGBtoGray(contrast_image);
		gray_image = MatManager.GraytoRGB(gray_image);

		MainImage.setSplit(true);

		if(mode == 1){
			threshold_image = Thresholding.autoGrayThreshold_dark(gray_image, 255, Imgproc.THRESH_BINARY);
		}
		else if(mode == 2){
			threshold_image = Thresholding.autoGrayThreshold_bright(gray_image, 255, Imgproc.THRESH_BINARY);
		}

		MainImage.setThreshold(true);

        int ksize = 2 * (int) Math.floor((double)threshold_image.cols() / 2000 + (double)threshold_image.rows() / 1000) - 1;
        despeckle_image = BlurFilter.MedianBlur(threshold_image, ksize);

        morph_image = mm.opening(despeckle_image, 2);
        morph_image = mm.closing(morph_image, 2);

		MainImage.setImage(morph_image);
	}

	public static void reset(){
		MainImage.setImage(original_image);
	}

	public static void save(){
		original_image = morph_image;

		MainImage.setImage(contrast_image);
		FileIO.export("Contrast", false, false);

		MainImage.setImage(gray_image);
		FileIO.export("Gray", true, false);

		MainImage.setImage(threshold_image);
		FileIO.export("Threshold", false, true);

		MainImage.setImage(despeckle_image);
		FileIO.export("Despeckle", false, false);

		MainImage.setImage(morph_image);
		FileIO.export("Morph", false, false);
	}
}
