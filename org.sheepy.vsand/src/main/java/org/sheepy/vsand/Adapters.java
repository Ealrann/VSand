package org.sheepy.vsand;

import java.util.List;

import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.IAdapterProvider;
import org.sheepy.vsand.constants.BoardConstantBufferAdapter;
import org.sheepy.vsand.constants.DrawConstantBufferAdapter;
import org.sheepy.vsand.constants.PixelConstantBufferAdapter;
import org.sheepy.vsand.draw.DrawCircleAdapter;
import org.sheepy.vsand.draw.DrawLineAdapter;
import org.sheepy.vsand.draw.DrawSquareAdapter;
import org.sheepy.vsand.loader.Board1BufferLoader;
import org.sheepy.vsand.loader.Board2BufferLoader;
import org.sheepy.vsand.loader.BoardImageLoader;
import org.sheepy.vsand.loader.ChunkBufferLoader;
import org.sheepy.vsand.loader.ConfigurationBufferLoader;
import org.sheepy.vsand.loader.MaterialCountAdapter;
import org.sheepy.vsand.loader.TransformationBufferLoader;
import org.sheepy.vsand.logic.ApplicationBehaviour;
import org.sheepy.vsand.ui.MaterialSelectorInputProviderAdapter;

public class Adapters implements IAdapterProvider
{
	@Override
	public List<Class<? extends IAdapter>> classifiers()
	{
		return List.of(	ConfigurationBufferLoader.class,
						Board1BufferLoader.class,
						Board2BufferLoader.class,
						ChunkBufferLoader.class,
						BoardImageLoader.class,
						TransformationBufferLoader.class,
						BoardConstantBufferAdapter.class,
						DrawConstantBufferAdapter.class,
						PixelConstantBufferAdapter.class,
						DrawCircleAdapter.class,
						DrawSquareAdapter.class,
						DrawLineAdapter.class,
						ApplicationBehaviour.class,
						MaterialSelectorInputProviderAdapter.class,
						MaterialCountAdapter.class);
	}
}