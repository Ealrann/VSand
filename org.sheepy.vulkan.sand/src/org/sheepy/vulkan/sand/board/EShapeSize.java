package org.sheepy.vulkan.sand.board;

public enum EShapeSize
{
	ES1(1),
	ES2(2),
	ES3(4),
	ES4(8),
	ES5(16),
	ES6(32),
	ES7(64),
	ES8(128);
	
	private int size;
	
	private EShapeSize(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
}
