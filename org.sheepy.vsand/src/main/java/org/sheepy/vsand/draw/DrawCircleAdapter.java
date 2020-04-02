package org.sheepy.vsand.draw;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.vsand.model.DrawCircle;
import org.sheepy.vsand.model.Materials;

import java.nio.ByteBuffer;

@Adapter(scope = DrawCircle.class)
public final class DrawCircleAdapter implements IDrawCommandAdapter<DrawCircle>
{
	@Override
	public void fillBuffer(DrawCircle command, ByteBuffer shapeBuffer)
	{
		final int size = command.getSize();
		final int halfSize = size >> 1; // Divide by 2
		final var material = command.getMaterial();
		final var materials = (Materials) material.eContainer();
		final int index = materials.getMaterials().indexOf(material);

		shapeBuffer.putInt(1);
		shapeBuffer.putInt(size);

		shapeBuffer.putInt(command.getX());
		shapeBuffer.putInt(command.getY());

		shapeBuffer.putInt(command.getX() - halfSize);
		shapeBuffer.putInt(command.getY() - halfSize);
		shapeBuffer.putInt(command.getX() + halfSize);
		shapeBuffer.putInt(command.getY() + halfSize);

		// Unused here
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);

		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);
		shapeBuffer.putInt(0);

		shapeBuffer.putInt(index);
	}
}
