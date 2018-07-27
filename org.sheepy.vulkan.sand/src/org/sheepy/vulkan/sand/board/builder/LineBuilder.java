package org.sheepy.vulkan.sand.board.builder;

import java.nio.ByteBuffer;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.sheepy.vulkan.sand.board.BoardModifications.Modification;

public class LineBuilder implements IShapeBuilder
{
	@Override
	public void fillBuffer(ByteBuffer shapeBuffer, Modification modification)
	{
		Vector2i p1 = new Vector2i(modification.oldX, modification.oldY);
		Vector2i p2 = new Vector2i(modification.x, modification.y);

		int dx = p2.x - p1.x;
		int dy = p2.y - p1.y;

		// Vecteur normal
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

		Vector2f v1 = new Vector2f(vx, vy);

		if (dx > 0) v1.negate();
		else if (dx == 0 && dy > 0) v1.negate();

		int halfSize = modification.size.getSize() >> 1;

		// Set length of vector to halfSize.
		v1.normalize(halfSize);

		Vector2f v2 = new Vector2f(v1);
		v2.negate();

		Vector2f p1_1 = new Vector2f(p1);
		p1_1.add(v1);
		Vector2f p1_2 = new Vector2f(p1);
		p1_2.add(v2);
		Vector2f p2_1 = new Vector2f(p2);
		p2_1.add(v2);
		Vector2f p2_2 = new Vector2f(p2);
		p2_2.add(v1);

		shapeBuffer.putInt(modification.x);
		shapeBuffer.putInt(modification.y);

		int minX = (int) Math
				.floor(Math.min(Math.min(Math.min(p1_1.x, p1_2.x), p2_1.x), p2_2.x) - halfSize);
		int minY = (int) Math
				.floor(Math.min(Math.min(Math.min(p1_1.y, p1_2.y), p2_1.y), p2_2.y) - halfSize);
		int maxX = (int) Math
				.ceil(Math.max(Math.max(Math.max(p1_1.x, p1_2.x), p2_1.x), p2_2.x) + halfSize);
		int maxY = (int) Math
				.ceil(Math.max(Math.max(Math.max(p1_1.y, p1_2.y), p2_1.y), p2_2.y) + halfSize);

		shapeBuffer.putInt(minX);
		shapeBuffer.putInt(minY);
		shapeBuffer.putInt(maxX);
		shapeBuffer.putInt(maxY);

		shapeBuffer.putInt((int) Math.round(p1_1.x));
		shapeBuffer.putInt((int) Math.round(p1_1.y));

		shapeBuffer.putInt((int) Math.round(p1_2.x));
		shapeBuffer.putInt((int) Math.round(p1_2.y));

		shapeBuffer.putInt((int) Math.round(p2_1.x));
		shapeBuffer.putInt((int) Math.round(p2_1.y));

		shapeBuffer.putInt((int) Math.round(p2_2.x));
		shapeBuffer.putInt((int) Math.round(p2_2.y));
	}
}
