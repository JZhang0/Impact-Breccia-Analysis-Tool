/*
 * Copyright (C) 2024 Yifei Zhang, Nicolas Louis Jacobs, Yuhan Zhang - All Rights Reserved
* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package src.main.java;

public class Settings //this is getting replaced with a File with these values stored in it
{
	public static boolean LAUNCH_MAXIMIZED = true;
	public static int DEFAULT_WIDTH = 1000;
	public static int DEFAULT_HEIGHT = 800;
	public static String VERSION = "1.0.0";
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
