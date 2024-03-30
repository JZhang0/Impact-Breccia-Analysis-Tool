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
package utils.GUI;

import src.main.java.GUI.GUI;
import utils.Definition.ColorBGRValue;
import utils.Processing.EdgeDetection;
import utils.Processing.MatManager;
import utils.Processing.MorphManager;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Toggle
{
	//Overlay the previous image onto the current one
	public static void render_anchor_image()
	{
		if(AnchorImage.exists())
			GUI.render(AnchorImage.getImageMat());
	}

	//Overlay the first image onto the current one
	public static boolean render_edge_overlay()
	{
		if(MainImage.checkGray()){
			MorphManager mm = MorphManager.getInstance();
			MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, MainImage.getImageWidth(), MainImage.getImageHeight());

			GUI.createGUI();
			Mat edges = EdgeDetection.autoCannyEdgeDetection(MainImage.getImageMat(), 3, true, ColorBGRValue.BGR_RED);
			edges = mm.dilation(edges, 1);
			
			Mat edge_overlay = MatManager.overlay(AnchorImage.getImageMat(), edges);
			GUI.render(edge_overlay);
			GUI.destroyGUI();

			return true;
		}
		else{
			return false;
		}
	}
}
