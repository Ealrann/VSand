package org.sheepy.vsand.loader;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Load;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.MaterialUtil;
import org.sheepy.vsand.util.TransformationUtil;

@Adapter(scope = Buffer.class, name = "Transformation", lazy = false)
public final class TransformationBufferLoader implements IVulkanAdapter
{
	private static final int BYTE_SIZE = MaterialUtil.MAX_MATERIAL_NUMBER
			* MaterialUtil.MAX_MATERIAL_NUMBER
			* Integer.BYTES;

	@Load
	public static void load(Buffer buffer)
	{
		final var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);
		final var byteBuffer = MemoryUtil.memAlloc(BYTE_SIZE);
		final int[] transfoArray = TransformationUtil.toArray(application);

		byteBuffer.asIntBuffer().put(transfoArray);
		byteBuffer.position(BYTE_SIZE);
		byteBuffer.flip();

		buffer.setSize(BYTE_SIZE);
		buffer.setData(byteBuffer);
	}

	@Dispose
	public static void dispose(Buffer buffer)
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
