import java.nio.*;

//tb/1802

class DBBuffer
{
	//in bytes
	static long buf_size=48000 * 4;
	static
	{
		System.err.println(""+System.currentTimeMillis());
		System.loadLibrary("DBBuffer");
	}

	private native ByteBuffer create(long size);

	public static void main(String[] args)
	{
		System.err.println(""+System.currentTimeMillis());
		DBBuffer dbb=new DBBuffer();

		System.err.println(""+System.currentTimeMillis());
		//let c library create a direct byte buffer and return it to bb
		ByteBuffer bb=dbb.create(buf_size);
		System.err.println(""+System.currentTimeMillis());

		//the buffer was created BIG_ENDIAN (default)
		//buffer contains LITTLE_ENDIAN data, toggle "view"
		bb.order(ByteOrder.LITTLE_ENDIAN);

		//show properties
		System.out.println(bb + "\nis direct: "+bb.isDirect());
		if (bb.order() == ByteOrder.LITTLE_ENDIAN)
		{
			System.out.println("byte order: little-endian");
		}
		else
		{
			System.out.println("byte order: big-endian!");
		}

		//get a float view onto byte buffer (expecting floats)
		FloatBuffer fb=((ByteBuffer)bb.rewind()).asFloatBuffer();
		while (fb.hasRemaining())
		{
			float f=fb.get();
//			System.out.println(fb.position() + " -> " + fb.get());
		}
		System.err.println(""+System.currentTimeMillis());
	}//end main()
}
//EOF
