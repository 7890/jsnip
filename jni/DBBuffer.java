import java.nio.*;

//tb/1802

class DBBuffer
{
	//in bytes
	static long buf_size=16 * 4;
	static
	{
		System.loadLibrary("DBBuffer");
	}

	private native ByteBuffer create(long size);

	public static void main(String[] args)
	{
		DBBuffer dbb=new DBBuffer();

		//let c library create a direct byte buffer and return it to bb
		ByteBuffer bb=dbb.create(buf_size);

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
			System.out.println(fb.position() + " -> " + fb.get());
		}
	}//end main()
}
//EOF
