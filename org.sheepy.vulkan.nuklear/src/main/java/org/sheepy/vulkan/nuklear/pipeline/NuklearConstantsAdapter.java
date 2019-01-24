package org.sheepy.vulkan.nuklear.pipeline;

import java.nio.ByteBuffer;

import org.eclipse.emf.ecore.EClass;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.vulkan.api.window.IWindowListener;
import org.sheepy.lily.vulkan.api.window.Surface;
import org.sheepy.lily.vulkan.common.allocation.adapter.IFlatAllocableAdapter;
import org.sheepy.lily.vulkan.common.device.LogicalDevice;
import org.sheepy.vulkan.nuklear.model.NuklearPackage;
import org.sheepy.lily.vulkan.process.graphic.process.IGraphicContextAdapter;
import org.sheepy.lily.vulkan.resource.buffer.AbstractConstantsAdapter;

public class NuklearConstantsAdapter extends AbstractConstantsAdapter
		implements IFlatAllocableAdapter
{
	private final int SIZE = 16 * 4;
	private ByteBuffer buffer;
	private LogicalDevice logicalDevice;
	private boolean needRecord = true;

	private final IWindowListener windowListener = new IWindowListener()
	{
		@Override
		public void onWindowResize(Surface surface)
		{
			needRecord = true;
		}
	};

	@Override
	public void flatAllocate(MemoryStack stack)
	{
		var context = IGraphicContextAdapter.adapt(target).getContext(target);
		logicalDevice = context.logicalDevice;
		buffer = MemoryUtil.memAlloc(SIZE);
		logicalDevice.window.addListener(windowListener);
	}

	@Override
	public void free()
	{
		MemoryUtil.memFree(buffer);
		logicalDevice.window.removeListener(windowListener);
	}

	@Override
	public boolean needRecord()
	{
		return needRecord;
	}

	@Override
	public boolean isAllocationDirty()
	{
		return false;
	}

	@Override
	protected int getSize()
	{
		return SIZE;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return NuklearPackage.Literals.NUKLEAR_CONSTANTS == eClass;
	}

	@Override
	public ByteBuffer getData()
	{
		Surface surface = logicalDevice.window.getSurface();
		int width = surface.width;
		int height = surface.height;

		buffer.putFloat(2.0f / width);
		buffer.putFloat(0.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(-2.0f / height);
		buffer.putFloat(0.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(-1.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(-1.0f);
		buffer.putFloat(1.0f);
		buffer.putFloat(0.0f);
		buffer.putFloat(1.0f);

		buffer.flip();

		needRecord = false;
		return buffer;
	}
}
