package org.sheepy.vsand.adapter;

import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.Image;
import org.sheepy.lily.vulkan.resource.image.ImageAdapter;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = Image.class, name = "Board Image")
public class BoardImageLoader extends ImageAdapter
{
	public BoardImageLoader(Image image)
	{
		super(image);

		final var application = (VSandApplication) ModelUtil.getApplication(image);
		final int width = application.getSize().x;
		final int height = application.getSize().y;

		image.setWidth(width);
		image.setHeight(height);
		image.setTiling(VK_IMAGE_TILING_OPTIMAL);
		image.setProperties(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
	}
}
