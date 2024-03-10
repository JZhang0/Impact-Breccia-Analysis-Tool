package utils.File;

import src.main.java.GUI.GUI;
import src.main.java.GUI.MainImage;
import src.main.java.Settings;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageDropHandler extends TransferHandler
{
	@Override
	public boolean canImport(TransferSupport support)
	{
		return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	@Override
	public boolean importData(TransferSupport support)
	{
		//I don't know what this does, but I'm not about to delete it because it looks important
		if (!canImport(support))
		{
			return false;
		}

		Transferable transferable = support.getTransferable();

		//Begin attempt to read files from drag and drop
		try
		{
			//Create a list to store the files dragged into IBAT
			//This is implemented as a list in case of the event that multiple files are dragged into IBAT at once
			List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

			//Loop through the entire list
			//The final valid image is the one that will actually be processed
			for (File file : files)
			{
				//Loop through all the valid file formats to check to see if the current file is an image we can process
				for (String filetype : Settings.SUPPORTED_FILE_FORMATS)
				{
					//If it is a valid image, then set it to the image we'll be using
					//Note that this will be overwritten with the final valid image in the input
					if (file.getName().endsWith("." + filetype))
					{
						MainImage.setImage(FileIO.readFile(file.getAbsolutePath()));
						GUI.render(MainImage.getImageByte());
						MainImage.setFilename(file.getName().substring(0, file.getName().lastIndexOf('.')));
						MainImage.setTimestamp(System.currentTimeMillis());
						FileIO.export("default");
					}
				}
			}

			return true;
		}
		catch (UnsupportedFlavorException | IOException e)
		{
			e.printStackTrace();

			return false;
		}
	}
}