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

import src.main.java.Settings;
import utils.Calculation.HistCalculator;
import utils.File.FileIO;
import utils.GUI.MainImage;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Thresholding 
{
    private static int threshold = 127;

    public static Mat simpleThreshold(Mat srcImage, double thresholdValue, double maxValue, int type){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.threshold(srcImage, destImage, thresholdValue, maxValue, type);

        return destImage;
    }

    //Adjust the threshold of the MainImage
	public static Mat adjustThreshold(int threshold_value)
	{
        threshold = threshold_value;

		return simpleThreshold(MainImage.getImageMat(), threshold, Settings.THRESHOLD_MAX, Imgproc.THRESH_BINARY);
	}

    //Return the current threshold value
	public static int getThresh()
	{
		return threshold;
	}

    public static void reset()
    {
        threshold = 127;
    }

    public static Mat autoGrayThreshold_dark(Mat srcImage, double maxValue, int type){
        HistCalculator hc = new HistCalculator(srcImage);

        threshold = (int) (hc.getMax() - hc.getStdDev());

        // An algorithm to auto determine threshold value, current approach: invert gray image -> maxvalue - stdDev.
        return simpleThreshold(srcImage, threshold, maxValue, type);
    }

    public static Mat autoGrayThreshold_bright(Mat srcImage, double maxValue, int type){
        HistCalculator hc = new HistCalculator(srcImage);

        threshold = (int) (hc.getMax() - hc.getMean());

        // An algorithm to auto determine threshold value, current approach: invert gray image -> maxvalue - mean.
        return simpleThreshold(srcImage, threshold, maxValue, type);
    }

	public static Mat auto_dark()
	{
		//Adjust the contrast
		return autoGrayThreshold_dark(MainImage.getImageMat(), Settings.THRESHOLD_MAX, Imgproc.THRESH_BINARY);
	}

    public static Mat auto_bright()
	{
		//Adjust the contrast
		return autoGrayThreshold_bright(MainImage.getImageMat(), Settings.THRESHOLD_MAX, Imgproc.THRESH_BINARY);
	}

    //Save this filter to MainImage's current image
	public static void save()
	{
		MainImage.setImage(adjustThreshold(threshold));
        MainImage.setThreshold(true);
		reset();

		FileIO.export("Threshold", false, true);
	}
}