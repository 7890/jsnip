//https://www.java-tips.org/other-api-tips-100035/148-jni/1378-simple-example-of-using-the-java-native-interface.html
class HelloWorld
{
	static
	{
		System.loadLibrary("HelloWorld");
	}
	private native void print();

	public static void main(String[] args)
	{
		new HelloWorld().print();
	}
}
//EOF
