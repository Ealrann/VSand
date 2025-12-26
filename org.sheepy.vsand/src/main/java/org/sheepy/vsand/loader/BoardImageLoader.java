package org.sheepy.vsand.loader;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticImage;
import org.sheepy.vsand.model.vsand.VSandApplication;
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

		image.size(application.size());
		image.tiling(0);
		image.usages().add(EImageUsage.STORAGE);
	}
}
