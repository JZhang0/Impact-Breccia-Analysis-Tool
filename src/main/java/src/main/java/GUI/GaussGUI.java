package src.main.java.GUI;

import src.main.java.Settings;
import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.Processing.BlurFilter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JTextField;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GaussGUI extends JButton
{
	private static JLabel label;
	private static JSlider slider;
	private static JTextField text_field;
	private static JButton button_auto, button_save;

	//The amount that we are adjusting the contrast by
	//This is equivalent to the kernel value saved in the Gauss class
	private static int gauss_adjustment_value;

	private static boolean getSetting = false;

	private static void getGaussianSetting(){
		getSetting = true;
		gauss_adjustment_value = BlurFilter.getGaussianKernel();

		if(gauss_adjustment_value == 0){
			text_field.setText(String.valueOf(gauss_adjustment_value));
		}
		else{
			text_field.setText(String.valueOf(gauss_adjustment_value * 2 - 1));
		}
		slider.setValue(gauss_adjustment_value);

		GUI.render(BlurFilter.addGauss(gauss_adjustment_value));
		getSetting = false;
	}

	public GaussGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(1)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			GaussGUI.launch();
		}
	}

	public static void launch()
	{
		JFrame frame = GUI.getFrame();
		JDialog dialog = new JDialog(frame, "Apply Gaussian blur", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		//Label
		label = new JLabel("Gaussian kernel size ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(label, constraints);

		//Slider
		slider = new JSlider(Settings.GAUSS_MIN, Settings.GAUSS_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		dialog.add(slider, constraints);

		//Textfield
		text_field = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(text_field, constraints);

		//Automate button
		button_auto = new JButton("Automate");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_auto, constraints);

		//Confirmation button
		button_save = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);

		//Set defaults
		getGaussianSetting();

		//Updating the slider
		slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
				gauss_adjustment_value = slider.getValue();

				if(gauss_adjustment_value == 0){
					text_field.setText(String.valueOf(gauss_adjustment_value));
				}
				else{
					text_field.setText(String.valueOf(gauss_adjustment_value * 2 - 1));
				}

				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting() && !getSetting){
					GUI.render(BlurFilter.addGauss(gauss_adjustment_value));
				}
			}
        });

		text_field.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                int ksize = Integer.parseInt(text_field.getText()) > 0 ? (Integer.parseInt(text_field.getText()) + 1) / 2 : 0;
				if (text_field.getText().matches("\\d+") && ksize >= Settings.GAUSS_MIN && ksize <= Settings.GAUSS_MAX)
				{
					slider.setValue(ksize);
				}
            }
        });

		//Automate
		button_auto.addActionListener(e ->
		{
			BlurFilter.auto_gaussian();
			getGaussianSetting();
		});

		//Save
		button_save.addActionListener(e ->
		{
			BlurFilter.save_gaussian();
			GUI.destroyGUI();
			dialog.dispose();
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to invert colours
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				BlurFilter.resetGaussian();
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
			}
		});

		dialog.setSize(500, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
