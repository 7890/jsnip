//custom layout manager
//recursively divide the available/remaining space to layout

//inspired by https://juce.com/doc/tutorial_rectangle_advanced
//  Rectangle<int> area (getLocalBounds());
//  header.setBounds (area.removeFromTop (headerFooterHeight));

//see TilingLayoutExample.java for an example usage

//tb/1711

import java.awt.Container;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

import java.util.HashMap;

//=============================================================================
//=============================================================================
public class TilingLayoutManager implements LayoutManager2 //extending '2' since we want to use constraints
{
	public static final int NOT_CONNECTED=0;

	public static final int LEFT=1;
	public static final int RIGHT=2;
	public static final int TOP=3;
	public static final int BOTTOM=4;

	private Rectangle bounds=new Rectangle(0,0,0,0);

	private HashMap<Component,Object> comps=new HashMap<Component,Object>();

	private int minWidth=0;
	private int minHeight=0;
	private int preferredWidth=0;
	private int preferredHeight=0;

	public static boolean debug=true;

//=============================================================================
	public TilingLayoutManager()
	{
		new TilingLayoutManager(false); //default no debug
	}

//=============================================================================
	public TilingLayoutManager(boolean debug)
	{
		this.debug=debug;
	}

//=============================================================================
	public static String directionToString(int direction)
	{
		switch(direction)
		{
			case NOT_CONNECTED: return "NOT CONNECTED";
			case LEFT:          return "LEFT";
			case RIGHT:         return "RIGHT";
			case TOP:           return "TOP";
			case BOTTOM:        return "BOTTOM";
		}
		return "UNKNOWN";
	}

//=============================================================================
	public static void debug(String s)
	{
		if(!debug){return;}
		System.err.println(s);
	}

//=============================================================================
	public String toString()
	{
		return getClass().getName();///
	}

	//Required by LayoutManager
//=============================================================================
	public void addLayoutComponent(String name,Component comp)
	{
	}

	//Required by LayoutManager
//=============================================================================
	public void removeLayoutComponent(Component comp)
	{
		comps.remove(comp);
	}

//=============================================================================
	private void setSizes(Container parent)
	{
		debug("===setSizes() called");

		int nComps=parent.getComponentCount();
		Dimension d=null;

		//Reset preferred/minimum width and height
		preferredWidth=0;
		preferredHeight=0;
		minWidth=0;
		minHeight=0;

		for (int i=0;i<nComps;i++)
		{
			Component c=parent.getComponent(i);
			if (c.isVisible())
			{
				d=c.getPreferredSize();

				preferredWidth+=d.width;
				preferredHeight+=d.height;

				minWidth=Math.max(c.getMinimumSize().width,minWidth);
				minHeight=Math.max(c.getMinimumSize().height,minHeight);
			}
		}
		debug("minWidth: "+minWidth+" minHeight: "+minHeight);
	}

	//Required by LayoutManager
//=============================================================================
	public Dimension preferredLayoutSize(Container parent)
	{
		debug("===preferredLayoutSize() called");

		setSizes(parent);

		//Always add the container's insets!
		Insets insets=parent.getInsets();
		Dimension dim=new Dimension(0,0);
		dim.width=preferredWidth+insets.left+insets.right;
		dim.height=preferredHeight+insets.top+insets.bottom;
		return dim;
	}

	//Required by LayoutManager
//=============================================================================
	public Dimension minimumLayoutSize(Container parent)
	{
		debug("===minimumLayoutSize() called");

		//Always add the container's insets!
		Insets insets=parent.getInsets();
		Dimension dim=new Dimension(0,0);
		dim.width=minWidth+insets.left+insets.right;
		dim.height=minHeight+insets.top+insets.bottom;
		return dim;
	}

