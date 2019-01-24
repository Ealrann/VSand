package org.sheepy.vulkan.sand.logic;

import java.nio.ByteBuffer;

import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.Materials;

public class BoardModification
{
	public static final int MODIFICATION_BYTE_COUNT = 17;

	public final EShape shape;
	public final EShapeSize size;
	public final Material material;
	public final int x;
	public final int y;
	public final int oldX;
	public final int oldY;

	public BoardModification(	EShape shape,
								EShapeSize size,
								int x,
								int y,
								int oldX,
								int oldY,
								Material material)
	{
		this.shape = shape;
		this.size = size;
		this.x = x;
		this.y = y;
		this.material = material;

		this.oldX = oldX;
		this.oldY = oldY;
	}

	public void fillBuffer(ByteBuffer copyBuffer)
	{
		int size = this.size.getSize();

		copyBuffer.putInt(shape.ordinal());
		copyBuffer.putInt(size);

		shape.getBuilder().fillBuffer(copyBuffer, this);

		var materials = (Materials) material.eContainer();
		int index = materials.getMaterials().indexOf(material);

		copyBuffer.putInt(index);
	}
}