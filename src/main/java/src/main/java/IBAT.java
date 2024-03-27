package src.main.java;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import src.main.java.GUI.*;
import utils.File.FileIO;

import java.awt.*;

//Launch the IBAT application
public class IBAT
{
	public static void main(String[] args)
	{
		//Load the OpenCV library
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		nu.pattern.OpenCV.loadLocally();

		//Launch the GUI
		new GUI();
	}
}