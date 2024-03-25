package utils;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import utils.File.*;
import utils.Processing.*;


public class test {
    public static void main(String[] args) {
		/*nu.pattern.OpenCV.loadLocally();
        MorphManager mm = MorphManager.getInstance();

		Mat sample, edges, dest;

        sample = FileIO.readFile("sample\\Impact Rocks\\MI_CC_04 - Melt-Bearing Breccia\\MI_CC_04_trimmed_4800px=1inch.tif");
        FileIO.saveFile("sample\\out\\1test.png", dest, "png", 9);
        dest = sample;

        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, 5);
        dest = BackgroundRemoval.removeBackground(dest);
        FileIO.saveFile("sample\\out\\2test_removeBackground.png", dest, "png", 9);

        // dest = Contrast.adjustContrast(dest, 1.56, 48, 5.0);
        dest = Contrast.autoContrast(dest);
        dest = Contrast.gammaCorrect(dest, 5.0);
		FileIO.saveFile("sample\\out\\3test_removeBackground_contrast.png", dest, "png", 9);

        dest = MatManager.RGBtoGray(dest);
        FileIO.saveFile("sample\\out\\4test_removeBackground_contrast_gray.png", dest, "png", 9);

        // dest = Thresholding.simpleThreshold(dest, 201.833, 255, Imgproc.THRESH_BINARY);
        dest = Thresholding.autoGrayThreshold(dest, 255, Imgproc.THRESH_BINARY);
        FileIO.saveFile("sample\\out\\5test_removeBackground_contrast_gray_simpleThreshold.png", dest, "png", 9);

        // MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, 2);
        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, dest.rows(), dest.cols());
        // dest = Despeckler.despeckle(dest, 7);
        dest = Despeckler.despeckle(dest, dest.rows(), dest.cols());
        FileIO.saveFile("sample\\out\\6test_removeBackground_contrast_gray_simpleThreshold_despeckle.png", dest, "png", 9);
        dest = mm.opening(dest, 2);
        dest = mm.closing(dest, 2);
		FileIO.saveFile("sample\\out\\7test_removeBackground_contrast_gray_simpleThreshold_despeckle_morph.png", dest, "png", 9);

		// edges = EdgeDetection.cannyEdgeDetection(dest, 115, 150, 3, true, new Scalar(255, 255, 255));
        edges = EdgeDetection.autoCannyEdgeDetection(dest, 3, true, new Scalar(255, 255, 255));
        edges = mm.dilation(edges, 1);
        FileIO.saveFile("sample\\out\\8test_removeBackground_contrast_gray_simpleThreshold_despeckle_morph_edges.png", edges, "png", 9);
		
		edges = MatManager.recolor(edges, new Scalar(255, 255, 255), new Scalar(255, 255, 255), new Scalar(0, 0, 255));
		dest = MatManager.overlay(sample, edges);
		FileIO.saveFile("sample\\out\\9test_removeBackground_contrast_gray_simpleThreshold_despeckle_morph_edges_overlay.png", dest, "png", 9);

		System.exit(0);

        // alpha = gray.Max / gray.Mean, beta = Gray.min * alpha
        // kSize needs to be porportional to image dimension.
        // Need an algorithm to auto determine threshold value, current approach: invert gray image -> maxvalue - stdDev.*/
	}
}