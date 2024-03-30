package src.main.java;

import javafx.scene.shape.Shape;

public class Settings //this is getting replaced with a File with these values stored in it
{
	public static boolean LAUNCH_MAXIMIZED = true;
	public static int DEFAULT_WIDTH = 1000;
	public static int DEFAULT_HEIGHT = 800;
	public static String VERSION = "0.0.2";
	public static String[] ICONS = new String[] {"contrast", "gauss", "RGB", "invert", "help", "threshold", "despeckle", "erosion", "dilation", "brush", "resize", "save", "undo", "redo", "auto", "zoom_in", "zoom_out", "zoom_reset", "filler", "eraser"};
	public static String[] CURSORS = new String[] {"cursor_eraser", "cursor_filler", "cursor_wait"};
	public static double ZOOM_FACTOR = 1.1;

	public static String[] SUPPORTED_FILE_FORMATS = new String[] {"jpg", "png", "webp", "tif"};
	public static String[] SUPPORTED_ANALYSIS_TYPES = new String[] {"Moments", "Convex Hull", "Bounding Rectangle", "Enclosing Circle", "Fit Ellipse", "Shape Descriptors", "Feret's Rectangle"};

	public static int THRESHOLD_MIN = 0;
	public static int THRESHOLD_MAX = 255;
	public static int CONTRAST_ALPHA_MIN = 0;
	public static int CONTRAST_ALPHA_MAX = 50;
	public static int CONTRAST_BETA_MIN = 0;
	public static int CONTRAST_BETA_MAX = 200;
	public static int CONTRAST_GAMMA_MIN = 0;
	public static int CONTRAST_GAMMA_MAX = 100;
	public static int GAUSS_MIN = 0;
	public static int GAUSS_MAX = 10;
	public static int DESPECKLE_MIN = 0;
	public static int DESPECKLE_MAX = 10;
	public static int OVERLAY_CURRENT_IMAGE_WEIGHT = 60;
}
