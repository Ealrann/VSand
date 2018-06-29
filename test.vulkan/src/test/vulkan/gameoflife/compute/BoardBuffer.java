package test.vulkan.gameoflife.compute;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkDescriptorBufferInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkWriteDescriptorSet;
import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;

import test.vulkan.gameoflife.Board;

public class BoardBuffer implements IDescriptor
{
	private Buffer buffer;
	
	private int width;
	private int height;
	
	public BoardBuffer(LogicalDevice logicalDevice, Board board)
	{
		this.width = board.getWidth();
		this.height = board.getHeight();
		
		long size = board.getWidth() * board.getHeight();
		long sizeBuffer = board.getWidth() * board.getHeight() * Integer.BYTES;
		
		buffer = Buffer.alloc(logicalDevice, sizeBuffer,
				VK_BUFFER_USAGE_STORAGE_BUFFER_BIT | VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
				VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
		ByteBuffer byteBuffer = MemoryUtil.memAlloc((int) sizeBuffer);
		IntBuffer intBufferView = byteBuffer.asIntBuffer();
		for (int i = 0; i < size; i++)
		{
			intBufferView.put(board.isActivated(i)? 1 : 0);
		}
		intBufferView.flip();

		buffer.fillWithBuffer(byteBuffer, sizeBuffer);
	}

	@Override
	public VkDescriptorSetLayoutBinding allocLayoutBinding(MemoryStack stack)
	{
		VkDescriptorSetLayoutBinding res = VkDescriptorSetLayoutBinding.callocStack(stack);
		res.descriptorType(VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
		res.descriptorCount(1);
		res.stageFlags(VK_SHADER_STAGE_COMPUTE_BIT);
		return res;
	}

	@Override
	public VkWriteDescriptorSet allocWriteDescriptor(MemoryStack stack)
	{
		VkDescriptorBufferInfo.Buffer bufferInfo = VkDescriptorBufferInfo.callocStack(1, stack);
		bufferInfo.buffer(buffer.getId());
		bufferInfo.offset(0);
		bufferInfo.range(buffer.getSize());

		VkWriteDescriptorSet descriptorWrite = VkWriteDescriptorSet.callocStack(stack);
		descriptorWrite.sType(VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET);
		descriptorWrite.dstArrayElement(0);
		descriptorWrite.descriptorType(VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
		descriptorWrite.pBufferInfo(bufferInfo);
		descriptorWrite.pImageInfo(null); // Optional
		descriptorWrite.pTexelBufferView(null); // Optional
		return descriptorWrite;
	}

	@Override
	public VkDescriptorPoolSize allocPoolSize(MemoryStack stack)
	{
		VkDescriptorPoolSize poolSize = VkDescriptorPoolSize.callocStack(stack);
		poolSize.type(VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
		poolSize.descriptorCount(1);
		return poolSize;
	}

	public Buffer getBuffer()
	{
		return buffer;
	}
	
	@Override
	public void free()
	{
		buffer.free();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
