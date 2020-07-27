package org.sheepy.vsand.load.buffer;

import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticImage;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vulkan.model.enumeration.EImageUsage;

@ModelExtender(scope = StaticImage.class, name = "Board Image")
@Adapter(singleton = true)
@AutoLoad
public final class BoardImageLoader implements IAdapter
{
	@Load
	private static void load(StaticImage image)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(image);

		image.setSize(application.getSize());
		image.setTiling(0);
		image.getUsages().add(EImageUsage.STORAGE);
	}
}
