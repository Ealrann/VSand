package org.sheepy.vsand.loader;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.extender.IAdapter;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticBuffer;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = StaticBuffer.class, name = "Board Buffer 1")
@Adapter(singleton = true)
@AutoLoad
public final class Board1BufferLoader implements IAdapter
{
	@Load
	private static void load(StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final var size = application.getSize();
		final int width = size.x() / 2;
		final int height = size.y() / 2;
		final int sizeBoard = width * height;
		final int sizeByte = sizeBoard * Integer.BYTES;

		buffer.setSize(sizeByte);
	}
}
