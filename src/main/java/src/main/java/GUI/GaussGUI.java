package src.main.java.GUI;

import src.main.java.Settings;
import utils.Processing.Gauss;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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

public class GaussGUI extends JButton
{
	//The amount that we are adjusting the contrast by
	//This is equivalent to the kernel value saved in the Gauss class
	private static int gauss_adjustment_value;

	public GaussGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(1)));
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

		//Slider
		JSlider slider = new JSlider(Settings.GAUSS_MIN, Settings.GAUSS_MAX);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		dialog.add(slider, constraints);

		//Textfield
		JTextField text_field = new JTextField(10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(text_field, constraints);

		//Confirmation button
		JButton button = new JButton("Confirm");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		dialog.add(button, constraints);

		//Set defaults
		text_field.setText(String.valueOf((Gauss.getKernel())));
		slider.setValue((Gauss.getKernel()));

		//Render the contrast with whatever alpha value is currently in memory
		GUI.render(Gauss.addGauss(Gauss.getKernel()));

		//Updating the slider
		slider.addChangeListener(e ->
		{
			gauss_adjustment_value = slider.getValue();

			text_field.setText(String.valueOf(gauss_adjustment_value));
		});

		//Override the documentListener so we can control what happens when the textfield changes
		text_field.getDocument().addDocumentListener(new DocumentListener() {
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
				if (text_field.getText().matches("\\d+") && Integer.parseInt(text_field.getText()) >= Settings.GAUSS_MIN && Integer.parseInt(text_field.getText()) <= Settings.GAUSS_MAX)
				{
					gauss_adjustment_value = Integer.parseInt(text_field.getText());
					GUI.render(Gauss.addGauss(gauss_adjustment_value));
				}
			}
		});

		//Save
		button.addActionListener(e ->
		{
			Gauss.save();
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
