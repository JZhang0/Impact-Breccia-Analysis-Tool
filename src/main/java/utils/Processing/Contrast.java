package utils.Processing;

import org.opencv.core.Mat;

//Update the contrast of the image
class Contrast
{
    private byte saturate(double val)
    {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }

    public Mat adjustContrast(Mat image)
    {
        return run(2.0, 88, image);
    }

    private Mat run(double alpha, int beta, Mat image)
    {
        Mat newImage = MatManager.createMatWithProperty(image);

        byte[] imageData = new byte[(int) (image.total() * image.channels())];
        image.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total() * newImage.channels())];

        for (int y = 0; y < image.rows(); y++)
            for (int x = 0; x < image.cols(); x++)
                for (int c = 0; c < image.channels(); c++)
                {
                    double pixelValue = imageData[(y * image.cols() + x) * image.channels() + c];

                    if (pixelValue < 0)
                        pixelValue = pixelValue + 256;

                    newImageData[(y * image.cols() + x) * image.channels() + c] = saturate(alpha * pixelValue + beta);
                }

        newImage.put(0, 0, newImageData);

        return newImage;
        /*HighGui.imshow("Original Image", image);
        HighGui.imshow("New Image", newImage);
        HighGui.waitKey();
        System.exit(0);*/
    }
}