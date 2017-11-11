import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

//testing TilingLayoutManager.java
//tb/1711

public class TilingLayoutExample
{
	public static JPanel createPanel(String name, Color color)
	{
		JPanel p=new JPanel();
		p.setName(name);
		p.setBackground(color);
		/*
		.setMinimumSize(new Dimension(50,50));
		.setPreferredSize(new Dimension(100,300));
		.setMaximumSize(new Dimension(300,300));
		*/
		return p;
	}

	public static TilingLayoutConstraints createConstraint(int direction,float size)
	{
		return new TilingLayoutConstraints(direction,size);
	}

	public static void main(String[] args)
	{
		JFrame mainframe = new JFrame("TilingLayout Example");
		JPanel mainpanel = new JPanel();

		TilingLayoutManager tlm=new TilingLayoutManager(true);//debug
		mainpanel.setLayout(tlm);

		mainpanel.add(createPanel("red",   Color.red),   createConstraint(tlm.LEFT,   0.5f));
		mainpanel.add(createPanel("green", Color.green), createConstraint(tlm.RIGHT,  0.5f));
		mainpanel.add(createPanel("blue",  Color.blue),  createConstraint(tlm.TOP,    0.5f));
		mainpanel.add(createPanel("black", Color.black), createConstraint(tlm.BOTTOM, 0.5f));

		//create the same layout on another panel
		JPanel subpanel=new JPanel();
		subpanel.setLayout(tlm);
		subpanel.add(createPanel("red",   Color.red),   createConstraint(tlm.LEFT,   0.5f));
		subpanel.add(createPanel("green", Color.green), createConstraint(tlm.RIGHT,  0.5f));
		subpanel.add(createPanel("blue",  Color.blue),  createConstraint(tlm.TOP,    0.5f));
		subpanel.add(createPanel("black", Color.black), createConstraint(tlm.BOTTOM, 0.5f));

		//use all remaining space on mainpanel to put subpanel
		mainpanel.add(subpanel,createConstraint(tlm.LEFT,1.0f));

		//this will fail (no more space to layout)
		mainpanel.add(new JLabel("foo"),createConstraint(tlm.LEFT,1.0f));

		mainframe.add(mainpanel);
		mainframe.setSize(300, 300);
//		mainframe.pack();
		mainframe.setLocationRelativeTo(null);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setVisible(true);
	}//end main()
}//end class TilingLayoutExample
//EOF
