package src.main.java;

public class Settings //this is getting replaced with a File with these values stored in it
{
	public static boolean LAUNCH_MAXIMIZED = true;
	public static int DEFAULT_WIDTH = 1000;
	public static int DEFAULT_HEIGHT = 800;
	public static String VERSION = "0.0.2";
	public static String[] ICONS = new String[] {"contrast", "gauss", "RGB", "invert", "subtract_background", "threshold", "despeckle", "outliers", "holes", "brush", "resize", "save", "undo", "redo", "auto"};
	public static double ZOOM_FACTOR = 1.1;

	public static String[] SUPPORTED_FILE_FORMATS = new String[] {"avif", "jpg", "jpeg", "png", "tif", "tiff", "webp"};
}
