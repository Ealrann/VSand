package org.sheepy.vulkan.sand.board.builder;

import java.nio.ByteBuffer;

import org.joml.Vector2f;
import org.sheepy.vulkan.sand.board.BoardModifications.Modification;

public class LineBuilder implements IShapeBuilder
{
	@Override
	public void fillBuffer(ByteBuffer shapeBuffer, Modification modification)
	{

		Vector2f p1 = new Vector2f(modification.oldX, modification.oldY);
		Vector2f p2 = new Vector2f(modification.x, modification.y);

		// Vecteur normal
		float vx = 0;
		float vy = -1;
		
		if(p1.x != p2.x)
		{
			vx = (p2.y - p1.y) / (p2.x - p1.x);
		}
		else
		{
			vx = 1;
			vy = 0;
		}

		Vector2f v1 = new Vector2f(vx, vy);

		float halfSize = modification.size.getSize() / 2f;

		// Set length of vector to halfSize.
		v1.normalize(halfSize);

		Vector2f v2 = new Vector2f(v1);
		v2.mul(-1);

		Vector2f p1_1 = new Vector2f(p1);
		p1_1.add(v2);
		Vector2f p1_2 = new Vector2f(p1);
		p1_2.add(v1);
		Vector2f p2_1 = new Vector2f(p2);
		p2_1.add(v1);
		Vector2f p2_2 = new Vector2f(p2);
		p2_2.add(v2);

		shapeBuffer.putInt(modification.x);
		shapeBuffer.putInt(modification.y);

		int minX = (int) Math.min(Math.min(Math.min(p1_1.x, p1_2.x), p2_1.x), p2_2.x);
		int minY = (int) Math.min(Math.min(Math.min(p1_1.y, p1_2.y), p2_1.y), p2_2.y);
		int maxX = (int) Math.max(Math.max(Math.max(p1_1.x, p1_2.x), p2_1.x), p2_2.x);
		int maxY = (int) Math.max(Math.max(Math.max(p1_1.y, p1_2.y), p2_1.y), p2_2.y);

		shapeBuffer.putInt((int) (minX - halfSize));
		shapeBuffer.putInt((int) (minY - halfSize));
		shapeBuffer.putInt((int) (maxX + halfSize));
		shapeBuffer.putInt((int) (maxY + halfSize));

		shapeBuffer.putInt((int) p1_1.x);
		shapeBuffer.putInt((int) p1_1.y);

		shapeBuffer.putInt((int) p1_2.x);
		shapeBuffer.putInt((int) p1_2.y);

		shapeBuffer.putInt((int) p2_1.x);
		shapeBuffer.putInt((int) p2_1.y);

		shapeBuffer.putInt((int) p2_2.x);
		shapeBuffer.putInt((int) p2_2.y);
	}
}
