package org.sheepy.vsand.logic;

import org.sheepy.vsand.logic.builder.CircleBuilder;
import org.sheepy.vsand.logic.builder.IShapeBuilder;
import org.sheepy.vsand.logic.builder.LineBuilder;
import org.sheepy.vsand.logic.builder.SquareBuilder;

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
