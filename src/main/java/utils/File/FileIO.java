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

import utils.GUI.MainImage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

public class FileIO {

    public static final int TIF_COMPRESSION_NONE = 1;
    public static final int TIF_COMPRESSION_LZW = 5;
    public static final int TIF_COMPRESSION_DEFLATE  = 8;
    public static final int TIF_COMPRESSION_ADOBE_DEFLATE  = 32946;

    public static final Map<String, Integer> imwriteFlags = new HashMap<>();
    static {
        /**
         * Second parameter varies from 1 to 100, the higher the better quality and bigger file size.
         */
        imwriteFlags.put("jpg", Imgcodecs.IMWRITE_JPEG_QUALITY);
        imwriteFlags.put("jpeg", Imgcodecs.IMWRITE_JPEG_QUALITY);
        imwriteFlags.put("webp", Imgcodecs.IMWRITE_WEBP_QUALITY);

        /**
         * Second parameter varies from 0 to 9, a higher value means a smaller size and longer compression time.
         */
        imwriteFlags.put("png", Imgcodecs.IMWRITE_PNG_COMPRESSION);

        /**
         * Second parameter specifies compression scheme, defined at top.
         */
        imwriteFlags.put("tif", Imgcodecs.IMWRITE_TIFF_COMPRESSION);
        imwriteFlags.put("tiff", Imgcodecs.IMWRITE_TIFF_COMPRESSION);
    }

    public static Mat readFile(String filePath)
    {
        return Imgcodecs.imread(filePath);
    }

    /**
     * Loads an image from a file.
     * 
     * @param filePath Name of file to be loaded.
     * @param flag - Flag that can take values of cv::ImreadModes.
     * @return A <code>Mat</code> object holding the image.
     */
    public static Mat readFile(String filePath, int flag){
        return Imgcodecs.imread(filePath);
    }

    public static boolean saveFile(String filePath, Mat srcImage){
		return Imgcodecs.imwrite(filePath, srcImage);
    }
    
    /**
     * Saves an image to a specified file.
     * 
     * @param filePath Name of the file.
     * @param srcImage (Mat or vector of Mat) Image or Images to be saved.
     * @param fileExtension Extension of the file to be saved, use lower case letters (jpg, etc.).
     * @param compressionValue  Specify compression value associated with file type: <p>
     *                          <ul>
     *                              <li>For <code>jpg/jpeg/webp/avif</code>, the second parameter varies from 1 to 100, the higher the better quality and bigger file size.</li>
     *                              <li>For <code>png</code>, the second parameter varies from 0 to 9, a higher value means a smaller size and longer compression time.</li>
     *                              <li>For <code>tif/tiff</code>, the second parameter specifies compression scheme, defined as follows: 
     *                              <ul>
     *                                  <li>TIF_COMPRESSION_NONE</li>
     *                                  <li>TIF_COMPRESSION_LZW</li>
     *                                  <li>TIF_COMPRESSION_DEFLATE</li>
     *                                  <li>TIF_COMPRESSION_ADOBE_DEFLATE</li>
     *                              </ul>
     *                              </li>
     *                          </ul>
     * @return <code>true</code> upon successful save.
     */
    public static boolean saveFile(String filePath, Mat srcImage, String fileExtension, int compressionValue){
        MatOfInt params = new MatOfInt(imwriteFlags.get(fileExtension), compressionValue);

		return Imgcodecs.imwrite(filePath, srcImage, params);
    }

    public static void export(String filter, boolean isSplit, boolean isThreshold)
    {
        //Ensure that the filepath exists before progressing
        File folder = new File(getFilepath());
        if (!folder.exists())
        {
            folder.mkdirs();
        }

        String filename = MainImage.getFilename() + "_" + History.getExportIndex() + "_" + filter + ".tif";

        //Increase the version history of this filter edit so we know what to roll back to on an undo
        History.addFilename(filename, isSplit, isThreshold);

        //Save the file into the correct directory
        saveFile(getFilepath() + filename, MainImage.getImageMat(), "tif", TIF_COMPRESSION_LZW);
    }

    //Delete an image file
    public static void delete(String filename)
    {
        File file = new File(getFilepath() + filename);

        if (file.exists())
        {
            file.delete();
        }
    }

    //Get the entire filepath that is used to store this image's filter history
    public static String getFilepath()
    {
        return "Exports\\";
    }

    public static void resetExportFolder(){
        File folder = new File(getFilepath());
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        else if(folder.exists() && folder.isDirectory()){
            for(File f : folder.listFiles()){
                f.delete();
            }
        }

        History.reset();
    }

    public static void deleteExportFolder(){
        File folder = new File(getFilepath());

        if(folder.exists() && folder.isDirectory()){
            for(File f : folder.listFiles()){
                f.delete();
            }
        }

        folder.delete();
    }
}
