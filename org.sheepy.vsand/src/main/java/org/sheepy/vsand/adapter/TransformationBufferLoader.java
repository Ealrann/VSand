package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.resource.buffer.BufferAdapter;
import org.sheepy.vsand.logic.MaterialUtil;
import org.sheepy.vsand.logic.TransformationUtil;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = Buffer.class, name = "Transformation")
public class TransformationBufferLoader extends BufferAdapter
{
	private static final int BYTE_SIZE = MaterialUtil.MAX_MATERIAL_NUMBER
			* MaterialUtil.MAX_MATERIAL_NUMBER
			* Integer.BYTES;

	public TransformationBufferLoader(Buffer buffer)
	{
		super(buffer);

		var application = (VSandApplication) EcoreUtil.getRootContainer(buffer);

		buffer.setSize(BYTE_SIZE);

		ByteBuffer bBuffer = MemoryUtil.memAlloc(BYTE_SIZE);
		int[] transfoArray = TransformationUtil.toArray(application);
		for (int i = 0; i < transfoArray.length; i++)
		{
			bBuffer.putInt(transfoArray[i]);
		}
		bBuffer.flip();
		buffer.setData(bBuffer);
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer.getData());
		buffer.setData(null);
	}
}
