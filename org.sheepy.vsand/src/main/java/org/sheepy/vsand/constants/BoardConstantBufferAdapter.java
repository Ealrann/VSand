package org.sheepy.vsand.constants;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;
import org.sheepy.vsand.model.BoardConstantBuffer;

@Statefull
@Adapter(scope = BoardConstantBuffer.class, lazy = false)
public final class BoardConstantBufferAdapter implements IConstantBufferUpdater
{
	private final int BYTE_SIZE = 2 * Integer.BYTES;
	private final int BOARD_INDEX_POSITION = 1 * Integer.BYTES;

	private final Random random = ThreadLocalRandom.current();
	private final BoardConstantBuffer boardConstantBuffer;

	private ByteBuffer buffer = null;

	public BoardConstantBufferAdapter(BoardConstantBuffer boardConstantBuffer)
	{
		this.boardConstantBuffer = boardConstantBuffer;
	}

	@Load
	public void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		boardConstantBuffer.setData(buffer);
	}

	@Override
	public void beforePush(ConstantBuffer constantBuffer)
	{
		final float rNumber = random.nextFloat();
		int currentBoardBuffer = boardConstantBuffer.getCurrentBoardBuffer();
		currentBoardBuffer = currentBoardBuffer == 1 ? 0 : 1;
		boardConstantBuffer.setCurrentBoardBuffer(currentBoardBuffer);

		buffer.putFloat(0, rNumber);
		buffer.putInt(BOARD_INDEX_POSITION, currentBoardBuffer);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer);
		boardConstantBuffer.setData(null);
	}
}
