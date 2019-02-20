package org.sheepy.vsand.buffer;

import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.lily.vulkan.model.resource.Image;

public class BoardImageLoader
{
	public static final void load(Image image, int width, int height)
	{
		image.setWidth(width);
		image.setHeight(height);
		image.setTiling(VK_IMAGE_TILING_OPTIMAL);
		image.setProperties(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
	}
}
