package org.sheepy.vsand.constants;

import java.nio.ByteBuffer;

import org.eclipse.emf.common.notify.Notification;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.NotifyChanged;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.adapter.annotation.Tick;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.process.IPipeline;
import org.sheepy.lily.vulkan.model.resource.ResourcePackage;
import org.sheepy.vsand.draw.IDrawCommandAdapter;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.DrawConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = DrawConstantBuffer.class)
public final class DrawConstantBufferAdapter implements IVulkanAdapter
{
	private final int BYTE_SIZE = 18 * Integer.BYTES;
	private final int BOARD_INDEX_POSITION = 17 * Integer.BYTES;

	private final DrawConstantBuffer constantBuffer;
	private final VSandApplication application;
	private final IPipeline pipeline;

	private ByteBuffer buffer = null;

	public DrawConstantBufferAdapter(DrawConstantBuffer constantBuffer)
	{
		this.constantBuffer = constantBuffer;
		pipeline = ModelUtil.findParent(constantBuffer, IPipeline.class);

		application = (VSandApplication) ModelUtil.getApplication(constantBuffer);
	}

	@Autorun
	public void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer);
	}

	@NotifyChanged
	public void notifyChanged(Notification notification)
	{
		if (notification.getFeature() == ResourcePackage.Literals.CONSTANT_BUFFER__BEING_PUSHED
				&& notification.getNewBooleanValue() == true)
		{
			buffer.putInt(BOARD_INDEX_POSITION,
					constantBuffer.getBoardConstantBuffer().getCurrentBoardBuffer());
		}
	}

	@Tick
	public void updateBuffer()
	{
		if (application.getDrawQueue().isEmpty() == false)
		{
			final var command = application.getDrawQueue().remove(0);
			fillBufferWithCommand(command);

			constantBuffer.setData(buffer);

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

	private <T extends DrawCommand> void fillBufferWithCommand(final T command)
	{
		final IDrawCommandAdapter<T> adapter = IDrawCommandAdapter.adapt(command);
		adapter.fillBuffer(command, buffer);
		buffer.putInt(0);
		buffer.flip();
	}
}
