package org.sheepy.vsand.util;

public enum EShapeSize
{
	ES1(2),
	ES2(4),
	ES3(8),
	ES4(16),
	ES5(32),
	ES6(64),
	ES7(128),
	ES8(256);
	
	private final int size;
	
	EShapeSize(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
}
