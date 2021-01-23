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

@ModelExtender(scope = ConstantBuffer.class, name = "MaterialCount")
@Adapter(singleton = true)
@AutoLoad
public final class MaterialCountAdapter implements IAdapter
{
	@Load
	private static void load(ConstantBuffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final int count = application.getMaterials().getMaterials().size();
		final ByteBuffer bBuffer = MemoryUtil.memCalloc(4);
		bBuffer.putInt(count);
		bBuffer.flip();
		buffer.setData(bBuffer);
	}
}
