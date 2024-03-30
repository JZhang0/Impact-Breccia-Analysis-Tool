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
package utils.File;

import src.main.java.GUI.GUI;
import src.main.java.GUI.SubBackgroundGUI;
import utils.GUI.AnchorImage;
import utils.GUI.MainImage;
import utils.Processing.BackgroundRemoval;
import src.main.java.Settings;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.TransferHandler;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

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
			File file_to_process = getFile(files);

			//Insert the file if the user imported a valid file
			if (file_to_process != null)
			{
				GUI.changeCursor(2);

				FileIO.resetExportFolder();
				MainImage.setSplit(false);
				MainImage.setThreshold(false);
				AnchorImage.subBacground(false);
				
				MainImage.setImage(FileIO.readFile(file_to_process.getAbsolutePath()));
				MainImage.setFilename(file_to_process.getName().substring(0, file_to_process.getName().lastIndexOf('.')));

				AnchorImage.setImageMat(BackgroundRemoval.subtractBackground());
				GUI.render(AnchorImage.getImageMat());

				GUI.changeCursor(-1);

				SubBackgroundGUI.act();
			}

			return true;
		}
		catch (UnsupportedFlavorException | IOException e)
		{
			e.printStackTrace();

			return false;
		}
	}

	//Find the file to process
	//Loop through the files that the user has imported and return the last valid one
	private static File getFile(List<File> files)
	{
		File file_to_process = null;

		//Loop through the entire list
		//The final valid image is the one that will actually be processed
		for (File file : files)
		{
			//Loop through all the valid file formats to check to see if the current file is an image we can process
			for (String filetype : Settings.SUPPORTED_FILE_FORMATS)
			{
				//If it is a valid image, then set it to the image we'll be using
				//Note that this will be overwritten with the final valid image in the input
				if (filetype.equals(file.getName().substring(file.getName().lastIndexOf('.') + 1)))
				{
					file_to_process = file;
					break;
				}
			}
		}

		return file_to_process;
	}
}
