import java.util.Vector;

//========================================================================
//========================================================================
class LPropsTest
{
	public int 	ai=23;
	public long	al=24;
	public float 	af=78.90f;
	public double	ad=79.909090909d;
	public String 	as="hallo velo ☕ x";
	public char	ac='@';
	public boolean 	ab=false;
	public Vector	av=new Vector();

	private int	apriv=0;

//========================================================================
	public static void main(String[] args) throws Exception
	{
		if(args.length!=1)
		{
			System.err.println("first arg: <properties file>\n");
			System.exit(1);
		}
		new LPropsTest(args);
	}

//========================================================================
	public static void printVector(Vector v)
	{
		for(int i=0;i<v.size();i++)
		{
			System.err.println("av["+i+"]="+v.get(i));
		}
	}

//========================================================================
	public void printCurrentValues()
	{
		System.err.println("ai="+ai);
		System.err.println("al="+al);
		System.err.println("af="+af);
		System.err.println("ad="+ad);
		System.err.println("as="+as+".EOL");
		System.err.println("ac="+ac);
		System.err.println("ab="+ab);
		printVector(av);

		System.err.println("apriv="+apriv);
	}

//========================================================================
	public LPropsTest(String[] args)
	{
		//adding initial values to vector
		//values from any properties file(s) will be *added* to the *existing* tokens
		//(the vector is not reset if av= keys are found)
		//the user of LProps has to take care of removing double entries, re-order etc.
		av.add("foo");
		av.add("bar");

		//LProps will always put string tokens to a vector.
		//the user of LProps has to take care of the destination datatype, for instance
		//converting to integer or float.

		System.err.println("values before loading properties from file (hardcoded):");
		//member variables after instance created
		printCurrentValues();

		System.err.println("\nvalues found by dumpObject (test private visibility):");
		System.err.println(LProps.dumpObject(this));

		System.err.println("storing current public object member key=value pairs to file a.properties");
		LProps.store("a.properties",this);

		System.err.println("\nprint from file"+args[0]+":");
		LProps.print(args[0]);

		System.err.println("\nloading argument properties file "+args[0]);

		if(!LProps.load(args[0],this))
		{
			System.err.println("could not load properties file "+args[0]);
		}

		System.err.println("\nvalues after loading "+args[0]+":");
		//member variables afer propertis file loaded
		printCurrentValues();

		System.err.println("\ntesting LPropsMan:");
		System.err.println("\ntrying to load 'a.properties', 'b.properties', '~/.na.prop'...");

		LPropsMan p=new LPropsMan();
		p.add("a.properties");//current directory
		p.add("b.properties");
		p.add(System.getProperty("user.home")+"/.na.prop");
		p.load(this);

		System.err.println("\nafter overloading a.properties < b.properties < ~/.na.prop:");
//		System.err.println(LProps.dumpObject(this));
		printCurrentValues();
	}//end constructor
}//end class LPropsTest
//EOF
