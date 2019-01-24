package org.sheepy.vulkan.sand.logic.builder;

import java.nio.ByteBuffer;

import org.sheepy.vulkan.sand.logic.BoardModification;

public interface IShapeBuilder
{
	void fillBuffer(ByteBuffer shapeBuffer, BoardModification modification);

}
