package org.sheepy.vsand.loader;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

@ModelExtender(scope = StaticBuffer.class, name = "Board Buffer 1")
@Adapter(singleton = true)
@AutoLoad
public final class Board1BufferLoader implements IAdapter
{
	@Load
	private static void load(StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final var size = application.size();
		final int width = size.x() / 2;
		final int height = size.y() / 2;
		final int sizeBoard = width * height;
		final int sizeByte = sizeBoard * Integer.BYTES;

		buffer.size(sizeByte);
	}
}
