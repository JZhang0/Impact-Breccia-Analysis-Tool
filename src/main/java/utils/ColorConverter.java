package utils;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorConverter {
    
    public static Mat RGBtoGray(Mat srcImage){
		Mat destImage = Mat.zeros(srcImage.size(), srcImage.type());

		Imgproc.cvtColor(srcImage, destImage, Imgproc.COLOR_RGB2GRAY);

		return destImage;
	}
}
