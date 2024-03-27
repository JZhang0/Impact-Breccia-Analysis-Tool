package utils.Processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import utils.Definition.ColorBGRValue;
import utils.File.FileIO;
import utils.GUI.MainImage;

public class BackgroundRemoval {
    // public static Mat 

    private static Rect coordContainObject(Mat srcImage){
        int left = 0, right = 0, top = 0, bottom = 0;

        for (int y = 0; y < srcImage.rows(); y++){
            for (int x = 0; x < srcImage.cols(); x++){
                double average = Arrays.stream(srcImage.get(y, x)).average().getAsDouble();

                if(average != 255.0 && average != 0.0){
                    if(top == 0){
                        top = y;
                    }
                    else{
                        bottom = y;
                    }
                }
            }
        }
        for (int x = 0; x < srcImage.cols(); x++){
            for (int y = 0; y < srcImage.rows(); y++){
                double average = Arrays.stream(srcImage.get(y, x)).average().getAsDouble();

                if(average != 255.0 && average != 0.0){
                    if(left == 0){
                        left = x;
                    }
                    else{
                        right = x;
                    }
                }
            }
        }
        System.out.println(left + " | " + top + " | " + (right-left) + " | " + (bottom-top));
        return new Rect(left, top, right-left, bottom-top);
    }

    public static Mat removeBackground2(Mat srcImage, Rect resizeRect) {
        MorphManager mm = MorphManager.getInstance();
        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, srcImage.rows(), srcImage.cols());

        Mat destImage = MatManager.createMatWithProperty(srcImage);
        Mat mask = new Mat();

        srcImage.copyTo(mask);
        mask = MatManager.flipColor(srcImage);
        mask = MatManager.recolor(mask, ColorBGRValue.BGR_WHITE, ColorBGRValue.BGR_WHITE, ColorBGRValue.BGR_BLACK);

        mask = MatManager.RGBtoGray(mask);
        mask = EdgeDetection.autoCannyEdgeDetection(mask, 5, true, ColorBGRValue.BGR_WHITE);

        mask = mm.closing(mask, 2);
        mask = mm.opening(mask, 20);
        mask = mm.dilation(mask, 2);
        mask = MatManager.floodFill(mask, new Point(0, 0), ColorBGRValue.BGR_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(mask.cols()-1, 0), ColorBGRValue.BGR_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(0, mask.rows()-1), ColorBGRValue.BGR_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(mask.cols()-1, mask.rows()-1), ColorBGRValue.BGR_MAGENTA);
        mask = mm.erosion(mask, 2);

        mask = MatManager.recolor(mask, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_WHITE);
        mask = MatManager.recolor(mask, ColorBGRValue.BGR_MAGENTA, ColorBGRValue.BGR_MAGENTA, ColorBGRValue.BGR_BLACK);

        srcImage.copyTo(destImage, mask);
        destImage = MatManager.recolor(destImage, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_WHITE);

        Rect rect = coordContainObject(destImage);
        destImage = destImage.submat(rect);
        resizeRect.x = rect.x;
        resizeRect.y = rect.y;
        resizeRect.height = rect.height;
        resizeRect.width = rect.width;

        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, destImage.rows(), destImage.cols());

        return destImage;
    }

    public static Mat removeBackground(Mat srcImage, Rect resizeRect) {
        MorphManager mm = MorphManager.getInstance();
        MorphManager.updateKernel(Imgproc.MORPH_ELLIPSE, srcImage.rows(), srcImage.cols());

        Mat destImage = MatManager.createMatWithProperty(srcImage);
        Mat mask = new Mat();

        srcImage.copyTo(mask);
        mask = MatManager.flipColor(srcImage);
        mask = MatManager.recolor(mask, ColorBGRValue.BGR_WHITE, ColorBGRValue.BGR_WHITE, ColorBGRValue.BGR_BLACK);

        mask = MatManager.RGBtoGray(mask);
        mask = EdgeDetection.autoCannyEdgeDetection(mask, 5, true, ColorBGRValue.BGR_WHITE);
        mask = MatManager.RGBtoGray(mask);

        mask = mm.closing(mask, 2);
        mask = mm.opening(mask, 5);
        FileIO.saveFile("sample\\out\\test_mask.png", mask, "png", 9);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        double inf = 0;
        Rect max_rect = null;
        for(int i=0; i< contours.size();i++){
            Rect rect = Imgproc.boundingRect(contours.get(i));

            double area = rect.area();

            if(inf < area) {
                max_rect = rect;
                inf = area;
            }
        }

        mask = MatManager.GraytoRGB(mask);

        mask = MatManager.floodFill(mask, new Point(0, 0), ColorBGRValue.BGR_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(mask.cols()-1, 0), ColorBGRValue.BGR_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(0, mask.rows()-1), ColorBGRValue.BGR_MAGENTA);
        mask = MatManager.floodFill(mask, new Point(mask.cols()-1, mask.rows()-1), ColorBGRValue.BGR_MAGENTA);

        mask = MatManager.recolor(mask, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_WHITE);
        mask = MatManager.recolor(mask, ColorBGRValue.BGR_MAGENTA, ColorBGRValue.BGR_MAGENTA, ColorBGRValue.BGR_BLACK);

        srcImage.copyTo(destImage, mask);
        destImage = MatManager.recolor(destImage, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_BLACK, ColorBGRValue.BGR_WHITE);

        destImage = destImage.submat(max_rect);

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
        MainImage.setImage(subtractBackground());

        FileIO.export("SubBackground");
    }
}
