package utils.File;

import src.main.java.GUI.MainImage;
import src.main.java.Settings;

public class Save
{
	public static void export(String filename, int filetype_id)
	{
		switch (filetype_id)
		{
			case 0: //AVIF
			case 1: //JPG
			case 2: //JPEG
			case 6: //WEBP
				FileIO.saveFile(FileIO.getFilepath() + filename + "." + Settings.SUPPORTED_FILE_FORMATS[filetype_id], MainImage.getImageMat(), Settings.SUPPORTED_FILE_FORMATS[filetype_id], 100);
				break;
			case 3: //PNG
				FileIO.saveFile(FileIO.getFilepath() + filename + "." + Settings.SUPPORTED_FILE_FORMATS[filetype_id], MainImage.getImageMat(), Settings.SUPPORTED_FILE_FORMATS[filetype_id], 0);
				break;
			case 4: //TIF
			case 5: //TIFF
				FileIO.saveFile(FileIO.getFilepath() + filename + "." + Settings.SUPPORTED_FILE_FORMATS[filetype_id], MainImage.getImageMat(), Settings.SUPPORTED_FILE_FORMATS[filetype_id], 5);
				break;
			default:
				System.out.println("Invalid filetype id given");
		}
	}
}
