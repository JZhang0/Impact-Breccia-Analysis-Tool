package src.main.java.GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import org.opencv.core.Mat;

import src.main.java.Settings;
import utils.File.FileIO;
import utils.File.Hotkeys;
import utils.File.IconLocator;
import utils.File.ImageDropHandler;
import utils.GUI.Zoom;
import utils.GUI.ClickFloodFill;
import utils.GUI.MainImage;
import utils.GUI.Pan;
import utils.Processing.MatManager;
import utils.Processing.MorphManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Point;

public class GUI extends javax.swing.JFrame
{
    private static JFrame frame;
    private static JPanel buttonPanel;
    private static JPanel mainPanel;
    private static Image current_image = null;

    private static double render_scale = 0.0;

    // 0 for eraser, 1 for filler, -1 for none
    private static int editing_mode = -1;

    private static boolean popup_available = true;

    //Create the GUI for the application
    public GUI()
    {
        MorphManager.getInstance();
        
        frame = new JFrame("Impact Breccia Analysis Tool v" + Settings.VERSION);
        frame.setLayout(new BorderLayout());
        frame.setSize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);

        //Create button panel and main panel
        buttonPanel = createButtonPanel();
        mainPanel = createMainPanel();

        //Add button panel and main panel to the frame
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        //Add hotkey support
        new Hotkeys();

        FileIO.resetExportFolder();

        //Set application size
        if (Settings.LAUNCH_MAXIMIZED)
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				FileIO.deleteExportFolder();
			}
		});

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Create the button panel at the top of the screen
    private static JPanel createButtonPanel()
    {
        JPanel topPanel = new JPanel();
        JButton[] buttons = new JButton[18];

        buttons[0] = new HelpGUI();
        buttons[1] = new InvertGUI();
        buttons[2] = new GaussGUI();
        buttons[3] = new ContrastGUI();
        buttons[4] = new RGBGUI();
        buttons[5] = new ThresholdGUI();
        buttons[6] = new DespeckleGUI();
        buttons[7] = new ErosionGUI();
        buttons[8] = new DilationGUI();
        buttons[9] = new AutomateGUI();
        buttons[10] = new FillerGUI();
        buttons[11] = new EraserGUI();
        buttons[12] = new ZoomInGUI();
        buttons[13] = new ZoomOutGUI();
        buttons[14] = new ResetGUI();
        buttons[15] = new UndoGUI();
        buttons[16] = new RedoGUI();
        buttons[17] = new SaveGUI();

        //Initialize the bar's layout
        topPanel.setLayout(new GridLayout(1, buttons.length));

        for (JButton button : buttons)
        {
            topPanel.add(button);
            button.setPreferredSize(new Dimension(85, 85));
            button.setBackground(Color.black);
            button.setFocusPainted(false);
            button.setIcon(new ImageIcon(((ImageIcon) button.getIcon()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        }

        return topPanel;
    }

    //Render the GUI using this image
    public static void render(Mat newImage)
    {
        //Update the current_image to the new image to be displayed by the GUI
        Image image = MatManager.matToImage(newImage);

        //Scale the image to match the height/width of the main panel
        current_image = image.getScaledInstance(-1, mainPanel.getHeight(), Image.SCALE_DEFAULT);

        //Render the new current_image
        render();
    }

    //Render the GUI without updating the current_image
    public static void render()
    {
        //Clear the current image
        mainPanel.removeAll();

        //Rerender the image with what is currently in current_image
        mainPanel.repaint();
    }

    public static Point getImageTopLeft(){
        if (current_image != null){
            int scaled_width = (int) (current_image.getWidth(null) * Zoom.getZoom());
            int scaled_height = (int) (current_image.getHeight(null) * Zoom.getZoom());

            // Calculate the position to center the image
            int x = (mainPanel.getWidth() - scaled_width) / 2;
            int y = (mainPanel.getHeight() - scaled_height) / 2;

            return new Point(x + Pan.getX(), y + Pan.getY());
        }
        else{
            return new Point(0, 0);
        }
    }

    public static double getImageRenderScale(){
        return render_scale;
    }

    private static JPanel createMainPanel()
    {
        JPanel mainPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (current_image != null)
                {
                    // Calculate the scaled size based on the zoom factor
                    int scaled_width = (int) (current_image.getWidth(null) * Zoom.getZoom());
                    int scaled_height = (int) (current_image.getHeight(null) * Zoom.getZoom());

                    render_scale = (double) scaled_height / MainImage.getImageHeight();

                    // Calculate the position to center the image
                    int x = (getWidth() - scaled_width) / 2;
                    int y = (getHeight() - scaled_height) / 2;

                    //Use nearest neighbour to stop blurring pixels together
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

                    //Render the image to the screen
                    g2d.drawImage(current_image, x + Pan.getX(), y + Pan.getY(), scaled_width, scaled_height, null);
                }
                else
                {
                    // Display a JLabel if the image is null
                    JLabel label = new JLabel("Drop image here");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    label.setBounds(0, 0, getWidth(), getHeight());
                    add(label);
                }
            }
        };

        mainPanel.setLayout(new BorderLayout());

        //Add image drag and drop functionality
        mainPanel.setTransferHandler(new ImageDropHandler());

        //Add image pan functionality
        Pan.addMouseListeners(mainPanel);

        //Add image zoom functionality
        Zoom.addMouseListeners(mainPanel);

        //Add image floodfill functionality
        ClickFloodFill.addMouseListeners(mainPanel);

        return mainPanel;
    }

    public static JFrame getFrame()
    {
        return frame;
    }

    public static boolean canCreateGUI()
    {
        return popup_available;
    }

    public static void createGUI()
    {
        //Remove the overlay if it is currently enabled
        ToggleImage.reset();

        //Block new popups from being created
        popup_available = false;
    }

    public static void destroyGUI()
    {
        popup_available = true;
    }

    public static boolean isEditing(int modeToCheck){
        return editing_mode == modeToCheck;
    }

    public static void setEditing(int mode){
        editing_mode = mode;
    }

    public static void changeCursor(int index){
        if(index == -1){
            frame.getRootPane().setCursor(Cursor.getDefaultCursor());
        }
        else{
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(IconLocator.getCursorPath(index));
            Cursor c = toolkit.createCustomCursor(image , new Point(0, 0), "cur");

            frame.getRootPane().setCursor(c);
        }
    }

    public static void changeComponentCursor(int index, Component comp){
        if(index == -1){
            comp.setCursor(Cursor.getDefaultCursor());
        }
        else{
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(IconLocator.getCursorPath(index));
            Cursor c = toolkit.createCustomCursor(image , new Point(0, 0), "cur");
            comp.setCursor(c);
        }
    }
}
