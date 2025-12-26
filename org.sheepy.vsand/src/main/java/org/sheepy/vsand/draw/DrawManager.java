package org.sheepy.vsand.draw;

import org.joml.Vector2ic;
import org.sheepy.lily.game.api.audio.AudioConfiguration;
import org.sheepy.lily.game.api.audio.IAudioAllocation;
import org.sheepy.lily.game.api.audio.IAudioHandle;
import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.DrawLine;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.util.EShapeSize;

public final class DrawManager
{
	private Vector2ic previousPosition;
	private Material material;
	private boolean firstDraw = false;

	public void start(Material material)
	{
		this.material = material;
		firstDraw = true;
	}

	public DrawCommand draw(final int brushSize, final Vector2ic position)
	{
		final DrawCommand newCommand;

		final var size = EShapeSize.values()[brushSize - 1];

		if (firstDraw || (previousPosition.x() == position.x() && previousPosition.y() == position.y()))
		{
			final var command = DrawCircle.builder().build();
			command.material(material);
			command.size(size.getSize());
			command.x(position.x());
			command.y(position.y());

			newCommand = command;
			firstDraw = false;
		}
		else
		{
			final var command = DrawLine.builder().build();
			command.material(material);
			command.size(size.getSize());
			command.x1(previousPosition.x());
			command.y1(previousPosition.y());
			command.x2(position.x());
			command.y2(position.y());

			newCommand = command;
		}

		previousPosition = position;
		return newCommand;
	}

	public Material getMaterial()
	{
		return material;
	}
}
