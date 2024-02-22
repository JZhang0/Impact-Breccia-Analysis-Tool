package src.main.java;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import src.main.java.GUI.*;

public class Demo {
    public static void main(String[] args) {
        // Load the native OpenCV library
        /*System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img = new Contrast().adjustContrast(ImageManipulation.getImage("C:\\Users\\Nicolas\\Downloads\\Car.jpg"));

        HighGui.imshow("New Image", img);
        HighGui.waitKey();
        System.exit(0);*/
        ImageDragDropApp.main(args);
    }
}