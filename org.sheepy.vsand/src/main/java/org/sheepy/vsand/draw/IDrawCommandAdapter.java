package org.sheepy.vsand.draw;

import java.nio.ByteBuffer;

import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.IAdapterFactoryService;
import org.sheepy.vsand.model.DrawCommand;

public interface IDrawCommandAdapter<T extends DrawCommand> extends IAdapter
{
	void fillBuffer(T command, ByteBuffer buffer);
	
	@SuppressWarnings("unchecked")
	public static <T extends DrawCommand> IDrawCommandAdapter<T> adapt(T command)
	{
		return IAdapterFactoryService.INSTANCE.adapt(command, IDrawCommandAdapter.class);
	}
}
