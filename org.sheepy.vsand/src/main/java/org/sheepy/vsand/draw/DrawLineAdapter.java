package org.sheepy.vsand.draw;

import java.nio.ByteBuffer;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.vsand.model.DrawLine;
import org.sheepy.vsand.model.Materials;

@Adapter(scope = DrawLine.class)
public final class DrawLineAdapter implements IDrawCommandAdapter<DrawLine>
{
	@Override
	public void fillBuffer(DrawLine command, ByteBuffer shapeBuffer)
	{
		final Vector2i p1 = new Vector2i(command.getX1(), command.getY1());
		final Vector2i p2 = new Vector2i(command.getX2(), command.getY2());
		final var material = command.getMaterial();
		final var materials = (Materials) material.eContainer();
		final int index = materials.getMaterials().indexOf(material);

		final int dx = p2.x - p1.x;
		final int dy = p2.y - p1.y;

		// Vector normal
		float vx = 0;
		float vy = -1;

		if (dx != 0)
		{
			vx = (float) dy / dx;
		}
		else
		{
			vx = 1;
			vy = 0;
		}

		final Vector2f v1 = new Vector2f(vx, vy);

		if (dx > 0) v1.negate();
		else if (dx == 0 && dy > 0) v1.negate();

		final int halfSize = command.getSize() >> 1;

		// Set length of vector to halfSize.
		v1.normalize(halfSize);

		final Vector2f v2 = new Vector2f(v1);
		v2.negate();

		final Vector2f p1_1 = new Vector2f(p1);
		p1_1.add(v1);
		final Vector2f p1_2 = new Vector2f(p1);
		p1_2.add(v2);
		final Vector2f p2_1 = new Vector2f(p2);
		p2_1.add(v2);
		final Vector2f p2_2 = new Vector2f(p2);
		p2_2.add(v1);

		shapeBuffer.putInt(2);
		shapeBuffer.putInt(command.getSize());

		shapeBuffer.putInt(command.getX2());
		shapeBuffer.putInt(command.getY2());

		final int minX = (int) Math
				.floor(Math.min(Math.min(Math.min(p1_1.x, p1_2.x), p2_1.x), p2_2.x) - halfSize);
		final int minY = (int) Math
				.floor(Math.min(Math.min(Math.min(p1_1.y, p1_2.y), p2_1.y), p2_2.y) - halfSize);
		final int maxX = (int) Math
				.ceil(Math.max(Math.max(Math.max(p1_1.x, p1_2.x), p2_1.x), p2_2.x) + halfSize);
		final int maxY = (int) Math
				.ceil(Math.max(Math.max(Math.max(p1_1.y, p1_2.y), p2_1.y), p2_2.y) + halfSize);

		shapeBuffer.putInt(minX);
		shapeBuffer.putInt(minY);
		shapeBuffer.putInt(maxX);
		shapeBuffer.putInt(maxY);

		shapeBuffer.putInt(Math.round(p1_1.x));
		shapeBuffer.putInt(Math.round(p1_1.y));

		shapeBuffer.putInt(Math.round(p1_2.x));
		shapeBuffer.putInt(Math.round(p1_2.y));

		shapeBuffer.putInt(Math.round(p2_1.x));
		shapeBuffer.putInt(Math.round(p2_1.y));

		shapeBuffer.putInt(Math.round(p2_2.x));
		shapeBuffer.putInt(Math.round(p2_2.y));

		shapeBuffer.putInt(index);
	}
}
