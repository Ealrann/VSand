package org.sheepy.vsand.loader;

import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.StaticImage;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = StaticImage.class, name = "Board Image")
public final class BoardImageLoader implements IVulkanAdapter
{
	@Autorun
	public static void load(StaticImage image)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(image);
		final int width = application.getSize().x();
		final int height = application.getSize().y();

		image.setWidth(width);
		image.setHeight(height);
		image.setTiling(VK_IMAGE_TILING_OPTIMAL);
		image.setProperties(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
	}
}
