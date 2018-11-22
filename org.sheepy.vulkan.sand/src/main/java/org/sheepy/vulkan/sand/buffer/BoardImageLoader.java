package org.sheepy.vulkan.sand.buffer;

import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.vulkan.model.enumeration.EDescriptorType;
import org.sheepy.vulkan.model.enumeration.EFormat;
import org.sheepy.vulkan.model.enumeration.EImageLayout;
import org.sheepy.vulkan.model.enumeration.EImageUsage;
import org.sheepy.vulkan.model.enumeration.EPipelineStage;
import org.sheepy.vulkan.model.enumeration.EShaderStage;
import org.sheepy.vulkan.model.resource.Image;
import org.sheepy.vulkan.model.resource.ImageLayout;
import org.sheepy.vulkan.model.resource.impl.ImageLayoutImpl;

public class BoardImageLoader
{
	private static final EFormat IMAGE_FORMAT = EFormat.R8G8B8A8_UNORM;

	public static final void load(Image image, int width, int height)
	{
		ImageLayout initialLayout = new ImageLayoutImpl();
		initialLayout.setStage(EPipelineStage.COMPUTE_SHADER_BIT);
		initialLayout.setLayout(EImageLayout.GENERAL);
		initialLayout.setAccess(VK_ACCESS_SHADER_WRITE_BIT);

		image.setWidth(width);
		image.setHeight(height);
		image.setFormat(IMAGE_FORMAT);
		image.setMipLevels(1);
		image.setTiling(VK_IMAGE_TILING_OPTIMAL);
		image.getUsages().add(EImageUsage.TRANSFER_SRC);
		image.getUsages().add(EImageUsage.STORAGE);
		image.setProperties(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);

		image.setDescriptorType(EDescriptorType.STORAGE_IMAGE);
		image.getShaderStages().add(EShaderStage.COMPUTE_BIT);

		image.setInitialLayout(initialLayout);
	}
}
