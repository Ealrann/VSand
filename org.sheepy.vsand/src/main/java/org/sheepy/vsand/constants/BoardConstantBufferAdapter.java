package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;

import java.nio.ByteBuffer;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@ModelExtender(scope = BoardConstantBuffer.class)
@Adapter
@AutoLoad
public final class BoardConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 2 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = Integer.BYTES;

	private final BoardConstantBuffer boardConstantBuffer;

	private ByteBuffer buffer = null;
	private RandomGenerator random = null;
	private boolean deterministic = false;
	private long seed = 0;

	private BoardConstantBufferAdapter(BoardConstantBuffer boardConstantBuffer)
	{
		this.boardConstantBuffer = boardConstantBuffer;
	}

	@Load
	private void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		boardConstantBuffer.data(buffer);
	}

	@Override
	public void beforePush(ConstantBuffer constantBuffer)
	{
		ensureRandomInitialized();
		final float rNumber = random.nextFloat();
		final int nextIndex = nextBoardIndex(boardConstantBuffer.currentBoardBuffer());
		boardConstantBuffer.currentBoardBuffer(nextIndex);

		buffer.putFloat(0, rNumber);
		buffer.putInt(BOARD_INDEX_POSITION, nextIndex);
	}

	private void ensureRandomInitialized()
	{
		final boolean deterministicRandom = boardConstantBuffer.deterministicRandom();
		final long randomSeed = boardConstantBuffer.randomSeed();

		if (random == null || deterministic != deterministicRandom || seed != randomSeed)
		{
			deterministic = deterministicRandom;
			seed = randomSeed;
			random = deterministicRandom ? new SplittableRandom(randomSeed) : ThreadLocalRandom.current();
		}
	}

	private static int nextBoardIndex(int currentIndex)
	{
		return (currentIndex + 1) % 2;
	}

	@Dispose
	private void dispose()
	{
		MemoryUtil.memFree(buffer);
		boardConstantBuffer.data(null);
	}
}
