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

public class Board implements IDescriptor
{
	private int width;
	private int height;

	private int size;
	private int xOrigin;
	private int yOrigin;

	private boolean[] values;

	private Buffer buffer;

	public Board(LogicalDevice logicalDevice, int width, int height)
	{
		this.width = width;
		this.height = height;

		size = width * height;
		xOrigin = (int) (width / 2f);
		yOrigin = (int) (height / 2f);

		values = new boolean[size];

		long sizeBuffer = size * Integer.BYTES;
		
		buffer = Buffer.alloc(logicalDevice, sizeBuffer,
				VK_BUFFER_USAGE_STORAGE_BUFFER_BIT | VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
				VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
	}

	public void load()
	{
		long sizeBuffer = size * Integer.BYTES;

		ByteBuffer byteBuffer = MemoryUtil.memAlloc((int) sizeBuffer);
		IntBuffer intBufferView = byteBuffer.asIntBuffer();
		for (int i = 0; i < size; i++)
		{
			intBufferView.put(values[i] == false ? (int) 0 : 1);
		}
		intBufferView.flip();

		buffer.fillWithBuffer(byteBuffer, sizeBuffer);
	}

	public void setValue(int x, int y, boolean value)
	{
		x += xOrigin;
		y += yOrigin;

		values[y * width + x] = value;
	}

	public void activate(int x, int y)
	{
		setValue(x, y, true);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public boolean[] getValues()
	{
		return values;
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
}
