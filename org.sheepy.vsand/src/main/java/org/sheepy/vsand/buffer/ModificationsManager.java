package org.sheepy.vsand.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;
import org.sheepy.vsand.logic.BoardModification;
import org.sheepy.vsand.logic.EShape;
import org.sheepy.vsand.logic.EShapeSize;
import org.sheepy.vsand.model.Material;

public final class ModificationsManager
{
	public static final int BYTE_SIZE = BoardModification.MODIFICATION_BYTE_COUNT * Integer.BYTES;

	private final BufferAdapter adapter;
	private final Deque<BoardModification> modificationQueue = new ArrayDeque<>();
	private final ByteBuffer copyBuffer;

	private int oldX = 0;
	private int oldY = 0;

	public ModificationsManager(Buffer buffer)
	{
		adapter = BufferAdapter.adapt(buffer);
		copyBuffer = BufferUtils.createByteBuffer(BYTE_SIZE);
	}

	public void pushModification(DrawData data)
	{
		var shape = data.shape;
		final int x = data.position.x;
		final int y = data.position.y;

		// We cannot draw a line if nothing moved
		if (shape == EShape.Line && x == oldX && y == oldY)
		{
			shape = EShape.Circle;
		}

		final var modification = new BoardModification(shape, data.size, x, y, oldX, oldY, data.material);
		modificationQueue.add(modification);

		oldX = x;
		oldY = y;
	}

	public void update()
	{
		copyBuffer.clear();
		final BoardModification modif = modificationQueue.pop();
		modif.fillBuffer(copyBuffer);
		copyBuffer.flip();

		adapter.pushData(copyBuffer);
	}

	public boolean isEmpty()
	{
		return modificationQueue.isEmpty();
	}

	public static class DrawData
	{
		public final EShapeSize size;
		public final EShape shape;
		public final Vector2i position;
		public final Material material;

		public DrawData(EShapeSize size, EShape shape, Vector2i position, Material material)
		{
			this.size = size;
			this.shape = shape;
			this.position = position;
			this.material = material;
		}
	}
}
