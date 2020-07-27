package org.sheepy.vsand.constants;

import org.lwjgl.system.MemoryUtil;
import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.core.api.cadence.Tick;
import org.logoce.extender.api.ModelExtender;
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
@Adapter
@AutoLoad
public final class DrawConstantBufferAdapter implements IConstantBufferUpdater
{
	private static final int BYTE_SIZE = 17 * Integer.BYTES;

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
	public void beforePush(final ConstantBuffer buffer)
	{
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

			if (pipeline.isRecord() == false)
			{
				pipeline.setRecord(true);
			}
		}
		else if (pipeline.isRecord())
		{
			pipeline.setRecord(false);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends DrawCommand> void fillBufferWithCommand(final T command)
	{
		final var adapter = command.adaptNotNull(IDrawCommandAdapter.class);
		adapter.fillBuffer(command, buffer);
		buffer.flip();
	}
}
