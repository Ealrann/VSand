package org.sheepy.vsand.draw;

import org.logoce.adapter.api.Adapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.vsand.model.DrawSquare;
import org.sheepy.vsand.model.Materials;

import java.nio.ByteBuffer;

@ModelExtender(scope = DrawSquare.class)
@Adapter(singleton = true)
public final class DrawSquareAdapter implements IDrawCommandAdapter<DrawSquare>
{
	@Override
	public void fillBuffer(DrawSquare command, ByteBuffer shapeBuffer)
	{
		final int size = command.getSize();
		final int halfSize = size >> 1; // Divide by 2 :)
		final var material = command.getMaterial();
		final var materials = (Materials) material.eContainer();
		final int index = materials.getMaterials().indexOf(material);

		shapeBuffer.putInt(0);
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
