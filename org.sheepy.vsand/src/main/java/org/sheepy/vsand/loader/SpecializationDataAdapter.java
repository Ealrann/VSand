package org.sheepy.vsand.loader;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.util.ModelUtil;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = ConstantBuffer.class, name = "SpecializationData")
@Adapter(singleton = true)
@AutoLoad
public final class SpecializationDataAdapter implements IAdapter
{
	@Load
	private static void load(ConstantBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.root(buffer);
		final int count = application.materials().materials().size();
		final var size = application.size();
		assert size.x() % 16 == 0;
		assert size.y() % 16 == 0;
		final ByteBuffer bBuffer = MemoryUtil.memCalloc(3 * Integer.BYTES);
		bBuffer.putInt(count);
		bBuffer.putInt(size.x());
		bBuffer.putInt(size.y());
		bBuffer.flip();
		buffer.data(bBuffer);
	}
}
