package org.sheepy.vsand.loader;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.IAdapter;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.TransformationUtil;

@Adapter(scope = Buffer.class, name = "Transformation", lazy = false)
public final class TransformationBufferLoader implements IAdapter
{
	@Load
	private static void load(Buffer buffer)
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

		buffer.setSize(size);
		buffer.setData(byteBuffer);
	}

	@Dispose
	private static void dispose(Buffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
