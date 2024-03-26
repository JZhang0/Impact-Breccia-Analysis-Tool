package utils.File;

import org.opencv.core.Mat;
import src.main.java.GUI.GUI;
import src.main.java.GUI.MainImage;

import java.util.ArrayList;
import java.util.List;

/*
* This controls the program's ability to store information about past filters that have been applied to the image. You can use this class to go back to a previous filter and then return to the present filter.
* */
public class History
{
	//This list stores the filename of each individual file containing a filter's output
	private static List<String> filenames = new ArrayList<>();

	//The position in the filter history that we are currently at
	private static int version_history_index = 0;

	//Get our current position in the filter list's history
	public static int getVersion()
	{
		return version_history_index;
	}

	//Increase the version when we add a new filter
	public static void increaseVersion()
	{
		version_history_index++;
	}

	//Decrease the version when we undo a filter
	public static void decreaseVersion()
	{
		version_history_index--;
	}

	//Undo the previous filter and go back to the previous filter's job
	public static void undo()
	{
		if (History.getVersion() > 1)
		{
			History.decreaseVersion();
			MainImage.setImage(getPreviousImage(1));
			GUI.render(MainImage.getImageMat());
		}
	}

	//Redo the previously undone change and go back to the present changes
	public static void redo()
	{
		if (History.getVersion() < filenames.size())
		{
			History.increaseVersion();
			MainImage.setImage(getPreviousImage(2));
			GUI.render(MainImage.getImageMat());
		}
	}

	public static Mat getPreviousImage(int back)
	{
		if (back < 2)
			return getOriginalImage();
		else
			return FileIO.readFile(FileIO.getFilepath() + filenames.get(History.getVersion() - back));
	}

	public static Mat getOriginalImage()
	{
		return FileIO.readFile(FileIO.getFilepath() + filenames.get(History.getVersion() - version_history_index));
	}

	//Add a filename to the history list
	//If we detect here that we are overwriting a filter, then erase the future history since we're not going with those filters anymore
	public static void addFilename(String new_filename)
	{
		int filenames_size = filenames.size();

		//If we used undo and are now doing a new operation, remove the history after this point
		for (int i = filenames_size; i >= version_history_index; i--)
		{
			FileIO.delete(filenames.get(filenames.size() - 1));
			filenames.remove(filenames.size() - 1);
		}

		//Add the new filtered image to the filter list
		filenames.add(new_filename);
	}
}
