package org.sheepy.vsand.loader;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.util.ModelUtil;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.vulkanresource.DataBuffer;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = DataBuffer.class, name = "Configuration")
@Adapter(singleton = true)
@AutoLoad
public final class ConfigurationBufferLoader implements IAdapter
{
	private static final int UNIT_BYTES = 8 * Integer.BYTES;
	private static final int MATERIAL_FLAG_PRESSURE_LIQUID = 1;

	@Load
	private static void load(DataBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.root(buffer);
		final var materials = application.materials().materials();
		final int materialCount = materials.size();
		final int size = UNIT_BYTES * materialCount;

		final ByteBuffer bBuffer = MemoryUtil.memAlloc(size);
		for (final Material material : materials)
		{
			bBuffer.putInt(material.isStatic() ? 1 : 0);
			bBuffer.putInt(material.density());
			bBuffer.putInt(material.runoff());

			bBuffer.putInt(materialFlags(material));

			// Color
			bBuffer.putFloat(material.r() / 255f);
			bBuffer.putFloat(material.g() / 255f);
			bBuffer.putFloat(material.b() / 255f);
			bBuffer.putFloat(0f);
		}
		bBuffer.flip();

		buffer.data(bBuffer);
	}

	@Dispose
	private static void dispose(DataBuffer buffer)
	{
		MemoryUtil.memFree(buffer.data());
		buffer.data(null);
	}

	private static int materialFlags(final Material material)
	{
		return isPressureLiquid(material) ? MATERIAL_FLAG_PRESSURE_LIQUID : 0;
	}

	private static boolean isPressureLiquid(final Material material)
	{
		final var name = material.name();
		if (name == null) return false;

		return switch (name)
		{
			case "Water", "LiquidWax", "Lava", "LavaBoiling", "Petrol", "PetrolFire", "HotWax", "Acid" -> true;
			default -> false;
		};
	}
}
