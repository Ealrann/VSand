package org.sheepy.vsand.draw;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.Materials;

import java.nio.ByteBuffer;

@ModelExtender(scope = DrawCircle.class)
@Adapter(singleton = true)
public final class DrawCircleAdapter implements IDrawCommandAdapter<DrawCircle>
{
	@Override
	public void fillBuffer(DrawCircle command, ByteBuffer shapeBuffer)
	{
		final int size = command.size();
		final int halfSize = size >> 1; // Divide by 2 :)
		final var material = command.material();
		final var materials = (Materials) material.lmContainer();
		final int index = materials.materials().indexOf(material);

		shapeBuffer.putInt(1);
		shapeBuffer.putInt(size);

		shapeBuffer.putInt(command.x());
		shapeBuffer.putInt(command.y());

		shapeBuffer.putInt(command.x() - halfSize);
		shapeBuffer.putInt(command.y() - halfSize);
		shapeBuffer.putInt(command.x() + halfSize);
		shapeBuffer.putInt(command.y() + halfSize);

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
