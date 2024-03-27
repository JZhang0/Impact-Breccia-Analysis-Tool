package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import src.main.java.GUI.DilationGUI;
import utils.Definition.ColorBGRValue;
import utils.File.FileIO;
import utils.GUI.MainImage;

public class Automate
{
	private static Mat original_image, contrast_image, gray_image, threshold_image, despeckle_image, morph_image;

	public static void run(int mode)
	{
		MorphManager mm = MorphManager.getInstance();
		MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, MainImage.getImageMat().rows(), MainImage.getImageMat().cols());

        Mat main_image;

        main_image = original_image = MainImage.getImageMat();

        contrast_image = Contrast.autoContrast(main_image, 5.0);

        gray_image = MatManager.RGBtoGray(contrast_image);

		if(mode == 1){
			threshold_image = Thresholding.autoGrayThreshold_dark(gray_image, 255, Imgproc.THRESH_BINARY);
		}
		else if(mode == 2){
			threshold_image = Thresholding.autoGrayThreshold_bright(gray_image, 255, Imgproc.THRESH_BINARY);
		}

        int ksize = (int) Math.floor((double)threshold_image.cols() / 2000 + (double)threshold_image.rows() / 1000);
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
		FileIO.export("Contrast");

		MainImage.setImage(gray_image);
		FileIO.export("Gray");

		MainImage.setImage(threshold_image);
		FileIO.export("Threshold");

		MainImage.setImage(despeckle_image);
		FileIO.export("Despeckle");

		MainImage.setImage(morph_image);
		FileIO.export("Morph");
	}
}
