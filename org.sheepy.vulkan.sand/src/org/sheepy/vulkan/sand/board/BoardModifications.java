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
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;

/**
 * @author ealrann
 *
 */
public class BoardModifications implements IDescriptor
{
	private float zoom;

	private ByteBuffer copyBuffer;
	private Buffer modificationBuffer;

	private static int oldX = 0;
	private static int oldY = 0;

	private Deque<Modification> boardModifications = new ArrayDeque<>();

	public BoardModifications(LogicalDevice logicalDevice, float zoom)
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

		int size = 17 * Integer.BYTES;

		copyBuffer = MemoryUtil.memAlloc(size);
		modificationBuffer = new Buffer(logicalDevice, size, VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
				VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT);
		modificationBuffer.allocate();
	}

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

		boardModifications.add(new Modification(shape, size, x, y, oldX, oldY, value));

		oldX = x;
		oldY = y;
	}

	public void updateVkBuffer()
	{
		copyBuffer.clear();
		Modification modif = boardModifications.pop();
		modif.fillBuffer(copyBuffer);
		copyBuffer.flip();

		modificationBuffer.fillWithBuffer(copyBuffer);
	}

	public class Modification
	{
		public final EShape shape;
		public final EShapeSize size;
		public final EMaterial value;
		public final int x;
		public final int y;
		public final int oldX;
		public final int oldY;

		Modification(EShape shape, EShapeSize size, int x, int y, int oldX, int oldY,
				EMaterial value)
		{
			this.shape = shape;
			this.size = size;
			this.x = x;
			this.y = y;
			this.value = value;

			this.oldX = oldX;
			this.oldY = oldY;
		}

		public void fillBuffer(ByteBuffer copyBuffer)
		{
			int size = (int) (this.size.getSize() * zoom);

			copyBuffer.putInt(shape.ordinal());
			copyBuffer.putInt(size);

			shape.getBuilder().fillBuffer(copyBuffer, this);

			copyBuffer.putInt(value.ordinal());
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
