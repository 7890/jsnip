//========================================================================
//========================================================================
class LPropsTest
{
	public int 	ai=23;
	public long	al=24;
	public float 	af=78.90f;
	public double	ad=79.909090909d;
	public String 	as="hallo velo";
	public char	ac='@';
	public boolean 	ab=false;

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
	public LPropsTest(String[] args)
	{
		System.err.println("values before load:");
		//member variables after instance created
		System.err.println("ai="+ai);
		System.err.println("al="+al);
		System.err.println("af="+af);
		System.err.println("ad="+ad);
		System.err.println("as="+as);
		System.err.println("ac="+ac);
		System.err.println("ab="+ab);
		System.err.println("apriv="+apriv);

		System.err.println("\nvalues found by dumpObject:");
		System.err.println(LProps.dumpObject(this));

		System.err.println("storing object members to file a.properties");
		LProps.store("a.properties",this);

		if(!LProps.load(args[0],this))
		{
			System.err.println("could not load properties file "+args[0]);
		}

		System.err.println("\nvalues after load:");

		//member variables afer propertis file loaded
		System.err.println("ai="+ai);
		System.err.println("al="+al);
		System.err.println("af="+af);
		System.err.println("ad="+ad);
		System.err.println("as="+as);
		System.err.println("ac="+ac);
		System.err.println("ab="+ab);
		System.err.println("apriv="+apriv);
	}//end constructor
}//end class LPropsTest

/*
ai=23
al=24
af=78.9
ad=79.909090909
as=hallo velo
ac=@
ab=false
apriv=99
*/
//EOF
