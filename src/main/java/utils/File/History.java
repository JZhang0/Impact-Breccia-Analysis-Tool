package utils.File;

import org.opencv.core.Mat;
import src.main.java.GUI.GUI;
import utils.GUI.MainImage;

import java.util.ArrayList;
import java.util.List;

/*
* This controls the program's ability to store information about past filters that have been applied to the image. You can use this class to go back to a previous filter and then return to the present filter.
* */
public class History
{
	//This list stores the filename of each individual file containing a filter's output
	private static List<String> filenames = new ArrayList<String>();
	private static List<Boolean> grayHistory = new ArrayList<Boolean>(), binaryHistory = new ArrayList<Boolean>();

	//The position in the filter history that we are currently at
	private static int version_history_index = -1;

	//Get our current position in the filter list's history
	public static int getExportIndex()
	{
		return version_history_index + 1;
	}

	public static void reset(){
		version_history_index = -1;
		filenames.clear();
	}

	private static Mat getIndexImage()
	{
		return FileIO.readFile(FileIO.getFilepath() + filenames.get(version_history_index));
	}

	//Undo the previous filter and go back to the previous filter's job
	public static void undo()
	{
		if (version_history_index > 0)
		{
			if(grayHistory.get(version_history_index) == true){
				MainImage.setSplit(false);
			}
			else if(binaryHistory.get(version_history_index) == true){
				MainImage.setThreshold(false);
			}

			version_history_index--;
			MainImage.setImage(getIndexImage());
			GUI.render(MainImage.getImageMat());
		}
	}

	//Redo the previously undone change and go back to the present changes
	public static void redo()
	{
		if (version_history_index < filenames.size() - 1)
		{
			version_history_index++;
			
			if(grayHistory.get(version_history_index) == true){
				MainImage.setSplit(true);
			}
			else if(binaryHistory.get(version_history_index) == true){
				MainImage.setThreshold(true);
			}

			MainImage.setImage(getIndexImage());
			GUI.render(MainImage.getImageMat());
		}
	}

	public static Mat getOriginalImage()
	{
		return FileIO.readFile(FileIO.getFilepath() + filenames.get(History.getExportIndex() - version_history_index));
	}

	//Add a filename to the history list
	//If we detect here that we are overwriting a filter, then erase the future history since we're not going with those filters anymore
	public static void addFilename(String new_filename, boolean isSplit, boolean isThreshold)
	{
		//If we used undo and are now doing a new operation, remove the history after this point
		for (int i = filenames.size() - 1; i > version_history_index; i--)
		{
			FileIO.delete(filenames.get(i));
			filenames.remove(i);
			grayHistory.remove(i);
			binaryHistory.remove(i);
		}

		//Add the new filtered image to the filter list
		filenames.add(new_filename);
		grayHistory.add(isSplit);
		binaryHistory.add(isThreshold);
		System.out.println(filenames + " | " + isSplit + " | " + isThreshold);

		version_history_index = filenames.size() - 1;
	}
}
