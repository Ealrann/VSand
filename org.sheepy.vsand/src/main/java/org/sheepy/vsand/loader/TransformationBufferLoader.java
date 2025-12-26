package org.sheepy.vsand.loader;

import org.lwjgl.system.MemoryUtil;
import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.util.ModelUtil;
import org.sheepy.lily.core.api.adapter.Dispose;
import org.sheepy.lily.core.api.adapter.Load;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.vulkanresource.DataBuffer;
import org.sheepy.vsand.model.vsand.VSandApplication;
import org.sheepy.vsand.util.TransformationUtil;

@ModelExtender(scope = DataBuffer.class, name = "Transformation")
@Adapter(singleton = true)
@AutoLoad
public final class TransformationBufferLoader implements IAdapter
{
	@Load
	private static void load(DataBuffer buffer)
	{
		final var application = (VSandApplication) ModelUtil.root(buffer);
		final var materials = application.materials().materials();
		final int materialCount = materials.size();
		final int valueCount = materialCount * materialCount;
		final int[] transfoArray = TransformationUtil.toArray(application);
		final int paddedCount = ((valueCount + 3) / 4) * 4;
		final int size = paddedCount * Integer.BYTES;
		final var byteBuffer = MemoryUtil.memAlloc(size);

		final var intBuffer = byteBuffer.asIntBuffer();
		intBuffer.put(transfoArray);
		for (int i = valueCount; i < paddedCount; i++)
		{
			intBuffer.put(-1);
		}

		byteBuffer.position(size);
		byteBuffer.flip();

		buffer.data(byteBuffer);
	}

	@Dispose
	private static void dispose(DataBuffer buffer)
	{
		MemoryUtil.memFree(buffer.data());
		buffer.data(null);
	}
}
