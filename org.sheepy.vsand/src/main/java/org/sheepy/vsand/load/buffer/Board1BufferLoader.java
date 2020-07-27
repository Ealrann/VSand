package org.sheepy.vsand.load.buffer;

import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
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
		final int width = size.x();
		final int height = size.y();
		final int sizeBoard = width * height;
		final int sizeByte = sizeBoard * Integer.BYTES;

		buffer.setSize(sizeByte);
	}
}
