package org.sheepy.vulkan.sand.board.builder;

import java.nio.IntBuffer;

import org.sheepy.vulkan.sand.board.EShapeSize;

public class SquareBuilder implements IShapeBuilder
{

	@Override
	public void fillBuffer(IntBuffer shapeBuffer, EShapeSize size, int value)
	{
		for (int index = 0; index < size.getSize() * size.getSize(); index++)
		{
			shapeBuffer.put(index, value);
		}
	}
}
