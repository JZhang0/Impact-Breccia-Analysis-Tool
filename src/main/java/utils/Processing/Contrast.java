package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import utils.Calculation.HistCalculator;

//Update the contrast of the image
public class Contrast
{
    private static byte saturate(double val)
    {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }

    private static Mat run(double alpha, int beta, Mat image)
    {
        Mat newImage = MatManager.createMatWithProperty(image);

        byte[] imageData = new byte[(int) (image.total() * image.channels())];
        image.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total() * newImage.channels())];

        for (int y = 0; y < image.rows(); y++){
            for (int x = 0; x < image.cols(); x++){
                for (int c = 0; c < image.channels(); c++)
                {
                    double pixelValue = imageData[(y * image.cols() + x) * image.channels() + c];

                    if (pixelValue < 0)
                        pixelValue = pixelValue + 256;

                    newImageData[(y * image.cols() + x) * image.channels() + c] = saturate(alpha * pixelValue + beta);
                }
            }
        }

        newImage.put(0, 0, newImageData);

        return newImage;
    }

    public static Mat gammaCorrect(Mat srcImage, double gammaValue){
        Mat newImage = MatManager.createMatWithProperty(srcImage);

        Mat lookUpTable = new Mat(1, 256, CvType.CV_8U);
        byte[] lookUpTableData = new byte[(int) (lookUpTable.total()*lookUpTable.channels())];
        for (int i = 0; i < lookUpTable.cols(); i++) {
            lookUpTableData[i] = saturate(Math.pow(i / 255.0, gammaValue) * 255.0);
        }
        lookUpTable.put(0, 0, lookUpTableData);
        Core.LUT(srcImage, lookUpTable, newImage);

        return newImage;
    }

    public static Mat autoContrast(Mat srcImage){
        HistCalculator hc = new HistCalculator(MatManager.RGBtoGray(srcImage));

        double alpha = hc.getMax() / hc.getMean();
        return run(alpha, (int)(alpha * hc.getMin()), srcImage);
    }

    /**
     * Change the contrast and brightness of an image using the formula: g(x)=αf(x)+β, and correct the brightness of the output.
     * @param srcImage The source image.
     * @param alpha Parameter to control contrast
     * @param beta Parameter to control brightness
     * @param gamma When γ<1, the original dark regions will be brighter and the histogram will be shifted to the right whereas it will be the opposite with γ>1.
     */
    public static Mat adjustContrast(Mat srcImage, double alpha, int beta, double gamma)
    {
        return gammaCorrect(run(alpha, beta, srcImage), gamma);
    }
}