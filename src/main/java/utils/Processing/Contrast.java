package utils.Processing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.opencv.core.Scalar;
import src.main.java.GUI.MainImage;
import utils.Calculation.HistCalculator;
import utils.File.FileIO;

//Update the contrast of the image
public class Contrast
{
    //The alpha value is the parameter by which we adjust the contrast
    private static double alpha = 1.0;

    //The beta value is the parameter by which we adjust the brightness
    private static int beta = 0;

    //Adjust the contrast and return a copy of the modified image to be displayed
    public static Mat adjustConstrast(double alpha_value, int beta_value)
    {
        //Create a blank image Mat
        Mat new_image = new Mat();

        //Adjust the contrast
        Core.multiply(MainImage.getImageMat(), new Scalar(alpha_value, alpha_value, alpha_value), new_image);

        //Adjust the brightness
		Core.add(new_image, new Scalar(beta_value, beta_value, beta_value), new_image);

		//Save our internal alpha and beta values to remember this as our current setting
		//When we go to save the file, we use these as the alpha and beta values
        alpha = alpha_value;
		beta = beta_value;

		//Return the image with contrast and brightness adjusted
        return new_image;
    }

    //Return the currently saved alpha value
    public static double getAlpha()
    {
        return alpha;
    }

	//Return the currently saved beta value
	public static int getBeta()
	{
		return beta;
	}

    //Save the image to the MainImage
    public static void save()
    {
        //Update the main image
        MainImage.setImage(adjustConstrast(alpha, beta));

        //Reset parameters
        alpha = 1.0;
		beta = 0;

        //Export for history
        FileIO.export("Contrast");
    }

	//Automatically adjust the contrast of the image
	public static Mat auto()
	{
		//Generate a histogram of the greyscaled image to get some data on it
		HistCalculator hc = new HistCalculator(MatManager.RGBtoGray(MainImage.getImageMat()));

		//Calculate the optimal alpha value to adjust the contrast by
		double alpha = hc.getMax() / hc.getMean();

		//Calculate the optimal beta value to adjust the brightness by
		int beta = (int) (alpha * hc.getMin());

		//Adjust the contrast
		return adjustConstrast(alpha, beta);
	}



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