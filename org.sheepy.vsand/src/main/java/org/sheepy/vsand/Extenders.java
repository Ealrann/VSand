package org.sheepy.vsand;

import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.IAdapterProvider;
import org.sheepy.vsand.constants.BoardConstantBufferAdapter;
import org.sheepy.vsand.constants.DrawConstantBufferAdapter;
import org.sheepy.vsand.constants.PixelConstantBufferAdapter;
import org.sheepy.vsand.constants.SwapConstantBufferAdapter;
import org.sheepy.vsand.draw.DrawCircleAdapter;
import org.sheepy.vsand.draw.DrawLineAdapter;
import org.sheepy.vsand.draw.DrawSquareAdapter;
import org.sheepy.vsand.fetch.BoardBufferFetchConsumer;
import org.sheepy.vsand.fetch.BoardFetchService;
import org.sheepy.vsand.input.InputManager;
import org.sheepy.vsand.input.PrimaryDrawer;
import org.sheepy.vsand.input.SecondaryDrawer;
import org.sheepy.vsand.loader.*;
import org.sheepy.vsand.logic.ApplicationBehaviour;
import org.sheepy.vsand.ui.MaterialSelectorInputProviderAdapter;

import java.lang.invoke.MethodHandles;
import java.util.List;

public final class Extenders implements IAdapterProvider
{
	@Override
	public List<Class<? extends IAdapter>> classifiers()
	{
		return List.of(ConfigurationBufferLoader.class,
					   Board1BufferLoader.class,
					   Board2BufferLoader.class,
					   Mass1BufferLoader.class,
					   Mass2BufferLoader.class,
					   HeadBufferLoader.class,
					   ChunkBufferLoader.class,
						   BoardImageLoader.class,
						   TransformationBufferLoader.class,
						   BoardConstantBufferAdapter.class,
						   SwapConstantBufferAdapter.class,
						   DrawConstantBufferAdapter.class,
						   PixelConstantBufferAdapter.class,
						   DrawCircleAdapter.class,
						   DrawSquareAdapter.class,
						   DrawLineAdapter.class,
					   ApplicationBehaviour.class,
					   MaterialSelectorInputProviderAdapter.class,
					   SpecializationDataAdapter.class,
					   BoardFetchService.class,
					   BoardBufferFetchConsumer.class,
					   InputManager.class,
					   PrimaryDrawer.class,
					   SecondaryDrawer.class,
					   BoardUpdateDispatchTaskLoader.class,
					   PressureHeadDispatchTaskLoader.class,
					   PressureStepDispatchTaskLoader.class,
					   BoardToPixelDispatchTaskLoader.class,
					   DrawDispatchTaskLoader.class);
	}

	@Override
	public MethodHandles.Lookup lookup()
	{
		return MethodHandles.lookup();
	}
}
