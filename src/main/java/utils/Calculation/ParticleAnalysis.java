package utils.Calculation;

import utils.Definition.ColorBGRValue;
import utils.File.FileIO;
import utils.Processing.MatManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.imgcodecs.Imgcodecs;

import org.apache.commons.lang3.ArrayUtils;

import com.opencsv.CSVWriter;

public class ParticleAnalysis {

    private static final Map<Integer, String[]> headers = new HashMap<>();
    static {
        headers.put(0, new String[] { "Area", "%Area", "Centroid X", "Centroid Y", "Perimeter" });
        headers.put(1, new String[] { "Convex Hull Area", "Solidity" });
        headers.put(2, new String[] { "Bounding Rectangle X", "Bounding Rectangle Y", "Bounding Rectangle width",
                "Bounding Rectangle Height" });
        headers.put(3, new String[] { "Circle Center X", "Circle Center Y", "Enclosing Circle Radius" });
        headers.put(4, new String[] { "Ellipse Center X", "Ellipse Center Y", "Ellipse Major", "Ellipse Minor",
                "Ellipse Angle" });
        headers.put(5, new String[] { "Circularity" });
        headers.put(6, new String[] { "Feret Rectangle X", "Feret Rectangle Y", "Feret Rectangle Angle",
                "Feret Rectangle width", "Feret Rectangle Height" });
    }

    private Mat m_finalImage;
    private String save_path, file_name;
    private List<MatOfPoint> m_contours;

    private String[] analysis_headers = { "Index" };

    private static double rock_area = 0.0;

