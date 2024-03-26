package src.main.java.GUI;

import utils.Processing.Automate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AutomateGUI extends JButton
{
	public AutomateGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(14)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			GUI.createGUI();
			AutomateGUI.launch();
		}
	}

	public static void launch()
	{
		// Add label at the top
		//JLabel label = new JLabel("Select which filters should be applied. Filters will be applied in order.");
		JDialog dialog = new JDialog(GUI.getFrame(), "Automatic processing", true);
		dialog.setLayout(new GridLayout(1, 8));
		boolean[] algs_to_run = {true, true, true, true, true, true, true, true, true};
		JToggleButton[] buttons = new JToggleButton[9];

		buttons[3] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(4)));
		buttons[0] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(3)));
		buttons[1] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(1)));
		buttons[2] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(0)));
		buttons[4] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(2)));
		buttons[5] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(5)));
		buttons[6] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(6)));
		buttons[7] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(7)));
		buttons[8] = new JToggleButton(new ImageIcon(FilterGUI.getFilepath(8)));
		JButton button8 = new JButton("<html><center>Confirm</center></html>");

		for (int i = 0; i < 9; i++)
		{
			//Update the size of the button icons
			buttons[i].setIcon(new ImageIcon(((ImageIcon) buttons[i].getIcon()).getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH)));

			buttons[i].setPreferredSize(new Dimension(85, 85));
			buttons[i].setFocusable(false);
			buttons[i].setSelected(!algs_to_run[i]);
			dialog.add(buttons[i]);

			int finalI = i;
			buttons[i].addActionListener(e ->
			{
				buttons[finalI].setSelectedIcon(buttons[finalI].getDisabledIcon());
				algs_to_run[finalI] = !buttons[finalI].getModel().isSelected();
			});
		}

		button8.setPreferredSize(new Dimension(85, 85));
		dialog.add(button8);

		button8.addActionListener(e ->
		{
			Automate.run(algs_to_run[1], algs_to_run[2], algs_to_run[3], algs_to_run[4], algs_to_run[5], algs_to_run[6], algs_to_run[7], algs_to_run[8], algs_to_run[0]);
			GUI.render(MainImage.getImageMat());
			GUI.destroyGUI();
			dialog.dispose();
		});

		//Detect when the dialog closes. Reset the preview if the user doesn't want to launch the automate alg
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				GUI.render(MainImage.getImageMat());
				GUI.destroyGUI();
			}
		});

		dialog.setSize(900, 125);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
