package src.main.java.GUI;

import src.main.java.Settings;
import utils.File.IconLocator;
import utils.GUI.MainImage;
import utils.Processing.Contrast;

import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.ImageIcon;
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

public class ContrastGUI extends JButton
{
	private static JLabel label_alpha, label_beta, label_gamma;
	private static JSlider slider_alpha, slider_beta, slider_gamma;
	private static JTextField text_field_alpha, text_field_beta, text_field_gamma;
	private static JButton button_auto, button_save;

	//The amount that we are adjusting the contrast by
	//These are equivalent to the alpha and beta values saved in the Contrast class
	private static double contrast_adjustment_value_alpha;
	private static int contrast_adjustment_value_beta;
	private static double contrast_adjustment_value_gamma;

	private static boolean getSetting = false;

	private static void getContrastSetting()
	{
		getSetting = true;
		contrast_adjustment_value_alpha = Contrast.getAlpha();
		contrast_adjustment_value_beta = Contrast.getBeta();
		contrast_adjustment_value_gamma = Contrast.getGamma();

		text_field_alpha.setText(String.format("%.2f", contrast_adjustment_value_alpha));
		text_field_beta.setText(String.valueOf(contrast_adjustment_value_beta));
		text_field_gamma.setText(String.format("%.2f", contrast_adjustment_value_gamma));

		slider_alpha.setValue((int) (Contrast.getAlpha() * 10));
		slider_beta.setValue(Contrast.getBeta() + 100);
		int int_gamma = (int) (contrast_adjustment_value_gamma <= 1.0 ? contrast_adjustment_value_gamma * 30 : contrast_adjustment_value_gamma <= 5.0 ? (contrast_adjustment_value_gamma - 1) * 12.5 + 30 : contrast_adjustment_value_gamma + 75);
		slider_gamma.setValue(int_gamma);

		GUI.render(Contrast.adjustConstrast(contrast_adjustment_value_alpha, contrast_adjustment_value_beta, contrast_adjustment_value_gamma));
		getSetting = false;
	}


	public ContrastGUI()
	{
		setIcon(new ImageIcon(IconLocator.getIconPath(0)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			ContrastGUI.launch();
		}
	}

	public static void launch()
	{
		JDialog dialog = new JDialog(GUI.getFrame(), "Contrast adjustment", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		//Label alpha
		label_alpha = new JLabel("Contrast");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(label_alpha, constraints);

		//Label beta
		label_beta = new JLabel("Brightness");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(label_beta, constraints);

		//Label gamma
		label_gamma = new JLabel("Gamma");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		dialog.add(label_gamma, constraints);

		//Slider alpha
		slider_alpha = new JSlider(Settings.CONTRAST_ALPHA_MIN, Settings.CONTRAST_ALPHA_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		dialog.add(slider_alpha, constraints);

		//Slider beta
		slider_beta = new JSlider(Settings.CONTRAST_BETA_MIN, Settings.CONTRAST_BETA_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		dialog.add(slider_beta, constraints);

		//Slider gamma
		slider_gamma = new JSlider(Settings.CONTRAST_GAMMA_MIN, Settings.CONTRAST_GAMMA_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		dialog.add(slider_gamma, constraints);

		//Textfield alpha
		text_field_alpha = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(text_field_alpha, constraints);

		//Textfield beta
		text_field_beta = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(text_field_beta, constraints);

		//Textfield gamma
		text_field_gamma = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		dialog.add(text_field_gamma, constraints);

		//Automate button
		button_auto = new JButton("Automate");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		dialog.add(button_auto, constraints);

		//Confirmation button
		button_save = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);

		// Set defaults
		getContrastSetting();

		//Updating the slider
		slider_alpha.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
				contrast_adjustment_value_alpha = (double) slider_alpha.getValue() / 10;
				text_field_alpha.setText(String.format("%.2f", contrast_adjustment_value_alpha));

				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting() && !getSetting){
					GUI.render(Contrast.adjustConstrast(contrast_adjustment_value_alpha, contrast_adjustment_value_beta, contrast_adjustment_value_gamma));
				}
			}
        });
		slider_beta.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
				contrast_adjustment_value_beta = slider_beta.getValue() - 100;
				text_field_beta.setText(String.valueOf(contrast_adjustment_value_beta));

				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting() && !getSetting){
					GUI.render(Contrast.adjustConstrast(contrast_adjustment_value_alpha, contrast_adjustment_value_beta, contrast_adjustment_value_gamma));
				}
			}
        });
		slider_gamma.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
				double gamma = (double) slider_gamma.getValue();
				if(gamma <= 30){
					contrast_adjustment_value_gamma = gamma / 30;
				}
				else if(gamma <= 80){
					contrast_adjustment_value_gamma = (gamma - 30) * 0.08 + 1;
				}
				else if(gamma <= 100){
					contrast_adjustment_value_gamma = gamma - 75;
				}
				text_field_gamma.setText(String.format("%.2f", contrast_adjustment_value_gamma));

				JSlider source = (JSlider) e.getSource();
				if(!source.getValueIsAdjusting() && !getSetting){
					GUI.render(Contrast.adjustConstrast(contrast_adjustment_value_alpha, contrast_adjustment_value_beta, contrast_adjustment_value_gamma));
				}
			}
        });

		//Override the documentListener so we can control what happens when the textfield changes
		text_field_alpha.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                double alpha = Double.parseDouble(text_field_alpha.getText());
				if (text_field_alpha.getText().matches("^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") && alpha * 10 >= Settings.CONTRAST_ALPHA_MIN && alpha * 10 <= Settings.CONTRAST_ALPHA_MAX)
				{
					slider_alpha.setValue((int) (alpha * 10));
				}
            }
        });
		text_field_beta.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
				int beta = Integer.parseInt(text_field_beta.getText());
				if (text_field_beta.getText().matches("\\d+") && beta + 100 >= Settings.CONTRAST_BETA_MIN && beta + 100 <= Settings.CONTRAST_BETA_MAX)
				{
					slider_beta.setValue(beta + 100);
				}
            }
        });
		text_field_gamma.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
				double gamma = Double.parseDouble(text_field_gamma.getText());
				int int_gamma = (int) (gamma <= 1.0 ? gamma * 30 : gamma <= 5.0 ? (gamma - 1) * 12.5 + 30 : gamma + 75);
				if (text_field_gamma.getText().matches("^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$") && int_gamma >= Settings.CONTRAST_GAMMA_MIN && int_gamma * 10 <= Settings.CONTRAST_GAMMA_MAX)
				{

					slider_gamma.setValue(int_gamma);
				}
            }
        });

		//Automate
		button_auto.addActionListener(e ->
		{
			Contrast.auto();
			getContrastSetting();
		});

		//Save
		button_save.addActionListener(e ->
		{
			Contrast.save();
			GUI.destroyGUI();
			dialog.dispose();
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to invert colours
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				Contrast.reset();
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
			}
		});

		dialog.setSize(400, 200);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
