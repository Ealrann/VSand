package org.sheepy.vsand.draw;

import java.nio.ByteBuffer;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.vsand.model.DrawSquare;
import org.sheepy.vsand.model.Materials;

@Adapter(scope = DrawSquare.class)
public class DrawSquareAdapter implements IDrawCommandAdapter<DrawSquare>
{
	@Override
	public void fillBuffer(DrawSquare command, ByteBuffer shapeBuffer)
	{
		final int size = command.getSize();
		final int halfSize = size >> 1; // Divide by 2
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
