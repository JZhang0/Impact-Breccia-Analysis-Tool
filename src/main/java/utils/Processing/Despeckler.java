package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Despeckler {

	public static Mat despeckle(Mat srcImage, int ksize){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}

	public static Mat despeckle(Mat srcImage, int width, int height){
		Mat destImage = MatManager.createMatWithProperty(srcImage);

		int ksize = 2 * (width / 2000 + height / 1000) + 1;
        System.out.println("Despeckle ksize: " + ksize);

		Imgproc.medianBlur(srcImage, destImage, ksize);

		return destImage;
	}
}