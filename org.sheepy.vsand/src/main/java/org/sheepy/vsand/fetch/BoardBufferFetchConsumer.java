package org.sheepy.vsand.fetch;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.game.api.resource.buffer.IBufferDataConsumer;
import org.sheepy.lily.vulkan.model.vulkanresource.StaticBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = StaticBuffer.class)
@Adapter
public final class BoardBufferFetchConsumer implements IBufferDataConsumer, IAdapter
{
	private enum BufferType
	{
		BOARD,
		MASS,
		UNKNOWN
	}

	private final int bufferIndex;
	private final BufferType bufferType;
	private final BoardFetchService fetchService;

	private BoardBufferFetchConsumer(StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		fetchService = application.adaptNotNull(BoardFetchService.class);

		final var name = buffer.name();
		bufferIndex = name != null && name.endsWith("2") ? 1 : 0;
		if (name != null && name.startsWith("Board Buffer"))
		{
			bufferType = BufferType.BOARD;
		}
		else if (name != null && name.startsWith("Mass Buffer"))
		{
			bufferType = BufferType.MASS;
		}
		else
		{
			bufferType = BufferType.UNKNOWN;
		}
	}

	@Override
	public void fetch(ByteBuffer data)
	{
		switch (bufferType)
		{
			case BOARD -> fetchService.onBoardFetched(bufferIndex, data);
			case MASS -> fetchService.onMassFetched(bufferIndex, data);
			case UNKNOWN ->
			{
			}
		}
	}
}
