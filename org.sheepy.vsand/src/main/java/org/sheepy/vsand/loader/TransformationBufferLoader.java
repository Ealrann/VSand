package org.sheepy.vsand.loader;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.vulkanresource.DataBuffer;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.TransformationUtil;

@ModelExtender(scope = DataBuffer.class, name = "Transformation")
@Adapter(singleton = true, lazy = false)
public final class TransformationBufferLoader implements IExtender
{
	@Load
	private static void load(DataBuffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final int[] transfoArray = TransformationUtil.toArray(application);
		final var materials = application.getMaterials().getMaterials();
		final int materialCount = materials.size();
		final int size = materialCount * materialCount * Integer.BYTES;
		final var byteBuffer = MemoryUtil.memAlloc(size);

		byteBuffer.asIntBuffer().put(transfoArray);
		byteBuffer.position(size);
		byteBuffer.flip();

		buffer.setData(byteBuffer);
	}

	@Dispose
	private static void dispose(DataBuffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
