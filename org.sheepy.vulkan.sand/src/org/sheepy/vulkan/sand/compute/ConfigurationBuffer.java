package org.sheepy.vulkan.sand.compute;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.command.SingleTimeCommand;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.sand.board.EMaterial;

public class ConfigurationBuffer implements IAllocable
{
	private static final int MAX_MATERIAL_NUMBER = 16;

	private Buffer configBuffer;
	private LogicalDevice logicalDevice;
	private CommandPool commandPool;

	public ConfigurationBuffer(LogicalDevice logicalDevice, CommandPool commandPool)
	{
		this.logicalDevice = logicalDevice;
		this.commandPool = commandPool;

		int size = (MAX_MATERIAL_NUMBER * 8);
		int byteSize = size * Integer.BYTES;

		configBuffer = new Buffer(logicalDevice, byteSize,
				VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT | VK_BUFFER_USAGE_TRANSFER_DST_BIT,
				VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
		configBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
				VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		configBuffer.allocate();

		ByteBuffer bBuffer = MemoryUtil.memAlloc((int) configBuffer.getSize());
		for (EMaterial material : EMaterial.values())
		{
			bBuffer.putInt(material.isStatic ? 1 : 0);
			bBuffer.putInt(material.density);
			bBuffer.putInt(material.runoff);
			bBuffer.putFloat(material.viscosity);
			bBuffer.putFloat(material.r);
			bBuffer.putFloat(material.g);
			bBuffer.putFloat(material.b);

			// Padding1
			bBuffer.putFloat(0);
		}
		bBuffer.flip();

		Buffer staggingBuffer = new Buffer(logicalDevice, configBuffer.getSize(),
				VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
				VK_MEMORY_PROPERTY_HOST_COHERENT_BIT | VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT);
		staggingBuffer.allocate();
		staggingBuffer.fillWithBuffer(bBuffer);

		SingleTimeCommand stc = new SingleTimeCommand(commandPool,
				logicalDevice.getQueueManager().getComputeQueue())
		{
			@Override
			protected void doExecute(MemoryStack stack, VkCommandBuffer commandBuffer)
			{
				Buffer.copyBuffer(vkCommandBuffer, staggingBuffer.getId(), configBuffer.getId(),
						(int) configBuffer.getSize());
			}
		};
		stc.execute();

		staggingBuffer.free();
		MemoryUtil.memFree(bBuffer);
	}

	public Buffer getBuffer()
	{
		return configBuffer;
	}

	@Override
	public void free()
	{
		configBuffer.free();
	}
}
