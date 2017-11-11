import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.AbstractAction;

//testing ScreenImage.java
//tb/1711

public class ScreenImageExample
{
	public static void main(String[] args)
	{
		//create arbitrary gui
		JFrame mainframe=new JFrame("ScreenImage Example");

		JPanel mainpanel=new JPanel();
		JPanel subpanel=new JPanel();

		BorderLayout blm=new BorderLayout();
		GridLayout glm=new GridLayout(3,1);

		mainpanel.setLayout(blm);
		subpanel.setLayout(glm);

		subpanel.add(new JButton("a"));
		subpanel.add(new JButton("b"));
		subpanel.add(new JButton("c"));

		mainpanel.add(subpanel,BorderLayout.WEST);

		JButton snap=new JButton(new AbstractAction("Press to create image")
		{
			public void actionPerformed(ActionEvent ev)
			{
				//snapshot the mainpanel and save to mainpanel.jpg
				BufferedImage img=ScreenImage.createImage(mainpanel);
				try
				{
					ScreenImage.writeImage(img,"mainpanel.png");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		mainpanel.add(snap,BorderLayout.CENTER);
		mainpanel.add(new JLabel("bar"),BorderLayout.SOUTH);

		mainframe.add(mainpanel);
//		mainframe.setSize(300, 300);
		mainframe.pack();
		mainframe.setLocationRelativeTo(null);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setVisible(true);
	}//end main()
}//end class ScreenImageExample
//EOF
