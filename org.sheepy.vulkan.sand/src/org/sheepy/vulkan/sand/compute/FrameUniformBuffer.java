package org.sheepy.vulkan.sand.compute;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.device.LogicalDevice;

public class FrameUniformBuffer implements IAllocable
{
	private Buffer uniformBuffer;
	private Random random = new Random(System.nanoTime() + 1859320);
	private ByteBuffer jBuffer;

	public FrameUniformBuffer(LogicalDevice logicalDevice)
	{
		int size = Integer.BYTES;
		jBuffer = MemoryUtil.memAlloc(size);
		uniformBuffer = new Buffer(logicalDevice, size, VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
				VK_MEMORY_PROPERTY_HOST_COHERENT_BIT | VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT);
		uniformBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
				VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
	}

	public Buffer getBuffer()
	{
		return uniformBuffer;
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		uniformBuffer.allocate();

		update();
	}

	public void update()
	{
		int rNumber = random.nextInt();
		
		jBuffer.putInt(rNumber);
		jBuffer.flip();
		
		uniformBuffer.fillWithBuffer(jBuffer);
	}

	@Override
	public void free()
	{
		MemoryUtil.memFree(jBuffer);
		uniformBuffer.free();
	}
}
