package test.vulkan.gameoflife;

public class Board
{
	private int width;
	private int height;

	private int size;

	private boolean[] values;

	public Board(int width, int height)
	{
		this.width = width;
		this.height = height;

		size = width * height;

		values = new boolean[size];
	}

	public void setValue(int x, int y, boolean value)
	{
		values[y * width + x] = value;
	}

	public void activate(int x, int y)
	{
		setValue(x, y, true);
	}
	
	public boolean isActivated(int arrayLocation)
	{
		return values[arrayLocation];
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public boolean[] getValues()
	{
		return values;
	}
}
