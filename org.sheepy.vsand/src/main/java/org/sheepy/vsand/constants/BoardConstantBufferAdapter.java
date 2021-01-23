package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.BoardConstantBuffer;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@ModelExtender(scope = BoardConstantBuffer.class)
@Adapter
@AutoLoad
public final class BoardConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 2 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = Integer.BYTES;

	private final Random random = ThreadLocalRandom.current();
	private final BoardConstantBuffer boardConstantBuffer;

	private ByteBuffer buffer = null;

	private BoardConstantBufferAdapter(BoardConstantBuffer boardConstantBuffer)
	{
		this.boardConstantBuffer = boardConstantBuffer;
	}

	@Load
	private void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		boardConstantBuffer.setData(buffer);
	}

	@Override
	public void beforePush(ConstantBuffer constantBuffer)
	{
		final float rNumber = random.nextFloat();
		final int nextIndex = nextBoardIndex(boardConstantBuffer.getCurrentBoardBuffer());
		boardConstantBuffer.setCurrentBoardBuffer(nextIndex);

		buffer.putFloat(0, rNumber);
		buffer.putInt(BOARD_INDEX_POSITION, nextIndex);
	}

	private static int nextBoardIndex(int currentIndex)
	{
		return (currentIndex + 1) % 2;
	}

	@Dispose
	private void dispose()
	{
		MemoryUtil.memFree(buffer);
		boardConstantBuffer.setData(null);
	}
}
