package org.sheepy.vsand.loader;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

@ModelExtender(scope = StaticBuffer.class, name = "Flux Right Buffer")
@Adapter(singleton = true)
@AutoLoad
public final class FluxRightBufferLoader implements IAdapter
{
	@Load
	private static void load(final StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final var size = application.size();
		buffer.size(size.x() * size.y() * Integer.BYTES);
	}
}

