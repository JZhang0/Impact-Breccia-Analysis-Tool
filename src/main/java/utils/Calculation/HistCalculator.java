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
package utils.Calculation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;

import utils.Processing.MatManager;

public class HistCalculator {
    private Mat m_image;
    private double max, min, m_mean, m_stdDev;

    public HistCalculator(Mat image){
        m_image = image;
        if(m_image.channels() == 3){
            m_image = MatManager.RGBtoGray(m_image);
        }

        min = Core.minMaxLoc(m_image).minVal;
        max = Core.minMaxLoc(m_image).maxVal;

        MatOfDouble mean = new MatOfDouble(), stdDev = new MatOfDouble();
        Core.meanStdDev(m_image, mean, stdDev);
        m_mean = mean.get(0, 0)[0];
        m_stdDev = stdDev.get(0, 0)[0];

        System.out.println("HistCalculator | min: " + min);
        System.out.println("HistCalculator | max: " + max);
        System.out.println("HistCalculator | mean: " + m_mean);
        System.out.println("HistCalculator | stddev: " + m_stdDev + "\n");
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
