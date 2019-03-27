package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.api.allocation.IAllocationContext;
import org.sheepy.lily.vulkan.api.allocation.adapter.IAllocableAdapter;
import org.sheepy.lily.vulkan.resource.buffer.AbstractConstantsAdapter;
import org.sheepy.vsand.model.VSandConstants;

@Statefull
@Adapter(scope = VSandConstants.class)
public class VSandConstantAdapter extends AbstractConstantsAdapter implements IAllocableAdapter
{
	private final int BYTE_SIZE = Integer.BYTES * 3;

	private final Random random = new Random(System.nanoTime());
	private final VSandConstants constants;

	private ByteBuffer buffer = null;

	public VSandConstantAdapter(VSandConstants constants)
	{
		this.constants = constants;
	}

	public void tick()
	{
		constants.setFirstPass(true);
	}
	
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
		int firstPass = 0;
		if (constants.isFirstPass())
		{
			firstPass = 1;
			constants.setFirstPass(false);
		}

		float rNumber = random.nextFloat();

		buffer.putFloat(rNumber);
		buffer.putInt(firstPass);
		buffer.putInt(constants.isShowSleepZones() ? 1 : 0);
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
}
