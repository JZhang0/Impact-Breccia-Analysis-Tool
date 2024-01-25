package src.main.java;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageManipulation
{
    public static Mat getImage(String imagePath)
    {
        return Imgcodecs.imread(imagePath);
    }
}
