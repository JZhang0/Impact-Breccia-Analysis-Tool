package src.main.java;

import javax.swing.JFrame;

public class GUI extends JFrame
{
    GUI()
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
