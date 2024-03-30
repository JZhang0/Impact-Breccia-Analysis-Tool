package utils.Processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import utils.Calculation.ParticleAnalysis;
import utils.Definition.ColorBGRValue;
import utils.File.FileIO;
import utils.GUI.AnchorImage;
import utils.GUI.FloodFillImage;
import utils.GUI.MainImage;

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

        FileIO.saveFile("sample\\out\\test_mask.png", mask, "png", 9);

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
