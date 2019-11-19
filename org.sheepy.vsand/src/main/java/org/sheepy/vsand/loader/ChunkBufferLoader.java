package org.sheepy.vsand.loader;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.Buffer;

@Adapter(scope = Buffer.class, name = "Chunk Buffer", lazy = false)
public final class ChunkBufferLoader implements IAdapter
{
	@Load
	private static void load(Buffer buffer)
	{
		final var application = ModelUtil.getApplication(buffer);
		final int width = application.getSize().x();
		final int height = application.getSize().y();
		final int sizeChunks = (int) (Math.floor(width / 16.) * Math.floor(height / 16.));
		final int sizeByte = sizeChunks * Integer.BYTES;

		buffer.setSize(sizeByte);

		// Fill the board buffer with Void matter (0)
		final ByteBuffer bBuffer = MemoryUtil.memCalloc(sizeByte);
		bBuffer.position(sizeByte);
		bBuffer.flip();
		buffer.setData(bBuffer);
	}

	@Dispose
	private static void dispose(Buffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
