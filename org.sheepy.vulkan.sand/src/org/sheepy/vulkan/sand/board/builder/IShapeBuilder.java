package org.sheepy.vulkan.sand.board.builder;

import java.nio.ByteBuffer;

import org.sheepy.vulkan.sand.board.BoardModification;

public interface IShapeBuilder
{
	void fillBuffer(ByteBuffer shapeBuffer, BoardModification modification);

}
