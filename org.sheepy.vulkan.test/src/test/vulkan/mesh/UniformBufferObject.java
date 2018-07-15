package test.vulkan.mesh;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkDescriptorBufferInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetLayoutBinding;
import org.lwjgl.vulkan.VkWriteDescriptorSet;
import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.util.SizeOf;

public class UniformBufferObject implements IDescriptor, IAllocable
{
	private static final Vector3f UP_AXIS = new Vector3f(0f, 0f, 1f);
	private static final Vector3f AXIS = new Vector3f(0f, 0f, 1f);
	private static final Vector3f CENTER_LOCATION = new Vector3f(0f);
	private static final Vector3f EYE_LOCATION = new Vector3f(1.5f, 1.5f, 1.5f);
	private static final float RADIANS_90 = (float) Math.toRadians(90f);
	private static final float RADIANS_45 = (float) Math.toRadians(45f);

	public static final int SIZE_OF = SizeOf.MATRIX4F * 3;

	private VulkanApplication app;

	private Matrix4f model = new Matrix4f();
	private Matrix4f view = new Matrix4f();
	private Matrix4f proj = new Matrix4f();

	private Buffer buffer;

	public UniformBufferObject(VulkanApplication app, LogicalDevice logicalDevice)
	{
		this.app = app;

		buffer = new Buffer(logicalDevice, SIZE_OF, VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
				VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT);
		buffer.allocate();
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		
	}
	
	private ByteBuffer allocBuffer()
	{
		ByteBuffer res = MemoryUtil.memAlloc(SIZE_OF);
		FloatBuffer fb = res.asFloatBuffer();

		float[] values = new float[48];
		model.get(values, 0);
		view.get(values, 16);
		proj.get(values, 32);

		fb.put(values);

		return res;
	}

	public void update(float time)
	{
		model.identity().rotate((float) (time * -RADIANS_90), AXIS);

		view.identity().lookAt(EYE_LOCATION, CENTER_LOCATION, UP_AXIS);

		int width = app.getWidth();
		int height = app.getHeight();

		proj.identity().perspective(RADIANS_45, (float) width / (float) height, 0.1f, 10f);
		// inverse the y axis
		proj.mul(new Matrix4f().m11(-1f));

		ByteBuffer datas = allocBuffer();
		buffer.fillWithBuffer(datas);
		MemoryUtil.memFree(datas);
	}

	@Override
	public void free()
	{
		buffer.free();
	}

	public long getBufferId()
	{
		return buffer.getId();
	}

	@Override
	public VkDescriptorSetLayoutBinding allocLayoutBinding(MemoryStack stack)
	{
		VkDescriptorSetLayoutBinding res = VkDescriptorSetLayoutBinding.callocStack(stack);
		res.descriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
		res.descriptorCount(1);
		res.stageFlags(VK_SHADER_STAGE_VERTEX_BIT);

		return res;
	}

	@Override
	public VkWriteDescriptorSet allocWriteDescriptor(MemoryStack stack)
	{
		VkDescriptorBufferInfo.Buffer bufferInfos = VkDescriptorBufferInfo.callocStack(1, stack);
		bufferInfos.buffer(getBufferId());
		bufferInfos.offset(0);
		bufferInfos.range(UniformBufferObject.SIZE_OF);

		VkWriteDescriptorSet descriptorWrite = VkWriteDescriptorSet.callocStack(stack);
		descriptorWrite.sType(VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET);
		descriptorWrite.dstArrayElement(0);
		descriptorWrite.descriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER);
		descriptorWrite.pBufferInfo(bufferInfos);
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buffer == null) ? 0 : buffer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		UniformBufferObject other = (UniformBufferObject) obj;
		if (buffer == null)
		{
			if (other.buffer != null) return false;
		}
		else if (!buffer.equals(other.buffer)) return false;
		return true;
	}
}
