package org.sheepy.vulkan.sand.compute;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.command.SingleTimeCommand;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.resource.IResource;

/**
 * This Buffer will store the movement decisions during the board computation.
 * 
 * For each cell :
 * 
 * 4 bit for the possible next location: Up, Down, Right, Left 4 bit that
 * describes what neighbors want to come: Up, Down, Right, Left 8 bit for the
 * decision priority. The highest score will be favouritize : Up, Down, Right,
 * Left
 * 
 * 
 * @author ealrann
 *
 */
public class BoardDecisionBuffer implements IResource
{
	private LogicalDevice logicalDevice;

	private Buffer stagingBuffer;
	private Buffer buffer;
	private ByteBuffer javaBuffer;

	private Byte[] scoreArray = {
			0, 1, 2, 3
	};

	Random random = new Random();

	public BoardDecisionBuffer(LogicalDevice logicalDevice, int width, int height)
	{
		this.logicalDevice = logicalDevice;

		int byteSize = width * height * Integer.BYTES;

		stagingBuffer = new Buffer(logicalDevice, byteSize, VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
				VK_MEMORY_PROPERTY_HOST_COHERENT_BIT | VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT);

		buffer = new Buffer(logicalDevice, byteSize,
				VK_BUFFER_USAGE_TRANSFER_DST_BIT | VK_BUFFER_USAGE_STORAGE_BUFFER_BIT,
				VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
		buffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT, VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
	}

	@Override
	public void allocate(MemoryStack stack, CommandPool commandPool)
	{
		javaBuffer = MemoryUtil.memAlloc((int) buffer.getSize());

		stagingBuffer.allocate();
		buffer.allocate();

		updateRandomness();

		SingleTimeCommand stc = new SingleTimeCommand(commandPool,
				logicalDevice.getQueueManager().getGraphicQueue())
		{
			@Override
			protected void doExecute(MemoryStack stack, VkCommandBuffer commandBuffer)
			{
				updateBuffer(commandBuffer);
			}
		};
		stc.execute();
	}

	public void updateRandomness()
	{
		for (int i = 0; i < buffer.getSize() / Integer.BYTES; i++)
		{
			int res = 0;

			shuffleScoreArray();

			int scoreByte = 0;

			scoreByte = scoreByte | scoreArray[0];
			scoreByte = scoreByte | (scoreArray[1] << 2);
			scoreByte = scoreByte | (scoreArray[2] << 4);
			scoreByte = scoreByte | (scoreArray[3] << 6);

			res = scoreByte << 8;

			javaBuffer.putInt(res);
		}
		javaBuffer.flip();

		stagingBuffer.fillWithBuffer(javaBuffer);
	}

	public void updateBuffer(VkCommandBuffer commandBuffer)
	{
		Buffer.copyBuffer(commandBuffer, stagingBuffer.getId(), buffer.getId(),
				(int) buffer.getSize());

		buffer.flush();
	}

	private void shuffleScoreArray()
	{
		random.nextInt();
		for (int i = 0; i < scoreArray.length; i++)
		{
			int change = i + random.nextInt(scoreArray.length - i);
			Byte tmp = scoreArray[i];
			scoreArray[i] = scoreArray[change];
			scoreArray[change] = tmp;
		}
	}

	public Buffer getBuffer()
	{
		return buffer;
	}

	@Override
	public void free()
	{
		stagingBuffer.free();
		buffer.free();
	}
}
