package org.sheepy.vsand.loader;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.StaticImage;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vulkan.model.enumeration.EImageUsage;

@ModelExtender(scope = StaticImage.class, name = "Board Image")
@Adapter(singleton = true, lazy = false)
public final class BoardImageLoader implements IExtender
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
