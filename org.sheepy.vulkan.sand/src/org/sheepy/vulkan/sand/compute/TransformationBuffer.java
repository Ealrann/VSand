package org.sheepy.vulkan.sand.compute;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.command.SingleTimeCommand;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.resource.IResource;
import org.sheepy.vulkan.sand.board.EMaterial;
import org.sheepy.vulkan.sand.board.ETransformation;

public class TransformationBuffer implements IResource
{
	private static final int TRANSFORMATIONS_ARRAY_SIZE = EMaterial.MAX_MATERIAL_NUMBER
			* EMaterial.MAX_MATERIAL_NUMBER
			* Integer.BYTES;
	
	private Buffer transformationBuffer;
	private LogicalDevice logicalDevice;

	public TransformationBuffer(LogicalDevice logicalDevice)
	{
		this.logicalDevice = logicalDevice;

		transformationBuffer = new Buffer(logicalDevice, TRANSFORMATIONS_ARRAY_SIZE,
				VK_BUFFER_USAGE_STORAGE_BUFFER_BIT | VK_BUFFER_USAGE_TRANSFER_DST_BIT,
				VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
		transformationBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
				VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
	}

	@Override
	public void allocate(MemoryStack stack, CommandPool commandPool)
	{
		transformationBuffer.allocate();

		ByteBuffer bBuffer = MemoryUtil.memAlloc(TRANSFORMATIONS_ARRAY_SIZE);
		int[] transfoArray = ETransformation.toArray();
		for (int i = 0; i < transfoArray.length; i++)
		{
			bBuffer.putInt(transfoArray[i]);
		}
		bBuffer.flip();

		Buffer staggingBuffer = new Buffer(logicalDevice, TRANSFORMATIONS_ARRAY_SIZE,
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
				Buffer.copyBuffer(vkCommandBuffer, staggingBuffer.getId(),
						transformationBuffer.getId(), (int) transformationBuffer.getSize());
			}
		};
		stc.execute();

		transformationBuffer.flush();

		staggingBuffer.free();
		MemoryUtil.memFree(bBuffer);
	}

	public Buffer getBuffer()
	{
		return transformationBuffer;
	}

	@Override
	public void free()
	{
		transformationBuffer.free();
	}
}
