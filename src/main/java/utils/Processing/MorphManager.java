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

import utils.File.FileIO;
import utils.GUI.MainImage;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class MorphManager {
    private static MorphManager MorphManager = null;

    // A history of operations applied, 0 for erosion and 1 for dilation.

    private static int m_elementType = Imgproc.MORPH_RECT;
    private static int m_kernelSize = 5;
    private static Mat m_kernel;

    public static synchronized MorphManager getInstance(){
        if(MorphManager == null){
            MorphManager = new MorphManager();
            updateKernel(m_elementType, m_kernelSize);
        }

        return MorphManager;
    }

    public static void updateKernel(int elementType, int kernelSize){
        m_elementType = elementType;
        m_kernelSize = kernelSize;
        m_kernel = Imgproc.getStructuringElement(m_elementType, new Size(2 * m_kernelSize + 1, 2 * m_kernelSize + 1), new Point(-1, -1));
    }

    public static void updateKernel(int elementType, int width, int height){
        m_elementType = elementType;
        m_kernelSize = (int) Math.floor((double)width / 2000 + (double)height / 1000);
        System.out.println("MorphManager | m_kernelSize: " + m_kernelSize);
        m_kernel = Imgproc.getStructuringElement(m_elementType, new Size(2 * m_kernelSize + 1, 2 * m_kernelSize + 1), new Point(-1, -1));
    }

    /**
     * Erode away(remove) the boundaries of foreground object.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param srcImage Input image
     * @param iterations Number of operations to apply
     */
    public Mat erosion(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.erode(srcImage, destImage, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    /**
     * Add pixels to the boundaries of objects in an image.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param Input Input image
     * @param iterations Number of operations to apply
     */
    public Mat dilation(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.dilate(srcImage, destImage, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    /**
     * Erosion followed by dilation. It is useful in removing noise.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param srcImage Input image
     * @param iterations Number of operations to apply
     */
    public Mat opening(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.morphologyEx(srcImage, destImage, Imgproc.MORPH_OPEN, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    /**
     * Dilation followed by Erosion. It is useful in closing small holes inside the foreground objects.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param srcImage Input image
     * @param iterations Number of operations to apply
     */
    public Mat closing(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.morphologyEx(srcImage, destImage, Imgproc.MORPH_CLOSE, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    public static void applyErosion()
	{
		MainImage.setImage(MorphManager.erosion(MainImage.getImageMat(), 1));
	}

    public static void applyDilation()
	{
		MainImage.setImage(MorphManager.dilation(MainImage.getImageMat(), 1));
	}

    public static void save(String op){
        FileIO.export(op, false, false);
    }
}
