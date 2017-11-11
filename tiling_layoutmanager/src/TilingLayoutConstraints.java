//tb/1711

public class TilingLayoutConstraints
{
	public int attach_direction;
	public float size_factor;

	public TilingLayoutConstraints(int attach_direction, float size_factor)
	{
		this.attach_direction=attach_direction;
		this.size_factor=size_factor;
	}

	public String toString()
	{
		return (TilingLayoutManager.directionToString(attach_direction)+":"+size_factor);
	}
}//end class TilingLayoutConstraints
//EOF
