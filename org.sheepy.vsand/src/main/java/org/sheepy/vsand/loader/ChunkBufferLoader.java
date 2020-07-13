package org.sheepy.vsand.loader;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.vsand.model.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = Buffer.class, name = "Chunk Buffer")
@Adapter(singleton = true, lazy = false)
public final class ChunkBufferLoader implements IExtender
{
	@Load
	private static void load(Buffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		final var size = application.getSize();
		final int width = size.x();
		final int height = size.y();
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
