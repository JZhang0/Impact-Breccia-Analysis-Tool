package utils.File;

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
        imwriteFlags.put("avif", Imgcodecs.IMWRITE_AVIF_QUALITY);

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

    public static Mat readFile(String filePath){
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
}