package org.sheepy.vsand.load.constant;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.logoce.adapter.api.Adapter;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = ConstantBuffer.class, name = "BoardSpecializationConstants")
@Adapter(singleton = true)
@AutoLoad
public final class MaterialCountAdapter implements IAdapter
{
	@Load
	private static void load(ConstantBuffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final int count = application.getMaterials()
									 .getMaterials()
									 .size();
		final var bBuffer = MemoryUtil.memCalloc(3 * Integer.BYTES);
		final var size = application.getSize();
		bBuffer.putInt(count);
		bBuffer.putInt(size.x());
		bBuffer.putInt(size.y());
		bBuffer.flip();
		buffer.setData(bBuffer);
	}
}
