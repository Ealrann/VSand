package org.sheepy.vsand.draw;

import java.nio.ByteBuffer;

import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.vsand.model.DrawCommand;

public interface IDrawCommandAdapter<T extends DrawCommand> extends IAdapter
{
	void fillBuffer(T command, ByteBuffer buffer);
}
