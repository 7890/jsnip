//tb/1802

public class CreateObject
{
	static
	{
		System.loadLibrary("CreateObject");
	}

	private native MyClass getMyClassObject();

	public static void main(String[] args)
	{
		CreateObject co=new CreateObject();
		MyClass mc=co.getMyClassObject();
		System.out.println("mc x: "+mc.x+" y: "+mc.y);
	}//end main()

	//this class will be instatiated from a C / JNI routine and given back to the Java process
	public static class MyClass
	{
		//some member variables that can be initialized with the 2nd constructor
		public String x="";
		public long y=42;
		public MyClass()
		{
			System.out.println("in MyClassConstructorA");
		}
		public MyClass(String a, long b)
		{
			x=a;
			y=b;
			System.out.println("in MyClassConstructorB " + a + " " + b);
		}
	}//end inner class MyClass
}//end class CreateObject
//EOF
