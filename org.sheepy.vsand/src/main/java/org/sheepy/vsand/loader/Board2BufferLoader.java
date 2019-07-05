package org.sheepy.vsand.loader;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.Buffer;

@Adapter(scope = Buffer.class, name = "Board Buffer 2")
public final class Board2BufferLoader implements IVulkanAdapter
{
	@Autorun
	public static void load(Buffer buffer)
	{
		final var application = ModelUtil.getApplication(buffer);
		final int width = application.getSize().x() / 2;
		final int height = application.getSize().y() / 2;
		final int sizeBoard = width * height;
		final int sizeByte = sizeBoard * Integer.BYTES;

		buffer.setSize(sizeByte);

		// Fill the board buffer with Void matter (0)
		final ByteBuffer bBuffer = MemoryUtil.memCalloc(sizeByte);
		buffer.setData(bBuffer);
	}

	@Dispose
	public static void dispose(Buffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
