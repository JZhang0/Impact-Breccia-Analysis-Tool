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

import java.io.File;
import java.util.Arrays;

import org.opencv.core.Mat;

import src.main.java.Settings;
import utils.Calculation.ParticleAnalysis;
import utils.GUI.AnchorImage;
import utils.GUI.MainImage;
import utils.Processing.MatManager;

public class Save
{
	private static void generateReport(String path, Boolean[] analysis_selection){
		ParticleAnalysis p = new ParticleAnalysis(MatManager.RGBtoGray(MatManager.flipColor(MainImage.getImageMat())), path, MainImage.getFilename());
		p.generateReport(analysis_selection);
	}

	public static void export(String path, int filetype, Boolean[] analysis_selection)
	{
		File folder = new File(FileIO.getFilepath());

        if(folder.exists() && folder.isDirectory()){
            for(File f : folder.listFiles()){
				String fileName = f.getName().substring(0, f.getName().lastIndexOf('.'));
				Mat img = FileIO.readFile(f.getAbsolutePath());

				switch (filetype)
				{
					case 0: //JPG
						FileIO.saveFile(path + fileName + "." + Settings.SUPPORTED_FILE_FORMATS[filetype], img, Settings.SUPPORTED_FILE_FORMATS[filetype], 100);
						break;
					case 1: //PNG
						FileIO.saveFile(path + fileName + "." + Settings.SUPPORTED_FILE_FORMATS[filetype], img, Settings.SUPPORTED_FILE_FORMATS[filetype], 1);
						break;
					case 2: //WEBP
						FileIO.saveFile(path + fileName + "." + Settings.SUPPORTED_FILE_FORMATS[filetype], img, Settings.SUPPORTED_FILE_FORMATS[filetype], 100);
						break;
					case 3: //TIF
						FileIO.saveFile(path + fileName + "." + Settings.SUPPORTED_FILE_FORMATS[filetype], img, Settings.SUPPORTED_FILE_FORMATS[filetype], FileIO.TIF_COMPRESSION_LZW);
						break;
					default:
						System.out.println("Invalid filetype id given");
				}
            }
        }

		if(Arrays.asList(analysis_selection).contains(true) && AnchorImage.isBackgroundRemoved()){
			generateReport(path, analysis_selection);
		}
	}
}
