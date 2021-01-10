package org.sheepy.vsand.draw;

import org.sheepy.lily.core.api.extender.IAdapter;
import org.sheepy.vsand.model.DrawCommand;

import java.nio.ByteBuffer;

public sealed interface IDrawCommandAdapter<T extends DrawCommand> extends IAdapter permits DrawCircleAdapter,
																							DrawLineAdapter,
																							DrawSquareAdapter
{
	void fillBuffer(T command, ByteBuffer buffer);
}
