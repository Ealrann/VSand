package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;
import org.sheepy.vsand.logic.MaterialUtil;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = Buffer.class, name = "Configuration")
public class ConfigurationBufferLoader extends BufferAdapter
{
	private static final int BYTE_SIZE = MaterialUtil.MAX_MATERIAL_NUMBER * 4 * Integer.BYTES;

	public ConfigurationBufferLoader(Buffer buffer)
	{
		super(buffer);
		buffer.setSize(BYTE_SIZE);

		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);

		final ByteBuffer bBuffer = MemoryUtil.memAlloc(BYTE_SIZE);
		for (final Material material : application.getMaterials().getMaterials())
		{
			bBuffer.putInt(material.isIsStatic() ? 1 : 0);
			bBuffer.putInt(material.getDensity());
			bBuffer.putInt(material.getRunoff());

			final int rgb = material.getR() | material.getG() << 8 | material.getB() << 16;

			bBuffer.putInt(rgb);
		}
		bBuffer.flip();

		buffer.setData(bBuffer);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
