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
package utils.Processing;

import utils.Calculation.ParticleAnalysis;
import utils.Definition.ColorBGRValue;
import utils.File.FileIO;
import utils.GUI.AnchorImage;
import utils.GUI.MainImage;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class BackgroundRemoval {
    public static Mat removeBackground(Mat srcImage, Rect resizeRect) {
        MorphManager mm = MorphManager.getInstance();
        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, srcImage.rows(), srcImage.cols());

        Mat destImage = MatManager.createMatWithProperty(srcImage);
        Mat mask = new Mat();

        srcImage.copyTo(mask);
        mask = MatManager.flipColor(srcImage);

        mask = MatManager.RGBtoGray(mask);
        mask = EdgeDetection.autoCannyEdgeDetection(mask, 5, true, ColorBGRValue.BGR_WHITE);
        mask = MatManager.RGBtoGray(mask);

        mask = mm.closing(mask, 2);
        mask = mm.opening(mask, 5);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        double inf = 0;
        int max_index = 0;
        Rect max_rect = null;
        for(int i=0; i< contours.size();i++){
            Rect rect = Imgproc.boundingRect(contours.get(i));
            double area = rect.area();

            if(inf < area) {
                max_rect = rect;
                max_index = i;
                inf = area;
            }
        }

        Imgproc.drawContours(mask, contours, max_index, ColorBGRValue.BGR_WHITE, -1);

        mask = MatManager.GraytoRGB(mask);

        ParticleAnalysis.setMask(MatManager.RGBtoGray(mask));

        srcImage.copyTo(destImage, mask);
        Core.copyMakeBorder(destImage, destImage, 10, 10, 10, 10, Core.BORDER_CONSTANT, ColorBGRValue.BGR_BLACK);
        destImage = MatManager.floodFill(destImage, 0, 0, ColorBGRValue.BGR_WHITE);

        destImage = destImage.submat(max_rect);
        mask = mask.submat(max_rect);

        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, destImage.rows(), destImage.cols());

        return destImage;
    }

    //Remove the background from the image and replace it with empty space
    public static Mat subtractBackground()
    {
        Rect resizeRect = new Rect();
        return BackgroundRemoval.removeBackground(MainImage.getImageMat(), resizeRect);
    }

    //Save the image with no background permanently
    public static void save()
    {
        MainImage.setImage(AnchorImage.getImageMat());
        AnchorImage.subBacground(true);

        FileIO.export("SubBackground", false, false);
    }
}
