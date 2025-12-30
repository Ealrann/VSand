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
	private final int bufferIndex;
	private final BoardFetchService fetchService;

	private BoardBufferFetchConsumer(StaticBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.getApplication(buffer);
		fetchService = application.adaptNotNull(BoardFetchService.class);
		bufferIndex = buffer.name() != null && buffer.name().endsWith("2") ? 1 : 0;
	}

	@Override
	public void fetch(ByteBuffer data)
	{
		fetchService.onBufferFetched(bufferIndex, data);
	}
}
