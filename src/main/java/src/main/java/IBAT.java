package src.main.java;

import src.main.java.GUI.*;

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