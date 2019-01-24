package org.sheepy.vsand.logic.builder;

import java.nio.ByteBuffer;

import org.sheepy.vsand.logic.BoardModification;

public class CircleBuilder implements IShapeBuilder
{
	@Override
	public void fillBuffer(ByteBuffer shapeBuffer, BoardModification modification)
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
