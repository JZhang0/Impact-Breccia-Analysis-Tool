package src.main.java.GUI;

import src.main.java.Settings;
import utils.Processing.Contrast;

import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JTextField;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ContrastGUI extends JButton
{
	//The amount that we are adjusting the contrast by
	//These are equivalent to the alpha and beta values saved in the Contrast class
	private static int contrast_adjustment_value_alpha;
	private static int contrast_adjustment_value_beta;

	public ContrastGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(0)));
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
		JLabel label_alpha = new JLabel("Contrast");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(label_alpha, constraints);

		//Label beta
		JLabel label_beta = new JLabel("Brightness");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(label_beta, constraints);

		//Slider alpha
		JSlider slider_alpha = new JSlider(Settings.CONTRAST_ALPHA_MIN, Settings.CONTRAST_ALPHA_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		dialog.add(slider_alpha, constraints);

		//Slider beta
		JSlider slider_beta = new JSlider(Settings.CONTRAST_BETA_MIN, Settings.CONTRAST_BETA_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		dialog.add(slider_beta, constraints);

		//Textfield alpha
		JTextField text_field_alpha = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		dialog.add(text_field_alpha, constraints);

		//Textfield beta
		JTextField text_field_beta = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(text_field_beta, constraints);

		//Automate button
		JButton button_auto = new JButton("Automate");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		dialog.add(button_auto, constraints);

		//Confirmation button
		JButton button_save = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		dialog.add(button_save, constraints);

		//Set defaults
		text_field_alpha.setText(String.valueOf((int) (Contrast.getAlpha() * 100)));
		slider_alpha.setValue((int) (Contrast.getAlpha() * 100));
		text_field_beta.setText(String.valueOf(Contrast.getBeta()));
		slider_beta.setValue(Contrast.getBeta());

		//Render the contrast with whatever alpha and beta values are currently in memory
		GUI.render(Contrast.adjustConstrast(Contrast.getAlpha(), Contrast.getBeta()));

		//Updating the slider
		slider_alpha.addChangeListener(e ->
		{
			contrast_adjustment_value_alpha = slider_alpha.getValue();

			text_field_alpha.setText(String.valueOf(contrast_adjustment_value_alpha));
		});
		slider_beta.addChangeListener(e ->
		{
			contrast_adjustment_value_beta = slider_beta.getValue();

			text_field_beta.setText(String.valueOf(contrast_adjustment_value_beta));
		});

		//Override the documentListener so we can control what happens when the textfield changes
		text_field_alpha.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateTextFieldValue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateTextFieldValue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateTextFieldValue();
			}

			//When the textfield changes to something valid, update the image rendered on the GUI
			private void updateTextFieldValue() {
				if (text_field_alpha.getText().matches("\\d+") && Integer.parseInt(text_field_alpha.getText()) >= Settings.CONTRAST_ALPHA_MIN && Integer.parseInt(text_field_alpha.getText()) <= Settings.CONTRAST_ALPHA_MAX)
				{
					contrast_adjustment_value_alpha = Integer.parseInt(text_field_alpha.getText());
					GUI.render(Contrast.adjustConstrast((double) contrast_adjustment_value_alpha / 100, contrast_adjustment_value_beta));
					//slider_alpha.setValue(contrast_adjustment_value_alpha);
					//System.out.println("Contrast alpha slider has been updated");
				}
			}
		});
		text_field_beta.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateTextFieldValue();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateTextFieldValue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateTextFieldValue();
			}

			//When the textfield changes to something valid, update the image rendered on the GUI
			private void updateTextFieldValue() {
				if (text_field_beta.getText().matches("\\d+") && Integer.parseInt(text_field_beta.getText()) >= Settings.CONTRAST_BETA_MIN && Integer.parseInt(text_field_beta.getText()) <= Settings.CONTRAST_BETA_MAX)
				{
					contrast_adjustment_value_beta = Integer.parseInt(text_field_beta.getText());
					GUI.render(Contrast.adjustConstrast((double) contrast_adjustment_value_alpha / 100, contrast_adjustment_value_beta));
					//slider_beta.setValue(contrast_adjustment_value_beta);
				}
			}
		});

		//Automate
		button_auto.addActionListener(e ->
		{
			GUI.render(Contrast.auto());
			contrast_adjustment_value_alpha = (int) (Contrast.getAlpha() * 100);
			contrast_adjustment_value_beta = Contrast.getBeta();
			text_field_alpha.setText(String.valueOf(contrast_adjustment_value_alpha));
			text_field_beta.setText(String.valueOf(contrast_adjustment_value_beta));
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
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
			}
		});

		dialog.setSize(400, 100);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
