package org.sheepy.vulkan.sand.board.builder;

import java.nio.ByteBuffer;

import org.sheepy.vulkan.sand.board.BoardModifications.Modification;

public interface IShapeBuilder
{
	void fillBuffer(ByteBuffer shapeBuffer, Modification modification);

}
