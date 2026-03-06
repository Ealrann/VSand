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

@ModelExtender(scope = ConstantBuffer.class, name = "MassConstants")
@Adapter
@AutoLoad
public final class MassConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 2 * Integer.BYTES;
	private static final int STEP_POSITION = Integer.BYTES;
	private static final int[] STEP_SEQUENCE = { 0, 1, 2, 1, 2, 3 };

	private final ConstantBuffer constantBuffer;
	private final BoardConstantBuffer boardConstantBuffer;

	private ByteBuffer buffer = null;
	private int stepIndex = 0;

	private MassConstantBufferAdapter(final ConstantBuffer constantBuffer)
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
		buffer.putInt(0, boardConstantBuffer.currentBoardBuffer());
		buffer.putInt(STEP_POSITION, STEP_SEQUENCE[stepIndex]);
		stepIndex = (stepIndex + 1) % STEP_SEQUENCE.length;
	}
}
