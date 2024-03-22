package src.main.java.GUI;



import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PencilGUI extends JButton
{
	public PencilGUI()
	{
		setIcon(new ImageIcon(FilterGUI.getFilepath(9)));
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
			System.out.println("pencil");
		}
	}
}
