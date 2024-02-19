package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class ImageThresholding {

public static Mat Threshold(Mat src){
    // Convert the original source image to grayscale
    Mat gray = new Mat();
    Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);

    // Apply thresholding
    Mat thresholded = new Mat();
    double thresholdValue = 128; 
    double maxValue = 255; // Maximum value to use with the THRESH_BINARY thresholding
    Imgproc.threshold(gray, thresholded, thresholdValue, maxValue, Imgproc.THRESH_BINARY);

    return thresholded;
}

}