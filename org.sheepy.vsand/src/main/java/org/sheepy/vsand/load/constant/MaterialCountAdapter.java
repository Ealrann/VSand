package org.sheepy.vsand.load.constant;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;
import org.sheepy.vsand.model.VSandApplication;

import java.nio.ByteBuffer;

@ModelExtender(scope = ConstantBuffer.class, name = "BoardSpecializationConstants")
@Adapter(singleton = true, lazy = false)
public final class MaterialCountAdapter implements IExtender
{
	@Load
	private static void load(ConstantBuffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final int count = application.getMaterials().getMaterials().size();
		final var bBuffer = MemoryUtil.memCalloc(3 * Integer.BYTES);
		bBuffer.putInt(count);
		bBuffer.putInt(application.getSize().x());
		bBuffer.putInt(application.getSize().y());
		bBuffer.flip();
		buffer.setData(bBuffer);
	}
}
