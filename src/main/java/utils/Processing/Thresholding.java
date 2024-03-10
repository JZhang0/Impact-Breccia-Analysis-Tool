package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import utils.Calculation.HistCalculator;


public class Thresholding {

    public static Mat simpleThreshold(Mat srcImage, double thresholdValue, double maxValue, int type){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.threshold(srcImage, destImage, thresholdValue, maxValue, type);

        return destImage;
    }

    public static Mat autoGrayThreshold(Mat srcImage, double maxValue, int type){
        HistCalculator hc = new HistCalculator(srcImage);

        return simpleThreshold(srcImage, hc.getMax() - hc.getStdDev(), maxValue, type);
    }
}