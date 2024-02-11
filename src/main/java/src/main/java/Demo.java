package src.main.java;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import src.main.java.GUI.*;
import utils.File.FileIO;

import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /*Mat img = new Contrast().adjustContrast(ImageManipulation.getImage("C:\\Users\\Nicolas\\Downloads\\Car.jpg"));

        HighGui.imshow("New Image", img);
        HighGui.waitKey();
        System.exit(0);*/
        MainImage.setImage(FileIO.readFile("C:\\Users\\Nicolas\\Downloads\\Car.jpg"));
        GUI gui = new GUI();

        //HighGui.imshow("New Image", MainImage.getImageMat());
        //HighGui.waitKey();
        //System.exit(0);
    }
}