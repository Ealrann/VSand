package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.*;
import org.sheepy.lily.core.api.cadence.Tick;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.input.IVulkanInputManager;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;
import org.sheepy.vsand.model.PixelConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.BoardUtil;
import org.sheepy.vsand.util.EShapeSize;

import java.nio.ByteBuffer;

@Statefull
@Adapter(scope = PixelConstantBuffer.class, lazy = false)
public final class PixelConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 7 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = 6 * Integer.BYTES;

	private final PixelConstantBuffer constantBuffer;
	private final VSandApplication application;

	private ByteBuffer buffer = null;

	private IVulkanInputManager inputManager;

	private PixelConstantBufferAdapter(PixelConstantBuffer constantBuffer)
	{
		this.constantBuffer = constantBuffer;

		application = (VSandApplication) ModelUtil.getApplication(constantBuffer);
	}

	@Load
	private void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);

		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		final var engineAdapter = vulkanEngine.adapt(IVulkanEngineAdapter.class);
		inputManager = engineAdapter.getInputManager();

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
