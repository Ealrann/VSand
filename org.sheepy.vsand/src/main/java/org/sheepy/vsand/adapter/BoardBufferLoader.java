package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;

@Statefull
@Adapter(scope = Buffer.class, name = "Board Buffer")
public class BoardBufferLoader extends BufferAdapter
{
	public BoardBufferLoader(Buffer buffer)
	{
		super(buffer);

		final var application = ModelUtil.getApplication(buffer);
		final int width = application.getSize().x;
		final int height = application.getSize().y;
		final int sizeBoard = width * height;
		final int sizeChunks = (int) (Math.ceil(width / 16.) * Math.ceil(height / 16.));
		final int sizeByte = (sizeBoard + sizeChunks) * Integer.BYTES;

		buffer.setSize(sizeByte);

		// Fill the board buffer with Void matter (0)
		final ByteBuffer bBuffer = MemoryUtil.memCalloc(sizeByte);
		buffer.setData(bBuffer);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
