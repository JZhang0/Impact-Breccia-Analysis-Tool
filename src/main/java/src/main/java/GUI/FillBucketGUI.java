package src.main.java.GUI;



import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillBucketGUI extends JButton
{
	public FillBucketGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(18)));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				act();
			}
		});
	}

	public static void act()
	{
		if (MainImage.exists() && GUI.canCreateGUI())
		{
			System.out.println("bucket");
		}
	}
}
