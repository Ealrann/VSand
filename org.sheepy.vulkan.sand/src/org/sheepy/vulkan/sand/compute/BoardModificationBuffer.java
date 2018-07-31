package org.sheepy.vulkan.sand.compute;

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
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.sand.board.BoardModification;
import org.sheepy.vulkan.sand.board.EMaterial;
import org.sheepy.vulkan.sand.board.EShape;
import org.sheepy.vulkan.sand.board.EShapeSize;

/**
 * @author ealrann
 *
 */
public class BoardModificationBuffer implements IAllocable, IDescriptor
{
	private float zoom;

	private ByteBuffer copyBuffer;
	private Buffer modificationBuffer;

	private static int oldX = 0;
	private static int oldY = 0;

	private Deque<BoardModification> boardModifications = new ArrayDeque<>();

	private static final int BYTE_SiZE = BoardModification.MODIFICATION_BYTE_COUNT * Integer.BYTES;

	public BoardModificationBuffer(LogicalDevice logicalDevice, float zoom)
	{
		this.zoom = zoom;
		int maxShapeSize = 1;
		for (EShapeSize shapeSize : EShapeSize.values())
		{
			if (maxShapeSize < shapeSize.getSize())
			{
				maxShapeSize = shapeSize.getSize();
			}
		}

		modificationBuffer = new Buffer(logicalDevice, BYTE_SiZE,
				VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
				VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT);
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		copyBuffer = MemoryUtil.memAlloc(BYTE_SiZE);
		modificationBuffer.allocate();
	}

	@Override
	public void free()
	{
		MemoryUtil.memFree(copyBuffer);
		modificationBuffer.free();
	}

	public boolean isEmpty()
	{
		return boardModifications.isEmpty();
	}

	public void pushModification(EShape shape, EShapeSize size, int x, int y, EMaterial value)
	{

		// We cannot draw a line if nothing moved
		if (shape == EShape.Line && x == oldX && y == oldY)
		{
			shape = EShape.Circle;
		}

		boardModifications.add(new BoardModification(shape, size, x, y, oldX, oldY, value));

		oldX = x;
		oldY = y;
	}

	public void updateVkBuffer()
	{
		copyBuffer.clear();
		BoardModification modif = boardModifications.pop();
		modif.fillBuffer(copyBuffer, zoom);
		copyBuffer.flip();

		modificationBuffer.fillWithBuffer(copyBuffer);
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
