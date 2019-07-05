package org.sheepy.vsand.loader;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.Buffer;

@Adapter(scope = Buffer.class, name = "Chunk Buffer")
public final class ChunkBufferLoader implements IVulkanAdapter
{
	@Autorun
	public static void load(Buffer buffer)
	{
		final var application = ModelUtil.getApplication(buffer);
		final int width = application.getSize().x();
		final int height = application.getSize().y();
		final int sizeChunks = (int) (Math.floor(width / 16.) * Math.floor(height / 16.));
		final int sizeByte = sizeChunks * Integer.BYTES;

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
