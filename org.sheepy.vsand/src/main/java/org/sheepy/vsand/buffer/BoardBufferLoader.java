package org.sheepy.vsand.buffer;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.vulkan.model.resource.Buffer;

public class BoardBufferLoader
{
	public static final void load(Buffer buffer, int width, int height)
	{
		int sizeBoard = width * height;
		int sizeChunks = (int) (Math.ceil(width / 32.) * Math.ceil(height / 32.));
		int sizeByte = (sizeBoard + sizeChunks) * Integer.BYTES;

		buffer.setSize(sizeByte);

		// Fill the board buffer with Void matter (0)
		ByteBuffer bBuffer = MemoryUtil.memCalloc(sizeByte);
		buffer.setData(bBuffer);
	}
}
