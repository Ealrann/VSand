package org.sheepy.vsand.constants;

import java.nio.ByteBuffer;
import java.util.Random;

import org.eclipse.emf.common.notify.Notification;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.NotifyChanged;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.ResourcePackage;
import org.sheepy.vsand.model.BoardConstantBuffer;

@Statefull
@Adapter(scope = BoardConstantBuffer.class)
public final class BoardConstantBufferAdapter implements IVulkanAdapter
{
	private final int BYTE_SIZE = 2 * Integer.BYTES;
	private final int BOARD_INDEX_POSITION = 1 * Integer.BYTES;

	private final Random random = new Random(System.nanoTime());
	private final BoardConstantBuffer constantBuffer;

	private ByteBuffer buffer = null;

	public BoardConstantBufferAdapter(BoardConstantBuffer constantBuffer)
	{
		this.constantBuffer = constantBuffer;
	}

	@Autorun
	public void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
		constantBuffer.setData(buffer);
	}

	@NotifyChanged
	public void notifyChanged(Notification notification)
	{
		if (notification.getFeature() == ResourcePackage.Literals.CONSTANT_BUFFER__BEING_PUSHED
				&& notification.getNewBooleanValue() == true)
		{
			final float rNumber = random.nextFloat();
			int currentBoardBuffer = constantBuffer.getCurrentBoardBuffer();
			currentBoardBuffer = currentBoardBuffer == 1 ? 0 : 1;
			constantBuffer.setCurrentBoardBuffer(currentBoardBuffer);

			buffer.putFloat(0, rNumber);
			buffer.putInt(BOARD_INDEX_POSITION, currentBoardBuffer);
		}
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer);
		constantBuffer.setData(null);
	}
}
