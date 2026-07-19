package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.nio.ByteBuffer;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

/**
 * Push constants for the two pressure_apply.comp dispatches of each tick:
 * phase 0 (column lift) then phase 1 (segment pull). The seed feeds the
 * per-row pull direction hash.
 */
@ModelExtender(scope = ConstantBuffer.class, name = "PressureConstants")
@Adapter
@AutoLoad
public final class PressureConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 4 * Integer.BYTES;
	private static final int PHASE_POSITION = Integer.BYTES;
	private static final int SEED_POSITION = 2 * Integer.BYTES;
	private static final int PHASE_COUNT = 2;
	private static final long SEED_SALT = 0x9E3779B97F4A7C15L;

	private final ConstantBuffer constantBuffer;
	private final BoardConstantBuffer boardConstantBuffer;

	private ByteBuffer buffer = null;
	private RandomGenerator random = null;
	private boolean deterministic = false;
	private long seed = 0;
	private int phaseIndex = 0;
	private int tickSeed = 0;

	private PressureConstantBufferAdapter(final ConstantBuffer constantBuffer)
	{
		this.constantBuffer = constantBuffer;

		final var application = (VSandApplication) ModelUtil.getApplication(constantBuffer);
		boardConstantBuffer = application.streamTree()
										 .filter(BoardConstantBuffer.class::isInstance)
										 .map(BoardConstantBuffer.class::cast)
										 .findFirst()
										 .orElseThrow();
	}

	@Load
	private void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		constantBuffer.data(buffer);
	}

	@Dispose
	private void dispose()
	{
		MemoryUtil.memFree(buffer);
		constantBuffer.data(null);
	}

	@Override
	public void beforePush(final ConstantBuffer constantBuffer)
	{
		ensureRandomInitialized();
		if (phaseIndex == 0)
		{
			tickSeed = random.nextInt();
		}

		buffer.putInt(0, boardConstantBuffer.currentBoardBuffer());
		buffer.putInt(PHASE_POSITION, phaseIndex);
		buffer.putInt(SEED_POSITION, tickSeed);
		phaseIndex = (phaseIndex + 1) % PHASE_COUNT;
	}

	private void ensureRandomInitialized()
	{
		final boolean deterministicRandom = boardConstantBuffer.deterministicRandom();
		final long randomSeed = boardConstantBuffer.randomSeed();

		if (random == null || deterministic != deterministicRandom || seed != randomSeed)
		{
			deterministic = deterministicRandom;
			seed = randomSeed;
			random = deterministicRandom ? new SplittableRandom(randomSeed ^ SEED_SALT) : ThreadLocalRandom.current();
		}
	}
}
