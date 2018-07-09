package org.sheepy.vulkan.sand.board;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkDescriptorBufferInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkWriteDescriptorSet;
import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;

/**
 * CPU side Board
 * 
 * @author ealrann
 *
 */
public class BoardModifications implements IDescriptor
{
	private int width;
	private int height;

	private ByteBuffer copyBuffer;
	private Buffer modificationBuffer;

	private Deque<Modification> boardModifications = new ArrayDeque<>();

	public BoardModifications(LogicalDevice logicalDevice, int width, int height)
	{
		this.width = width;
		this.height = height;

		int maxShapeSize = 1;
		for (EShapeSize shapeSize : EShapeSize.values())
		{
			if (maxShapeSize < shapeSize.getSize())
			{
				maxShapeSize = shapeSize.getSize();
			}
		}

		int size = 8 * 4;

		copyBuffer = MemoryUtil.memAlloc(size);
		modificationBuffer = Buffer.alloc(logicalDevice, size, VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
				VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT);
	}

	@Override
	public void free()
	{
		MemoryUtil.memFree(copyBuffer);
		modificationBuffer.free();
	}

	public boolean isDirty()
	{
		return boardModifications.isEmpty() == false;
	}

	public void pushModification(EShape shape, EShapeSize size, int x, int y, EMaterial value)
	{
		boardModifications.add(new Modification(shape, size, x, y, value));
	}

	public void updateVkBuffer()
	{
		Modification modif = boardModifications.pop();
		modif.fillBuffer(copyBuffer);
		copyBuffer.flip();

		modificationBuffer.fillWithBuffer(copyBuffer);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	private class Modification
	{
		EShape shape;
		EShapeSize size;
		EMaterial value;
		int x;
		int y;

		Modification(EShape shape, EShapeSize size, int x, int y, EMaterial value)
		{
			this.shape = shape;
			this.size = size;
			this.x = x;
			this.y = y;
			this.value = value;
		}

		public void fillBuffer(ByteBuffer copyBuffer)
		{
			copyBuffer.putInt(shape.ordinal());
			copyBuffer.putInt(size.getSize());
			copyBuffer.putInt(value.id);
			copyBuffer.putInt(x);
			copyBuffer.putInt(y);
		}
	}

	public Buffer getBuffer()
	{
		return modificationBuffer;
	}

	@Override
	public VkDescriptorSetLayoutBinding allocLayoutBinding(MemoryStack stack)
	{
		VkDescriptorSetLayoutBinding res = VkDescriptorSetLayoutBinding.callocStack(stack);
		res.descriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
		res.descriptorCount(1);
		res.stageFlags(VK_SHADER_STAGE_COMPUTE_BIT);
		return res;
	}

	@Override
	public VkWriteDescriptorSet allocWriteDescriptor(MemoryStack stack)
	{
		VkDescriptorBufferInfo.Buffer bufferInfo = VkDescriptorBufferInfo.callocStack(1, stack);
		bufferInfo.buffer(modificationBuffer.getId());
		bufferInfo.offset(0);
		bufferInfo.range(modificationBuffer.getSize());

		VkWriteDescriptorSet descriptorWrite = VkWriteDescriptorSet.callocStack(stack);
		descriptorWrite.sType(VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET);
		descriptorWrite.dstArrayElement(0);
		descriptorWrite.descriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
		descriptorWrite.pBufferInfo(bufferInfo);
		descriptorWrite.pImageInfo(null); // Optional
		descriptorWrite.pTexelBufferView(null); // Optional
		return descriptorWrite;
	}

	@Override
	public VkDescriptorPoolSize allocPoolSize(MemoryStack stack)
	{
		VkDescriptorPoolSize poolSize = VkDescriptorPoolSize.callocStack(stack);
		poolSize.type(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
		poolSize.descriptorCount(1);
		return poolSize;
	}

}
