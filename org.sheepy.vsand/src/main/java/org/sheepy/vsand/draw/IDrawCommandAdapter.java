package org.sheepy.vsand.draw;

import java.nio.ByteBuffer;

import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.vsand.model.DrawCommand;

public interface IDrawCommandAdapter<T extends DrawCommand> extends IExtender
{
	void fillBuffer(T command, ByteBuffer buffer);
}
