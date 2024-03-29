package org.sheepy.vsand.loader;

import org.logoce.adapter.api.Adapter;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticBuffer;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = StaticBuffer.class, name = "Chunk Buffer")
@Adapter(singleton = true)
@AutoLoad
public final class ChunkBufferLoader implements IAdapter
{
	@Load
	private static void load(StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final var size = application.getSize();
		final int width = size.x();
		final int height = size.y();
		final int chunkWidth = (int) Math.floor(width / 16.);
		final int chunkHeight = (int) Math.floor(height / 16.);
		final int sizeChunks = chunkWidth * chunkHeight;
		final int sizeByte = sizeChunks * Integer.BYTES;

		buffer.setSize(sizeByte);
	}
}
