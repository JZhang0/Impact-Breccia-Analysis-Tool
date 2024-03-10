package utils.Calculation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;

public class HistCalculator {
    private Mat m_image;
    private double max, min, m_mean, m_stdDev;

    public HistCalculator(Mat image){
        m_image = image;

        min = Core.minMaxLoc(m_image).minVal;
        max = Core.minMaxLoc(m_image).maxVal;

        MatOfDouble mean = new MatOfDouble(), stdDev = new MatOfDouble();
        Core.meanStdDev(m_image, mean, stdDev);
        m_mean = mean.get(0, 0)[0];
        m_stdDev = stdDev.get(0, 0)[0];

        System.out.println("min: " + min);
        System.out.println("max: " + max);
        System.out.println("mean: " + m_mean);
        System.out.println("stddev: " + m_stdDev);
    }

    public double getMin(){
        return min;
    }

    public double getMax(){
        return max;
    }

    public double getMean(){
        return m_mean;
    }

    public double getStdDev(){
        return m_stdDev;
    }


}
