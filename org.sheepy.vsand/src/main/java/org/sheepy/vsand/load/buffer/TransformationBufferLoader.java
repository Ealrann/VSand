package org.sheepy.vsand.load.buffer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.vulkan.model.vulkanresource.DataBuffer;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.TransformationUtil;

@ModelExtender(scope = DataBuffer.class, name = "Transformation")
@Adapter(singleton = true)
@AutoLoad
public final class TransformationBufferLoader implements IAdapter
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
