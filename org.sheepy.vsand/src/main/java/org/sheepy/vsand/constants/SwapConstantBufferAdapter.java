package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.SwapConstantBuffer;

import java.nio.ByteBuffer;

@ModelExtender(scope = SwapConstantBuffer.class)
@Adapter
@AutoLoad
public final class SwapConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 2 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = Integer.BYTES;

	private final SwapConstantBuffer swapConstantBuffer;

	private ByteBuffer buffer = null;

	private SwapConstantBufferAdapter(final SwapConstantBuffer swapConstantBuffer)
	{
		this.swapConstantBuffer = swapConstantBuffer;
	}

	@Load
	private void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		swapConstantBuffer.data(buffer);
	}

	@Override
	public void beforePush(final ConstantBuffer constantBuffer)
	{
		final var boardConstantBuffer = swapConstantBuffer.boardConstantBuffer();
		final int currentIndex = boardConstantBuffer != null ? boardConstantBuffer.currentBoardBuffer() : 0;
		final int nextIndex = nextBoardIndex(currentIndex);

		if (boardConstantBuffer != null)
		{
			boardConstantBuffer.currentBoardBuffer(nextIndex);
		}

		buffer.putFloat(0, 0f);
		buffer.putInt(BOARD_INDEX_POSITION, nextIndex);
	}

	private static int nextBoardIndex(final int currentIndex)
	{
		return (currentIndex + 1) % 2;
	}

	@Dispose
	private void dispose()
	{
		MemoryUtil.memFree(buffer);
		swapConstantBuffer.data(null);
	}
}
