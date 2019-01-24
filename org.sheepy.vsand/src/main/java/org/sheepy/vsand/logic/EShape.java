package org.sheepy.vulkan.sand.logic;

import org.sheepy.vulkan.sand.logic.builder.CircleBuilder;
import org.sheepy.vulkan.sand.logic.builder.IShapeBuilder;
import org.sheepy.vulkan.sand.logic.builder.LineBuilder;
import org.sheepy.vulkan.sand.logic.builder.SquareBuilder;

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
