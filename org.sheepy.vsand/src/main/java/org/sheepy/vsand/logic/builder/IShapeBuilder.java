package org.sheepy.vsand.logic.builder;

import java.nio.ByteBuffer;

import org.sheepy.vsand.logic.BoardModification;

public interface IShapeBuilder
{
	void fillBuffer(ByteBuffer shapeBuffer, BoardModification modification);

}
