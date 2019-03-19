package org.sheepy.vsand.adapter;

import static org.lwjgl.vulkan.VK10.*;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.common.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.Image;
import org.sheepy.lily.vulkan.resource.image.ImageAdapter;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = Image.class, name = "Configuration")
public class BoardImageLoader extends ImageAdapter
{
	public BoardImageLoader(Image image)
	{
		super(image);

		var application = (VSandApplication) ModelUtil.getApplication(image);
		int width = application.getSize().x;
		int height = application.getSize().y;

		image.setWidth(width);
		image.setHeight(height);
		image.setTiling(VK_IMAGE_TILING_OPTIMAL);
		image.setProperties(VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
	}
}
