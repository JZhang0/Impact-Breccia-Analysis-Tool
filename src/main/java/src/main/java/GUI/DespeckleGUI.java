package src.main.java.GUI;



import src.main.java.Settings;
import utils.Processing.Despeckler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DespeckleGUI extends JButton
{
	private static int despeckle_adjustment_value;

	public DespeckleGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(6)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			launch();
		}
	}



	public static void launch()
	{
		JFrame frame = GUI.getFrame();
		JDialog dialog = new JDialog(frame, "Despeckle", true);
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		//Slider
		JSlider slider = new JSlider(Settings.DESPECKLE_MIN, Settings.DESPECKLE_MAX);
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
		text_field.setText(String.valueOf((Despeckler.getKernel())));
		slider.setValue((Despeckler.getKernel()));

		//Render the image with despeckling using whatever k size is currently in memory
		GUI.render(Despeckler.despeckle(Despeckler.getKernel()));

		//Updating the slider
		slider.addChangeListener(e ->
		{
			despeckle_adjustment_value = slider.getValue();

			text_field.setText(String.valueOf(despeckle_adjustment_value));
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
				if (text_field.getText().matches("\\d+") && Integer.parseInt(text_field.getText()) >= Settings.DESPECKLE_MIN && Integer.parseInt(text_field.getText()) <= Settings.DESPECKLE_MAX)
				{
					despeckle_adjustment_value = Integer.parseInt(text_field.getText());
					GUI.render(Despeckler.despeckle(despeckle_adjustment_value));
				}
			}
		});

		//Save
		button.addActionListener(e ->
		{
			Despeckler.save();
			GUI.destroyGUI();
			dialog.dispose();
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to despeckle
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
