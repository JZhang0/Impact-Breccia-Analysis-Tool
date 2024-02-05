package utils.Processing;

import org.opencv.core.Mat;
import src.main.java.Settings;
import utils.File.FileIO;

public class ImageFilter
{
	static void export(String file_name, Mat image)
	{
		if (Settings.getDoSequentialExports())
			FileIO.saveFile(Settings.getFilePath() + "/" + file_name + ".jpg", image);
	}
}
