package org.sheepy.vsand.loader;

import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.StaticImage;
import org.sheepy.vsand.model.VSandApplication;

@Adapter(scope = StaticImage.class, name = "Board Image", lazy = false)
public final class BoardImageLoader implements IAdapter
{
	@Load
	private static void load(StaticImage image)
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
