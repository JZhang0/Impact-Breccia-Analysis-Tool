package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import src.main.java.GUI.MainImage;
import utils.Definition.ColorRGBValue;
import utils.File.FileIO;

public class BackgroundRemoval {

    public static Mat removeBackground(Mat srcImage) {
        MorphManager mm = MorphManager.getInstance();
        Mat destImage = MatManager.createMatWithProperty(srcImage);
        Mat mask = new Mat();

        mask = MatManager.flipColor(srcImage);
        mask = MatManager.recolor(mask, ColorRGBValue.RGB_WHITE, ColorRGBValue.RGB_WHITE, ColorRGBValue.RGB_BLACK);

        mask = MatManager.RGBtoGray(mask);
        mask = EdgeDetection.cannyEdgeDetection(mask, 40, 100, 3, true, ColorRGBValue.RGB_WHITE);

        mask = mm.dilation(mask, 1);
        mask = MatManager.floodFill(mask, new Point(0, 0), ColorRGBValue.RGB_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(mask.cols()-1, 0), ColorRGBValue.RGB_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(0, mask.rows()-1), ColorRGBValue.RGB_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(mask.cols()-1, mask.rows()-1), ColorRGBValue.RGB_MAGENTA);
        mask = mm.erosion(mask, 1);

        mask = MatManager.recolor(mask, ColorRGBValue.RGB_BLACK, ColorRGBValue.RGB_BLACK, ColorRGBValue.RGB_WHITE);
        mask = MatManager.recolor(mask, ColorRGBValue.RGB_MAGENTA, ColorRGBValue.RGB_MAGENTA, ColorRGBValue.RGB_BLACK);
        // FileIO.saveFile("sample\\out\\test_mask.jpg", mask, "jpg", 100);

        srcImage.copyTo(destImage, mask);
        destImage = MatManager.recolor(destImage, ColorRGBValue.RGB_BLACK, ColorRGBValue.RGB_BLACK, ColorRGBValue.RGB_WHITE);

        return destImage;
    }

    //Remove the background from the image and replace it with empty space
    public static Mat subtractBackground()
    {
        return removeBackground(MainImage.getImageMat());
    }

    //Save the image with no background permanently
    public static void save()
    {
        MainImage.setImage(subtractBackground());

        FileIO.export("SubBackground");
    }
}
