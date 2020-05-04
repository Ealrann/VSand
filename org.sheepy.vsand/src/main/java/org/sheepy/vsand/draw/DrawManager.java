package org.sheepy.vsand.draw;

import org.joml.Vector2ic;
import org.sheepy.lily.game.api.audio.AudioConfiguration;
import org.sheepy.lily.game.api.audio.IAudioAllocation;
import org.sheepy.lily.game.api.audio.IAudioHandle;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;
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
			final var command = VSandFactory.eINSTANCE.createDrawCircle();
			command.setMaterial(material);
			command.setSize(size.getSize());
			command.setX(position.x());
			command.setY(position.y());

			newCommand = command;
			firstDraw = false;
		}
		else
		{
			final var command = VSandFactory.eINSTANCE.createDrawLine();
			command.setMaterial(material);
			command.setSize(size.getSize());
			command.setX1(previousPosition.x());
			command.setY1(previousPosition.y());
			command.setX2(position.x());
			command.setY2(position.y());

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