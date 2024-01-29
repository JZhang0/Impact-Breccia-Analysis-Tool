package src.main.java.GUI;

import javax.swing.JFrame;

import src.main.java.Settings;

public class GUI extends JFrame
{
    public GUI()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Settings.getDefaultWidth(), Settings.getDefaultHeight());
        this.setTitle("Impact Breccia Analysis Tool v" + Settings.getVersion());
        this.setLocationRelativeTo(null);

        //ImagePannel imagePannel = new ImagePannel();

        //this.add(imagePannel);

        this.setVisible(true);
    }
}
