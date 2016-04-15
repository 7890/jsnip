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

	public LPropsTest(){}

	public static void main(String[] args) throws Exception
	{
		if(args.length!=1)
		{
			System.err.println("first arg: <properties file>\n");
			System.exit(1);
		}

		LPropsTest test=new LPropsTest();
		
		System.err.println("values before load:");

		//member variables after instance created
		System.err.println("ai="+test.ai);
		System.err.println("al="+test.al);
		System.err.println("af="+test.af);
		System.err.println("ad="+test.ad);
		System.err.println("as="+test.as);
		System.err.println("ac="+test.ac);
		System.err.println("ab="+test.ab);
		System.err.println("apriv="+test.apriv);

		if(!LProps.load(args[0],test))
		{
			System.err.println("could not load properties file");
		}

		System.err.println("\nvalues after load:");

		//member variables afer propertis file loaded
		System.err.println("ai="+test.ai);
		System.err.println("al="+test.al);
		System.err.println("af="+test.af);
		System.err.println("ad="+test.ad);
		System.err.println("as="+test.as);
		System.err.println("ac="+test.ac);
		System.err.println("ab="+test.ab);
		System.err.println("apriv="+test.apriv);
	}//end main
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