	//Required by LayoutManager
	/*
	 This is called when the panel is first displayed, and every time its size changes.
	 Note: You CAN'T assume preferredLayoutSize or minimumLayoutSize will be called

	bounds
	.----------------.
	|                |
	|                |
	|________________|

	compBounds, layout LEFT, 0.4
	.------.
	|      |
	|      |
	|______|

	new bounds
	       .---------.
	       |         |
	       |         |
	       |_________|

	 */
//=============================================================================
	public void layoutContainer(Container parent)
	{
		debug("===layoutContainer() called");

		Insets insets=parent.getInsets();
		int maxWidth=parent.getWidth() - (insets.left+insets.right);
		int maxHeight=parent.getHeight() - (insets.top+insets.bottom);
		int nComps=parent.getComponentCount();

		debug("space to layout ("+nComps+" components): max w: "+maxWidth+" max h: "+maxHeight);

		//create main bounds to be tiled
		//relative to container 0,0 top left
		bounds.setBounds(0,0,maxWidth,maxHeight);

		//iterate child components of this container
		for (int i=0;i<nComps;i++)
		{
			Component c=parent.getComponent(i);
			if (c.isVisible())
			{
				//min/max/preferred sizes of components are ignored
				//Dimension d=c.getPreferredSize();

				//set component bounds, layout
				c.setBounds(
					//create bounds for component, resize bounds for further layout (remaining space)
					createCompBounds(bounds,c)
				);

				//eventually stop laying out if space is used up
				if(bounds.width<=0 || bounds.height<=0)
				{
					debug("/!\\ no space left to layout component. aborting.");
					break;
				}
			}
		}
	}//end layoutContainer()

	//argument bounds will be resized
//=============================================================================
	public Rectangle createCompBounds(Rectangle bounds,Component comp)
	{
		//get the constraints for this component from the hashmap
		TilingLayoutConstraints constraints=(TilingLayoutConstraints)comps.get(comp);

		int comp_width_or_height=0;

		int x=(int)bounds.getLocation().getX();
		int y=(int)bounds.getLocation().getY();
		int h=(int)bounds.getHeight();
		int w=(int)bounds.getWidth();

		//the bounds to be returned
		Rectangle compBounds=new Rectangle(0,0,0,0);

		switch(constraints.attach_direction)
		{
			case LEFT: 
				comp_width_or_height=(int)(constraints.size_factor * w);
				//set component bounds
				compBounds.setBounds(
					x,
					y,
					comp_width_or_height,
					h
				);
				//set container bounds to remaining space
				bounds.setBounds(
					x+comp_width_or_height,
					y,
					w-comp_width_or_height,
					h
				);
				break;
			case RIGHT:
				comp_width_or_height=(int)(constraints.size_factor * w);
				compBounds.setBounds(
					x+w-comp_width_or_height,
					y,
					comp_width_or_height,
					h
				);
				bounds.setBounds(
					x,
					y,
					w-comp_width_or_height,
					h
				);
				break;
			case TOP:
				comp_width_or_height=(int)(constraints.size_factor * h);
				compBounds.setBounds(
					x,
					y,
					w,
					comp_width_or_height
				);
				bounds.setBounds(
					x,
					y+comp_width_or_height,
					w,
					h-comp_width_or_height
				);
				break;
			case BOTTOM:
				comp_width_or_height=(int)(constraints.size_factor * h);
				compBounds.setBounds(
					x,
					y+h-comp_width_or_height,
					w,
					comp_width_or_height
				);
				bounds.setBounds(
					x,
					y,
					w,
					h-comp_width_or_height
				);
				break;
		}// end switch()
		return compBounds;
	}//end createCompBounds()

	//Required by LayoutManager2
	//Adds the specified component to the layout, using the specified constraint object.
//=============================================================================
	public void addLayoutComponent(Component comp,Object constraints)
	{
		debug("===addLayoutComponent() called");
		debug("constraints: "+constraints);

		//put component to hashmap along with constraints
		//in order to retrieve constraints by component at layout time in layoutContainer()
		comps.put(comp,constraints);
	}

	//Required by LayoutManager2
	/*
	Returns the alignment along the x axis.
	This specifies how the component would like to be aligned relative to other components. 
	The value should be a number between 0 and 1 
	   where 0 represents alignment along the origin, 
	   1 is aligned the furthest away from the origin, 
	   0.5 is centered, etc.
	*/
//=============================================================================
	public float getLayoutAlignmentX(Container target)
	{
		return 0;//left
	}

	//Required by LayoutManager2
	//Returns the alignment along the y axis.
//=============================================================================
	public float getLayoutAlignmentY(Container target)
	{
		return 0;//top
	}

	//Required by LayoutManager2
	//Invalidates the layout, indicating that if the layout manager has cached information it should be discarded.
//=============================================================================
	public void invalidateLayout(Container target)
	{
	}

	//Required by LayoutManager2
	//Calculates the maximum size dimensions for the specified container, given the components it contains.
//=============================================================================
	public Dimension maximumLayoutSize(Container target)
	{
		return new Dimension(1,1);///fake
	}
} //end class TilingLayoutManager
//EOF
