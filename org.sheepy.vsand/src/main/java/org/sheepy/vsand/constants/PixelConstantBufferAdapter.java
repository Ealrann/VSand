package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.cadence.Tick;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.PixelConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.BoardUtil;
import org.sheepy.vsand.util.EShapeSize;

import java.nio.ByteBuffer;

@ModelExtender(scope = PixelConstantBuffer.class)
@Adapter(lazy = false)
public final class PixelConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 7 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = 6 * Integer.BYTES;

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
		buffer.putInt(BOARD_INDEX_POSITION, constantBuffer.getBoardConstantBuffer().getCurrentBoardBuffer());
	}

	@Tick
	private void updateBuffer()
	{
		final boolean forceClear = application.isForceClear();
		final var size = EShapeSize.values()[application.getBrushSize() - 1];
		final var mainMaterial = application.getMainMaterial();
		final int index = application.getMaterials().getMaterials().indexOf(mainMaterial);

		buffer.putInt(forceClear ? 1 : 0);
		buffer.putInt(application.isShowSleepZones() ? 1 : 0);
		buffer.putInt(index);
		buffer.putInt(size.getSize() >> 1);

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
		constantBuffer.setData(buffer);
	}
}
