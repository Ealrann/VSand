package org.sheepy.vulkan.sand.board;

import org.sheepy.vulkan.sand.board.builder.CircleBuilder;
import org.sheepy.vulkan.sand.board.builder.IShapeBuilder;
import org.sheepy.vulkan.sand.board.builder.LineBuilder;
import org.sheepy.vulkan.sand.board.builder.SquareBuilder;

public enum EShape
{
	Square(new SquareBuilder()),
	Circle(new CircleBuilder()),
	Line(new LineBuilder());

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
