package org.sheepy.vsand;

import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.IExtenderProvider;
import org.sheepy.vsand.constants.BoardConstantBufferAdapter;
import org.sheepy.vsand.constants.DrawConstantBufferAdapter;
import org.sheepy.vsand.constants.PixelConstantBufferAdapter;
import org.sheepy.vsand.draw.DrawCircleAdapter;
import org.sheepy.vsand.draw.DrawLineAdapter;
import org.sheepy.vsand.draw.DrawSquareAdapter;
import org.sheepy.vsand.input.InputManager;
import org.sheepy.vsand.input.PrimaryDrawer;
import org.sheepy.vsand.input.SecondaryDrawer;
import org.sheepy.vsand.loader.*;
import org.sheepy.vsand.logic.ApplicationBehaviour;
import org.sheepy.vsand.ui.MaterialSelectorInputProviderAdapter;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class Extenders implements IExtenderProvider
{
	@Override
	public List<Class<? extends IExtender>> classifiers()
	{
		return List.of(ConfigurationBufferLoader.class,
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
					   MaterialCountAdapter.class,
					   InputManager.class,
					   PrimaryDrawer.class,
					   SecondaryDrawer.class);
	}

	@Override
	public MethodHandles.Lookup lookup()
	{
		return MethodHandles.lookup();
	}
}
