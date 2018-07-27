package org.sheepy.vulkan.sand.board.builder;

import java.nio.ByteBuffer;

import org.sheepy.vulkan.sand.board.BoardModifications.Modification;

public class CircleBuilder implements IShapeBuilder
{
	@Override
	public void fillBuffer(ByteBuffer shapeBuffer, Modification modification)
	{
		shapeBuffer.putInt(modification.x);
		shapeBuffer.putInt(modification.y);

		int halfSize = modification.size.getSize() >> 1; // Divide by 2

		shapeBuffer.putInt(modification.x - halfSize);
		shapeBuffer.putInt(modification.y - halfSize);
		shapeBuffer.putInt(modification.x + halfSize);
		shapeBuffer.putInt(modification.y + halfSize);

		// Unused here
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);

		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
	}
}
