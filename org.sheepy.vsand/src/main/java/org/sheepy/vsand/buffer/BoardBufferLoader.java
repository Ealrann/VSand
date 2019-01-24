package org.sheepy.vsand.buffer;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.vulkan.model.enumeration.EBufferUsage;
import org.sheepy.lily.vulkan.model.enumeration.EDescriptorType;
import org.sheepy.lily.vulkan.model.enumeration.EShaderStage;
import org.sheepy.lily.vulkan.model.resource.Buffer;

public class BoardBufferLoader
{
	public static final void load(Buffer buffer, int width, int height)
	{
		int sizeBoard = width * height;
		int sizeChunks = (int) (Math.ceil(width / 32.) * Math.ceil(height / 32.));
		int sizeByte = (sizeBoard + sizeChunks) * Integer.BYTES;

		buffer.setSize(sizeByte);
		buffer.getUsages().add(EBufferUsage.STORAGE_BUFFER_BIT);
		buffer.getUsages().add(EBufferUsage.TRANSFER_SRC_BIT);
		buffer.getUsages().add(EBufferUsage.TRANSFER_DST_BIT);

		buffer.setDescriptorType(EDescriptorType.STORAGE_BUFFER);
		buffer.getShaderStages().add(EShaderStage.COMPUTE_BIT);

		// Fill the board buffer with Void matter (0)
		ByteBuffer bBuffer = MemoryUtil.memCalloc(sizeByte);
		buffer.setData(bBuffer);
	}
}
