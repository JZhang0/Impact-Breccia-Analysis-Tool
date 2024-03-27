package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import src.main.java.Settings;
import utils.Calculation.HistCalculator;
import utils.File.FileIO;
import utils.GUI.MainImage;


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
        if(MainImage.checkGray()){
            MainImage.setBinary(true);
        }
		reset();

		FileIO.export("Threshold");
	}
}