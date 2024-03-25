package src.main.java;

public class Settings //this is getting replaced with a File with these values stored in it
{
	public static boolean LAUNCH_MAXIMIZED = true;
	public static int DEFAULT_WIDTH = 1000;
	public static int DEFAULT_HEIGHT = 800;
	public static String VERSION = "0.0.2";
	public static String[] ICONS = new String[] {"contrast", "gauss", "RGB", "invert", "subtract_background", "threshold", "despeckle", "outliers", "holes", "brush", "resize", "save", "undo", "redo", "auto", "zoom_in", "zoom_out", "zoom_reset", "fill_bucket", "eraser_bucket", "eraser"};
	public static double ZOOM_FACTOR = 1.1;

	public static String[] SUPPORTED_FILE_FORMATS = new String[] {"avif", "jpg", "jpeg", "png", "tif", "tiff", "webp"};

	public static int THRESHOLD_MIN = 0;
	public static int THRESHOLD_MAX = 254;
	public static int CONTRAST_ALPHA_MIN = 100;
	public static int CONTRAST_ALPHA_MAX = 300;
	public static int CONTRAST_BETA_MIN = 0;
	public static int CONTRAST_BETA_MAX = 255;
	public static int GAUSS_MIN = 1;
	public static int GAUSS_MAX = 10;
	public static int DESPECKLE_MIN = 1;
	public static int DESPECKLE_MAX = 16;
	public static int OVERLAY_CURRENT_IMAGE_WEIGHT = 60;
}
