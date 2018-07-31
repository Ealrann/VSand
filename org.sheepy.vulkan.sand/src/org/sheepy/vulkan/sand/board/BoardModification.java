package org.sheepy.vulkan.sand.board;

import java.nio.ByteBuffer;

public class BoardModification
{
	public static final int MODIFICATION_BYTE_COUNT = 17;

	public final EShape shape;
	public final EShapeSize size;
	public final EMaterial value;
	public final int x;
	public final int y;
	public final int oldX;
	public final int oldY;

	public BoardModification(EShape shape, EShapeSize size, int x, int y, int oldX, int oldY, EMaterial value)
	{
		this.shape = shape;
		this.size = size;
		this.x = x;
		this.y = y;
		this.value = value;

		this.oldX = oldX;
		this.oldY = oldY;
	}

	public void fillBuffer(ByteBuffer copyBuffer, float zoom)
	{
		int size = (int) (this.size.getSize() * zoom);

		copyBuffer.putInt(shape.ordinal());
		copyBuffer.putInt(size);

		shape.getBuilder().fillBuffer(copyBuffer, this);

		copyBuffer.putInt(value.ordinal());
	}
}