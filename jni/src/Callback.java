import java.util.*;

//tb/1802

class Callback
{
	static int done=0;
	static
	{
		System.loadLibrary("Callback");
	}
	private native void register();

	public static void main(String[] args)
	{
		Callback cb=new Callback();
		cb.register();
		while(done==0)
		{
			System.out.println("in Java main() waiting");
			try{Thread.sleep(200);}catch(Exception e){}
		}
	}

	public void callback(long val)
	{
		System.err.println("in callback(), called from c thread: "+val);
		if(val>98){done=1;}
	}
}
//EOF
