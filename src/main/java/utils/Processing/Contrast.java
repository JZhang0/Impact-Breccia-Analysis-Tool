package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import src.main.java.GUI.MainImage;
import utils.File.FileIO;

/*
* This class defines the behaviour for updating the contrast of an image.
* */
public class Contrast
{
    //The alpha value is the parameter by which we adjust the contrast
    private static double alpha = 1.0;

    //Adjust the contrast and return a copy of the modified image to be displayed
    public static Mat adjustConstrast(double alpha_value)
    {
        Mat new_image = new Mat();
        Core.multiply(MainImage.getImageMat(), new Scalar(alpha_value, alpha_value, alpha_value), new_image);
        alpha = alpha_value;

        return new_image;
    }

    //Return the currently saved alpha value
    public static double getAlpha()
    {
        return alpha;
    }

    //Save the image to the MainImage
    public static void save()
    {
        MainImage.setImage(adjustConstrast(alpha));
        alpha = 1.0;

        FileIO.export("Contrast");
    }
}