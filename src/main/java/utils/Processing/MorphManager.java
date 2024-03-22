package utils.Processing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class MorphManager {
    private static MorphManager MorphManager = null;

    private static int m_elementType = Imgproc.MORPH_RECT;
    private static int m_kernelSize = 5;
    private static Mat m_kernel;

    public static synchronized MorphManager getInstance(){
        if(MorphManager == null){
            MorphManager = new MorphManager();
            updateKernel(m_elementType, m_kernelSize);
        }

        return MorphManager;
    }

    public static void updateKernel(int elementType, int kernelSize){
        m_elementType = elementType;
        m_kernelSize = kernelSize;
        // m_kernel = Imgproc.getStructuringElement(m_elementType, new Size(2 * m_kernelSize + 1, 2 * m_kernelSize + 1), new Point(m_kernelSize, m_kernelSize));
        m_kernel = Imgproc.getStructuringElement(m_elementType, new Size(2 * m_kernelSize + 1, 2 * m_kernelSize + 1), new Point(-1, -1));
    }

    public static void updateKernel(int elementType, int width, int height){
        m_elementType = elementType;
        m_kernelSize = width / 2000 + height / 1000;
        System.out.println("Morph m_kernelSize: " + m_kernelSize);
        m_kernel = Imgproc.getStructuringElement(m_elementType, new Size(2 * m_kernelSize + 1, 2 * m_kernelSize + 1), new Point(m_kernelSize, m_kernelSize));
    }

    /**
     * Erode away(remove) the boundaries of foreground object.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param srcImage Input image
     * @param iterations Numer of operations to apply
     */
    public Mat erosion(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.erode(srcImage, destImage, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    /**
     * Add pixels to the boundaries of objects in an image.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param Input Input image
     * @param iterations Numer of operations to apply
     */
    public Mat dilation(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.dilate(srcImage, destImage, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    /**
     * Erosion followed by dilation. It is useful in removing noise.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param srcImage Input image
     * @param iterations Numer of operations to apply
     */
    public Mat opening(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.morphologyEx(srcImage, destImage, Imgproc.MORPH_OPEN, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }

    /**
     * Dilation followed by Erosion. It is useful in closing small holes inside the foreground objects.<p>
     * 
     * See https://docs.opencv.org/4.x/d9/d61/tutorial_py_morphological_ops.html for details.
     * @param srcImage Input image
     * @param iterations Numer of operations to apply
     */
    public Mat closing(Mat srcImage, int iterations){
        Mat destImage = MatManager.createMatWithProperty(srcImage);

        Imgproc.morphologyEx(srcImage, destImage, Imgproc.MORPH_CLOSE, m_kernel, new Point(-1, -1), iterations);

        return destImage;
    }
}