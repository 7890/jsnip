class LPropsTest
{
	public int 	a=23;
	public float 	b=78.90f;
	public String 	c="hallo velo";
	public boolean 	d=false;

	public LPropsTest(){}

	public static void main(String[] args) throws Exception
	{
		if(args.length!=1)
		{
			System.err.println("first arg: <properties file>\n");
			System.err.println("possible .properties file keys:");
			System.err.println("a=<int value>");
			System.err.println("b=<float value>");
			System.err.println("c=<string value>");
			System.err.println("d=<boolean value>\n");
			System.exit(1);
		}

		LPropsTest test=new LPropsTest();
		
		System.err.println("values before load:");

		//member variables after instance created
		System.err.println("var a: "+test.a);
		System.err.println("var b: "+test.b);
		System.err.println("var c: "+test.c);
		System.err.println("var d: "+test.d);

		if(!LProps.load(args[0],test))
		{
			System.err.println("could not load properties file");
		}

		System.err.println("\nvalues after load:");

		//member variables afer propertis file loaded
		System.err.println("var a: "+test.a);
		System.err.println("var b: "+test.b);
		System.err.println("var c: "+test.c);
		System.err.println("var d: "+test.d);
	}//end main
}//end class LPropsTest

/*
$ cat a.properties

#props

a=55
b=66.77
c=check this out \nnow
d=true

*/
//EOF