    public ParticleAnalysis(Mat finalImage, String path, String fileName) {
        m_finalImage = new Mat();
        save_path = path;
        file_name = fileName;

        m_contours = new ArrayList<MatOfPoint>();

        Core.copyMakeBorder(finalImage, m_finalImage, 200, 200, 200, 200, Core.BORDER_CONSTANT, ColorBGRValue.BGR_BLACK);

        Imgproc.findContours(m_finalImage, m_contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
    }

    public static void setMask(Mat rock_mask) {
        Mat enclose_mask = new Mat();
        Core.copyMakeBorder(rock_mask, enclose_mask, 10, 10, 10, 10, Core.BORDER_CONSTANT, ColorBGRValue.BGR_BLACK);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(enclose_mask, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        rock_area = Imgproc.contourArea(contours.get(0));

        System.out.println("Area of rock: " + rock_area);
    }

    public void drawEdge() {
        Mat result = MatManager.GraytoRGB(MatManager.flipColor(m_finalImage));

        Imgproc.drawContours(result, m_contours, -1, ColorBGRValue.BGR_RED);

        for (int i = 0; i < m_contours.size(); i++) {
            Imgproc.putText(result, Integer.toString(i), m_contours.get(i).toArray()[0], Imgproc.FONT_HERSHEY_DUPLEX,
                    1.0, ColorBGRValue.BGR_RED);
        }

        FileIO.saveFile(save_path + file_name + "_edge_pad_200.png", result, "png", 9);
    }

    public void drawConvex() {
        Mat result = MatManager.GraytoRGB(MatManager.flipColor(m_finalImage));

        for (int i = 0; i < m_contours.size(); i++) {
            MatOfPoint cont = m_contours.get(i);

            MatOfInt convexHull = new MatOfInt();
            Imgproc.convexHull(cont, convexHull);

            Point[] contourArray = cont.toArray();
            Point[] hullPoints = new Point[convexHull.rows()];
            List<Integer> hullContourIdxList = convexHull.toList();

            for (int z = 0; z < hullContourIdxList.size(); z++) {
                hullPoints[z] = contourArray[hullContourIdxList.get(z)];
            }

            MatOfPoint hullMOP = new MatOfPoint(hullPoints);

            List<MatOfPoint> contr = new ArrayList<MatOfPoint>();
            contr.add(hullMOP);

            Imgproc.drawContours(result, contr, 0, ColorBGRValue.BGR_RED);
            Imgproc.putText(result, Integer.toString(i), m_contours.get(i).toArray()[0], Imgproc.FONT_HERSHEY_DUPLEX, 1.0,
                    ColorBGRValue.BGR_RED);
        }

        FileIO.saveFile(save_path + file_name + "_convex_pad_200.png", result, "png", 9);
    }

    public void drawStraightBoundingRectangle() {
        Mat result = MatManager.GraytoRGB(MatManager.flipColor(m_finalImage));

        for (int i = 0; i < m_contours.size(); i++) {
            Rect rect = Imgproc.boundingRect(m_contours.get(i));
            Imgproc.rectangle(result, rect, ColorBGRValue.BGR_RED);
            Imgproc.putText(result, Integer.toString(i), m_contours.get(i).toArray()[0], Imgproc.FONT_HERSHEY_DUPLEX, 1.0,
                    ColorBGRValue.BGR_RED);
        }

        FileIO.saveFile(save_path + file_name + "_BoundingRectangle_pad_200.png", result, "png", 9);
    }

    public void drawEnclosingCircle() {
        Mat result = MatManager.GraytoRGB(MatManager.flipColor(m_finalImage));

        for (int i = 0; i < m_contours.size(); i++) {
            MatOfPoint cont = m_contours.get(i);
            MatOfPoint2f cont2f = new MatOfPoint2f(cont.toArray());

            Point center = new Point();
            float Radius[] = new float[1];
            Imgproc.minEnclosingCircle(cont2f, center, Radius);

            Imgproc.circle(result, center, (int) Radius[0], ColorBGRValue.BGR_RED);
            Imgproc.putText(result, Integer.toString(i), m_contours.get(i).toArray()[0], Imgproc.FONT_HERSHEY_DUPLEX, 1.0,
                    ColorBGRValue.BGR_RED);
        }

        FileIO.saveFile(save_path + file_name + "_Circle_pad_200.png", result, "png", 9);
    }

    public void drawEllipse() {
        Mat result = MatManager.GraytoRGB(MatManager.flipColor(m_finalImage));

        for (int i = 0; i < m_contours.size(); i++) {
            MatOfPoint cont = m_contours.get(i);
            MatOfPoint2f cont2f = new MatOfPoint2f(cont.toArray());

            if (cont2f.toArray().length >= 5) {
                RotatedRect ellipse = Imgproc.fitEllipse(cont2f);
                Imgproc.ellipse(result, ellipse, ColorBGRValue.BGR_RED);
                Imgproc.putText(result, Integer.toString(i), m_contours.get(i).toArray()[0], Imgproc.FONT_HERSHEY_DUPLEX, 1.0, ColorBGRValue.BGR_RED);
            } 
            else {
                Rect rect = Imgproc.boundingRect(cont);
                Imgproc.rectangle(result, rect, ColorBGRValue.BGR_BLUE);
                Imgproc.putText(result, Integer.toString(i), m_contours.get(i).toArray()[0], Imgproc.FONT_HERSHEY_DUPLEX, 1.0, ColorBGRValue.BGR_BLUE);
            }
        }

        FileIO.saveFile(save_path + file_name + "_Ellipse_pad_200.png", result, "png", 9);

    }

    public void drawFeretBoundingRectangle(){
        Mat result = MatManager.GraytoRGB(MatManager.flipColor(m_finalImage));

        for(int i = 0; i < m_contours.size(); i++){
            MatOfPoint cont = m_contours.get(i);
            MatOfPoint2f cont2f = new MatOfPoint2f(cont.toArray());


            RotatedRect feret = Imgproc.minAreaRect(cont2f);
            Point[] vertices = new Point[4];
            feret.points(vertices);
            for (int j = 0; j < 4; j++){  
                Imgproc.line(result, vertices[j], vertices[(j+1)%4], ColorBGRValue.BGR_RED);
            }
            
            Imgproc.putText(result, Integer.toString(i), feret.center, Imgproc.FONT_HERSHEY_DUPLEX, 1.0, ColorBGRValue.BGR_RED);
        }

        FileIO.saveFile(save_path + file_name + "_FeretRectangle_pad_200.png", result, "png", 9);
    }

    public void generateReport(Boolean[] analysis_selection){
        File file = new File(save_path + file_name + "_analysis.csv"); 

        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile); 

            for(int p = 0; p < analysis_selection.length; p++){
                if(analysis_selection[p]){
                    analysis_headers = ArrayUtils.addAll(analysis_headers, headers.get(p));
                    switch (p) {
                        case 0: // Moment
                            drawEdge();
                            break;
                        case 1: // Convex
                            drawConvex();
                            break;
                        case 2: // Bounding Rectangle
                            drawStraightBoundingRectangle();
                            break;
                        case 3: // Enclosing Circle
                            drawEnclosingCircle();
                            break;
                        case 4: // Fit Ellipse
                            drawEllipse();
                            break;
                        case 6: // Feret's Rectangle
                            drawFeretBoundingRectangle();
                            break;
                        default:
                            break;
                    }
                }
            }

            writer.writeNext(analysis_headers);

            for(int i = 0; i < m_contours.size(); i++){
                MatOfPoint cont = m_contours.get(i);
                MatOfPoint2f cont2f = new MatOfPoint2f(cont.toArray());

                double area = Imgproc.contourArea(cont);
                double perimeter = Imgproc.arcLength(cont2f, true);
                
                String[] data = { Integer.toString(i) };
                for(int j = 0; j < analysis_selection.length; j++){
                    if(analysis_selection[j]){
                        switch (j) {
                            case 0: // Moment
                                Moments m = Imgproc.moments(cont);
                                double area_percentage = area / rock_area;
                                int centroidX = (int) (m.m10 / m.m00) - 200;
                                int centroidY = (int) (m.m01 / m.m00) - 200;

                                data = ArrayUtils.addAll(data, new String[] { Double.toString(area), Double.toString(area_percentage), Integer.toString(centroidX), Integer.toString(centroidY), Double.toString(perimeter) });
                                break;
                            case 1: // Convex
                                MatOfInt convexHull = new MatOfInt();
                                Imgproc.convexHull(cont, convexHull);

                                Point[] contourArray = cont.toArray();
                                Point[] hullPoints = new Point[convexHull.rows()];
                                List<Integer> hullContourIdxList = convexHull.toList();
                                for(int z = 0; z < hullContourIdxList.size(); z++){
                                    hullPoints[z] = contourArray[hullContourIdxList.get(z)];
                                }

                                MatOfPoint hullMOP = new MatOfPoint(hullPoints);

                                double hullArea = Imgproc.contourArea(hullMOP);

                                data = ArrayUtils.addAll(data, new String[] { Double.toString(hullArea), Double.toString(area / hullArea) });
                                break;
                            case 2: // Bounding Rectangle
                                Rect rect = Imgproc.boundingRect(cont);
                
                                double rectX = rect.tl().x - 200;
                                double rectY = rect.tl().y - 200;
                                double rectWidth = rect.width;
                                double rectHeight = rect.height;
                
                                data = ArrayUtils.addAll(data, new String[] { Double.toString(rectX), Double.toString(rectY), Double.toString(rectWidth), Double.toString(rectHeight) });
                                break;
                            case 3: // Enclosing Circle
                                Point center = new Point();
                                float Radius[] = new float[1];
                                Imgproc.minEnclosingCircle(cont2f, center, Radius);
                
                                data = ArrayUtils.addAll(data, new String[] { Double.toString(center.x - 200), Double.toString(center.y - 200), Double.toString(Radius[0]) });
                                break;
                            case 4: // Fit Ellipse
                                if(cont2f.toArray().length >= 5){
                                    RotatedRect ellipse = Imgproc.fitEllipse(cont2f);
                
                                    data = ArrayUtils.addAll(data, new String[] { Double.toString(ellipse.center.x - 200), Double.toString(ellipse.center.y - 200), Double.toString(ellipse.size.height), Double.toString(ellipse.size.width), Double.toString(ellipse.angle) });
                                }
                                else{
                                    data = ArrayUtils.addAll(data, new String[] { "NAN", "NAN", "NAN", "NAN", "NAN" });
                                }
                                break;
                            case 5: // Shape Descriptors
                                double circularity = 4 * Math.PI * area / Math.pow(perimeter, 2);

                                data = ArrayUtils.addAll(data, new String[] { Double.toString(circularity) });
                                break;
                            case 6: // Feret's Rectangle
                                RotatedRect feret = Imgproc.minAreaRect(cont2f);
                                
                                data = ArrayUtils.addAll(data, new String[] { Double.toString(feret.center.x - 200), Double.toString(feret.center.y - 200), Double.toString(feret.angle), Double.toString(feret.size.width), Double.toString(feret.size.height) });
                                break;
                            default:
                                break;
                        }
                    }
                }
                writer.writeNext(data);
            } 

            writer.close(); 
        }
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }
    }
}