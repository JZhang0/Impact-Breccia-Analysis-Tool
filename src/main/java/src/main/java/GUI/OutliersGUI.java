package src.main.java.GUI;



import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutliersGUI extends JButton
{
	public OutliersGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(7)));
		addActionListener(e -> act());
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			System.out.println("outliers");
		}
	}
}
