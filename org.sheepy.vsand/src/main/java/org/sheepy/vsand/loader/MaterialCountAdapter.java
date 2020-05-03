package org.sheepy.vsand.loader;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = ConstantBuffer.class, name = "MaterialCount")
@Adapter(singleton = true, lazy = false)
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
