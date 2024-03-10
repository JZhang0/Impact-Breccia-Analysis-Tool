package src.main.java.GUI;

import javax.swing.*;

import src.main.java.Settings;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;

public class GUI extends javax.swing.JFrame
{
    private static JFrame frame;
    private static JPanel buttonPanel;
    private static JPanel mainPanel;

    //Create the GUI for the application
    public GUI()
    {
        frame = new JFrame("Impact Breccia Analysis Tool v" + Settings.VERSION);
        frame.setLayout(new BorderLayout());
        frame.setSize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);

        //Create button panel and main panel
        buttonPanel = createButtonPanel();
        mainPanel = createMainPanel();

        //Add button panel and main panel to the frame
        frame.add(buttonPanel, BorderLayout.NORTH);
        //frame.add(createLabelPanel(), BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.CENTER);

        //Set application size
        if (Settings.LAUNCH_MAXIMIZED)
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Create the button panel at the top of the screen
    private static JPanel createButtonPanel()
    {
        JPanel topPanel = new JPanel();
        JButton[] buttons = new JButton[12];

        buttons[0] = new ButtonTMP();
        buttons[1] = new ButtonTMP();
        buttons[2] = new RGBGUI(frame);
        buttons[3] = new ButtonTMP();
        buttons[4] = new ButtonTMP();
        buttons[5] = new ButtonTMP();
        buttons[6] = new ButtonTMP();
        buttons[7] = new ButtonTMP();
        buttons[8] = new ButtonTMP();
        buttons[9] = new ButtonTMP();
        buttons[10] = new ButtonTMP();
        buttons[11] = new ButtonTMP();

        //Initialize the bar's layout
        topPanel.setLayout(new GridLayout(1, buttons.length));

        for (JButton button : buttons)
        {
            topPanel.add(button);
            adjustButtonSize(button, frame, buttons.length);
        }

        return topPanel;
    }

	/*private static JPanel createLabelPanel() {
		// Create labels for buttons
		JLabel label1 = new JLabel("Button 1 Label");
		JLabel label2 = new JLabel("Button 2 Label");
		JLabel label3 = new JLabel("Button 3 Label");
		JLabel label4 = new JLabel("Button 3 Label");
		JLabel label5 = new JLabel("Button 3 Label");
		JLabel label6 = new JLabel("Button 3 Label");
		JLabel label7 = new JLabel("Button 3 Label");
		JLabel label8 = new JLabel("Button 3 Label");
		JLabel label9 = new JLabel("Button 3 Label");
		JLabel label10 = new JLabel("Button 3 Label");
		JLabel label11 = new JLabel("Button 3 Label");
		JLabel label12 = new JLabel("Button 3 Label");

		// Add labels to a panel with box layout
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(label1);
		labelPanel.add(label2);
		labelPanel.add(label3);
		labelPanel.add(label4);
		labelPanel.add(label5);
		labelPanel.add(label6);
		labelPanel.add(label7);
		labelPanel.add(label8);
		labelPanel.add(label9);
		labelPanel.add(label10);
		labelPanel.add(label11);
		labelPanel.add(label12);

		return labelPanel;
	}*/

    //Create the main panel that displays the image
    private static JPanel createMainPanel()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JLabel helloLabel = new JLabel("Drop image here");

        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(helloLabel, BorderLayout.CENTER);

        return mainPanel;
    }

    //Update button size
    private static void adjustButtonSize(JButton button, JFrame frame, int button_total) {
        Icon icon = button.getIcon();
        ImageIcon imageIcon = (ImageIcon) icon;
        Image image = imageIcon.getImage();
        int size = Math.min(frame.getHeight(), frame.getWidth()) / button_total; // Divide by the number of buttons
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        button.setIcon(new ImageIcon(scaledImage));
        button.setPreferredSize(new Dimension(size, size));
    }

    //This sets the main area to the image sample given
    public static void render(byte[] image)
    {
        // Replace main panel with an image
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);

        mainPanel.removeAll();
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
