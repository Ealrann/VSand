package org.sheepy.vulkan.nuklear.pipeline;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.vulkan.common.execution.ExecutionManager;
import org.sheepy.lily.vulkan.common.execution.IResourceAllocable;
import org.sheepy.lily.vulkan.model.enumeration.EFilter;
import org.sheepy.lily.vulkan.model.resource.Sampler;
import org.sheepy.lily.vulkan.model.resource.impl.SamplerImpl;
import org.sheepy.lily.vulkan.resource.image.ImageInfo;
import org.sheepy.lily.vulkan.resource.nativehelper.VkTexture;

public class NullTexture implements IResourceAllocable
{
	private static final int FORMAT = VK_FORMAT_R8G8B8A8_UNORM;
	private static final int USAGE = VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT;

	private static final Sampler sampler = new SamplerImpl();
	static
	{
		sampler.setMinFilter(EFilter.NEAREST);
		sampler.setMagFilter(EFilter.NEAREST);
	}

	private static final ImageInfo imageInfo = new ImageInfo(1, 1, FORMAT, USAGE,
			VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);

	public final VkTexture texture = new VkTexture(imageInfo, sampler);

	@Override
	public void allocate(MemoryStack stack, ExecutionManager executionManager)
	{
		ByteBuffer buffer = MemoryUtil.memAlloc(4);
		buffer.putInt(0xFFFFFFFF);
		buffer.flip();

		texture.allocate(stack, executionManager.logicalDevice);
		texture.loadImage(stack, executionManager, buffer);
	}

	@Override
	public void free()
	{
		texture.free();
	}

	public long getSamplerId()
	{
		return texture.getSamplerId();
	}
}
