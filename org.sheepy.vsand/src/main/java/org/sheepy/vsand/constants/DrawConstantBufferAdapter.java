package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.cadence.Tick;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.resource.buffer.IConstantBufferUpdater;
import org.sheepy.lily.vulkan.model.process.AbstractPipeline;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.draw.IDrawCommandAdapter;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.DrawConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = DrawConstantBuffer.class)
@Adapter(lazy = false)
public final class DrawConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 18 * Integer.BYTES;
	private static final int BOARD_INDEX_POSITION = 17 * Integer.BYTES;

	private final DrawConstantBuffer drawConstantBuffer;
	private final VSandApplication application;
	private final AbstractPipeline pipeline;

	private ByteBuffer buffer = null;

	private DrawConstantBufferAdapter(DrawConstantBuffer drawConstantBuffer)
	{
		this.drawConstantBuffer = drawConstantBuffer;
		pipeline = ModelUtil.findParent(drawConstantBuffer, AbstractPipeline.class);

		application = (VSandApplication) ModelUtil.getApplication(drawConstantBuffer);
	}

	@Load
	private void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
	}

	@Dispose
	private void dispose()
	{
		MemoryUtil.memFree(buffer);
	}

	@Override
	public void beforePush(ConstantBuffer constantBuffer)
	{
		final var boardConstantBuffer = drawConstantBuffer.getBoardConstantBuffer();
		final int currentBoardBuffer = boardConstantBuffer.getCurrentBoardBuffer();

		buffer.putInt(BOARD_INDEX_POSITION, currentBoardBuffer);
	}

	@Tick
	private void updateBuffer()
	{
		final var drawQueue = application.getDrawQueue();
		if (drawQueue.isEmpty() == false)
		{
			final var command = drawQueue.get(0);
			fillBufferWithCommand(command);
			drawQueue.remove(0);

			drawConstantBuffer.setData(buffer);

			if (pipeline.isEnabled() == false)
			{
				pipeline.setEnabled(true);
			}
		}
		else if (pipeline.isEnabled())
		{
			pipeline.setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends DrawCommand> void fillBufferWithCommand(final T command)
	{
		final var adapter = command.adaptNotNull(IDrawCommandAdapter.class);
		adapter.fillBuffer(command, buffer);
		buffer.putInt(0);
		buffer.flip();
	}
}
