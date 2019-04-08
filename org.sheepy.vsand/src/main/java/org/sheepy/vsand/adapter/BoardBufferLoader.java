package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.common.util.VulkanModelUtil;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = Buffer.class, name = "Board Buffer")
public class BoardBufferLoader extends BufferAdapter
{
	public BoardBufferLoader(Buffer buffer)
	{
		super(buffer);

		var application = (VSandApplication) VulkanModelUtil.getApplication(buffer);
		int width = application.getSize().x;
		int height = application.getSize().y;
		int sizeBoard = width * height;
		int sizeChunks = (int) (Math.ceil(width / 32.) * Math.ceil(height / 32.));
		int sizeByte = (sizeBoard + sizeChunks) * Integer.BYTES;

		buffer.setSize(sizeByte);

		// Fill the board buffer with Void matter (0)
		ByteBuffer bBuffer = MemoryUtil.memCalloc(sizeByte);
		buffer.setData(bBuffer);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
