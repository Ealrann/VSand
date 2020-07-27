package org.sheepy.vsand.loader;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.resource.DataBuffer;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = DataBuffer.class, name = "Configuration")
@Adapter(singleton = true, lazy = false)
public final class ConfigurationBufferLoader implements IExtender
{
	private static final int UNIT_BYTES = 8 * Integer.BYTES;

	@Load
	private static void load(DataBuffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final var materials = application.getMaterials().getMaterials();
		final int materialCount = materials.size();
		final int size = UNIT_BYTES * materialCount;

		final ByteBuffer bBuffer = MemoryUtil.memAlloc(size);
		for (final Material material : materials)
		{
			bBuffer.putInt(material.isIsStatic() ? 1 : 0);
			bBuffer.putInt(material.getDensity());
			bBuffer.putInt(material.getRunoff());
			bBuffer.putInt(material.getType().getValue());

			// Color
			bBuffer.putFloat(material.getR() / 255f);
			bBuffer.putFloat(material.getG() / 255f);
			bBuffer.putFloat(material.getB() / 255f);
			bBuffer.putFloat(0f);
		}
		bBuffer.flip();

		buffer.setData(bBuffer);
	}

	@Dispose
	private static void dispose(DataBuffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
