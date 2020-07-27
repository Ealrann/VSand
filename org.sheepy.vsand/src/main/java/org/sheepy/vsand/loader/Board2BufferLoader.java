package org.sheepy.vsand.loader;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.StaticBuffer;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = StaticBuffer.class, name = "Board Buffer 2")
@Adapter(singleton = true, lazy = false)
public final class Board2BufferLoader implements IExtender
{
	@Load
	private static void load(StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final var size = application.getSize();
		final int width = size.x();
		final int height = size.y();
		final int sizeBoard = width * height;
		final int sizeByte = sizeBoard * Integer.BYTES;

		buffer.setSize(sizeByte);
	}
}
