package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;
import java.util.Random;

import org.eclipse.emf.ecore.EClass;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.vulkan.common.allocation.adapter.IAllocableAdapter;
import org.sheepy.lily.vulkan.common.allocation.common.IAllocationContext;
import org.sheepy.lily.vulkan.resource.buffer.AbstractConstantsAdapter;
import org.sheepy.vsand.model.VSandConstants;
import org.sheepy.vsand.model.VSandPackage;

public class VSandConstantAdapter extends AbstractConstantsAdapter implements IAllocableAdapter
{
	private final int BYTE_SIZE = Integer.BYTES * 3;
	private final Random random = new Random(System.nanoTime());

	private ByteBuffer buffer = null;

	@Override
	public void allocate(MemoryStack stack, IAllocationContext context)
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);
	}

	@Override
	public void free(IAllocationContext context)
	{
		MemoryUtil.memFree(buffer);
	}

	@Override
	protected int getSize()
	{
		return BYTE_SIZE;
	}

	@Override
	public ByteBuffer getData()
	{
		var pc = (VSandConstants) target;
		int firstPass = 0;
		if (pc.isFirstPass())
		{
			firstPass = 1;
			pc.setFirstPass(false);
		}

		float rNumber = random.nextFloat();

		buffer.putFloat(rNumber);
		buffer.putInt(firstPass);
		buffer.putInt(pc.isShowSleepZones() ? 1 : 0);
		buffer.flip();

		return buffer;
	}

	@Override
	public boolean needRecord()
	{
		return true;
	}

	@Override
	public boolean isAllocationDirty(IAllocationContext context)
	{
		return false;
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return VSandPackage.Literals.VSAND_CONSTANTS == eClass;
	}
}
