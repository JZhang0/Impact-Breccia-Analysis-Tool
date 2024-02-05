package src.main.java;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import src.main.java.GUI.*;
import utils.File.FileIO;
import utils.Processing.BlurFilter;
import utils.Processing.Contrast;

public class Demo {
    public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat original_image = FileIO.readFile("C:\\Users\\Nicolas\\Downloads\\Car.jpg");
        Mat processed_image = original_image;

        processed_image = Contrast.adjustContrast(processed_image);
        //processed_image = BlurFilter.adjustBlur(processed_image);

        HighGui.imshow("Old Image", original_image);
        HighGui.imshow("New Image", processed_image);
        HighGui.waitKey();
        System.exit(0);
        //GUI gui = new GUI();
    }
}