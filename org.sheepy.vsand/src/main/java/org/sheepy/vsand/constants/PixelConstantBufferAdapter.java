package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.cadence.Tick;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.PixelConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;
import org.sheepy.vsand.util.BoardUtil;
import org.sheepy.vsand.util.EShapeSize;

import java.nio.ByteBuffer;

@ModelExtender(scope = PixelConstantBuffer.class)
@Adapter
@AutoLoad
public final class PixelConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 9 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = 8 * Integer.BYTES;

	private final PixelConstantBuffer constantBuffer;
	private final VSandApplication application;
	private final ByteBuffer buffer;
	private final IInputManager inputManager;

	private PixelConstantBufferAdapter(PixelConstantBuffer constantBuffer)
	{
		this.constantBuffer = constantBuffer;

		application = (VSandApplication) ModelUtil.getApplication(constantBuffer);
		inputManager = application.adapt(IInputManager.class);
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		updateBuffer();
	}

	@Dispose
	private void dispose()
	{
		MemoryUtil.memFree(buffer);
	}

	@Override
	public void beforePush(ConstantBuffer b)
	{
		buffer.putInt(BOARD_INDEX_POSITION, constantBuffer.boardConstantBuffer().currentBoardBuffer());
	}

	@Tick
	private void updateBuffer()
	{
		final boolean forceClear = application.forceClear();
		final var size = EShapeSize.values()[application.brushSize() - 1];
		final var mainMaterial = application.mainMaterial();
		final int index = application.materials().materials().indexOf(mainMaterial);

		buffer.putInt(forceClear ? 1 : 0);
		buffer.putInt(application.showSleepZones() ? 1 : 0);
		buffer.putInt(application.showMass() ? 1 : 0);
		buffer.putInt(index);
		buffer.putInt(size.getSize() >> 1);
		buffer.putInt(0);

		if (inputManager != null)
		{
			final var cursorPosition = inputManager.getCursorPosition();
			final var cursorPositionOnBoard = BoardUtil.toBoardPosition(cursorPosition, application);
			buffer.putInt(cursorPositionOnBoard.x());
			buffer.putInt(cursorPositionOnBoard.y());
		}
		else
		{
			buffer.putInt(0);
			buffer.putInt(0);
		}
		buffer.putInt(0);
		buffer.flip();
		constantBuffer.data(buffer);
	}
}
