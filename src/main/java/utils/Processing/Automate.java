package utils.Processing;

import src.main.java.GUI.AutoFillBucketGUI;

public class Automate
{
	public static void run(boolean blur, boolean contrast, boolean background, boolean rgb, boolean threshold, boolean despeckle, boolean outliers, boolean holes, boolean invert)
	{
		System.out.println("Beginning automatic processing");
		System.out.println("Blur: " + blur);
		System.out.println("Contrast: " + contrast);
		System.out.println("Subtract background: " + background);
		System.out.println("RGB: " + rgb);
		System.out.println("Threshold: " + threshold);
		System.out.println("Despeckle: " + despeckle);
		System.out.println("Outliers: " + outliers);
		System.out.println("Holes: " + holes);
		System.out.println("Invert: " + invert);

		if (invert)
		{
			//Invert.auto();
			//Invert.save();
		}

		if (blur)
		{
			//Gauss.auto();
			//Gauss.save();
		}

		if (contrast)
		{
			Contrast.auto();
			Contrast.save();
		}

		if (background)
		{
			//BackgroundRemoval.auto();
			//BackgroundRemoval.save();
		}

		if (rgb)
		{
			//RGB.auto();
			//RGB.save();
		}

		if (threshold)
		{
			//Threshold.auto();
			//Threshold.save();
		}

		if (despeckle)
		{
			//Despeckler.auto();
			//Despeckler.save();
		}

		if (outliers)
		{
			//Outliers.auto();
			//Outliers.save();
		}

		if (holes)
		{
			//AutoFillBucket.auto();
			//AutoFillBucketGUI.save();
		}
	}
}
