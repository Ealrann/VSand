package org.sheepy.vsand.loader;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = ConstantBuffer.class, name = "SpecializationData")
@Adapter(singleton = true)
@AutoLoad
public final class SpecializationDataAdapter implements IAdapter
{
	@Load
	private static void load(ConstantBuffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final int count = application.getMaterials().getMaterials().size();
		final var size = application.getSize();
		assert size.x() % 16 == 0;
		assert size.y() % 16 == 0;
		final ByteBuffer bBuffer = MemoryUtil.memCalloc(3 * Integer.BYTES);
		bBuffer.putInt(count);
		bBuffer.putInt(size.x());
		bBuffer.putInt(size.y());
		bBuffer.flip();
		buffer.setData(bBuffer);
	}
}
