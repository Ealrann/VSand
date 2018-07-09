package org.sheepy.vulkan.sand.board;

import org.sheepy.vulkan.sand.board.builder.CircleBuilder;
import org.sheepy.vulkan.sand.board.builder.IShapeBuilder;
import org.sheepy.vulkan.sand.board.builder.SquareBuilder;

public enum EShape
{
	Square(new SquareBuilder()),
	Circle(new CircleBuilder());

	private IShapeBuilder builder;

	private EShape(IShapeBuilder builder)
	{
		this.builder = builder;
	}

	public IShapeBuilder getBuilder()
	{
		return builder;
	}
}
