package org.sheepy.vulkan.sand.board.builder;

import java.nio.IntBuffer;

import org.sheepy.vulkan.sand.board.EShapeSize;

public interface IShapeBuilder
{

	void fillBuffer(IntBuffer shapeBuffer, EShapeSize size, int value);

}
