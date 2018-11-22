package org.sheepy.vulkan.sand.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.vulkan.model.enumeration.EBufferUsage;
import org.sheepy.vulkan.model.enumeration.EDescriptorType;
import org.sheepy.vulkan.model.enumeration.EMemoryProperty;
import org.sheepy.vulkan.model.enumeration.EShaderStage;
import org.sheepy.vulkan.model.resource.Buffer;
import org.sheepy.vulkan.resource.buffer.BufferAdapter;
import org.sheepy.vulkan.sand.logic.BoardModification;
import org.sheepy.vulkan.sand.logic.EShape;
import org.sheepy.vulkan.sand.logic.EShapeSize;
import org.sheepy.vulkan.sand.model.Material;

public class ModificationsManager
{
	private static final int BYTE_SIZE = BoardModification.MODIFICATION_BYTE_COUNT * Integer.BYTES;

	private final BufferAdapter adapter;
	private final Deque<BoardModification> modificationQueue = new ArrayDeque<>();
	private final ByteBuffer copyBuffer;

	private int oldX = 0;
	private int oldY = 0;

	public ModificationsManager(Buffer buffer)
	{
		adapter = BufferAdapter.adapt(buffer);

		buffer.setSize(BYTE_SIZE);
		buffer.getUsages().add(EBufferUsage.UNIFORM_BUFFER_BIT);
		buffer.getProperties().add(EMemoryProperty.HOST_VISIBLE_BIT);
		buffer.getProperties().add(EMemoryProperty.HOST_COHERENT_BIT);
		buffer.setDescriptorType(EDescriptorType.UNIFORM_BUFFER);
		buffer.getShaderStages().add(EShaderStage.COMPUTE_BIT);

		copyBuffer = MemoryUtil.memAlloc(BYTE_SIZE);
	}

	public void pushModification(EShape shape, EShapeSize size, int x, int y, Material value)
	{
		// We cannot draw a line if nothing moved
		if (shape == EShape.Line && x == oldX && y == oldY)
		{
			shape = EShape.Circle;
		}

		var modification = new BoardModification(shape, size, x, y, oldX, oldY, value);
		modificationQueue.add(modification);

		oldX = x;
		oldY = y;
	}

	public void update()
	{
		copyBuffer.clear();
		BoardModification modif = modificationQueue.pop();
		modif.fillBuffer(copyBuffer);
		copyBuffer.flip();

		adapter.pushData(copyBuffer);
	}

	public boolean isEmpty()
	{
		return modificationQueue.isEmpty();
	}
}
