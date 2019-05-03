package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.allocation.IAllocableAdapter;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.input.IVulkanInputManager;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.resource.buffer.AbstractConstantsAdapter;
import org.sheepy.vsand.logic.EShapeSize;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandConstants;
import org.sheepy.vulkan.allocation.IAllocationContext;

@Statefull
@Adapter(scope = VSandConstants.class)
public class VSandConstantAdapter extends AbstractConstantsAdapter implements IAllocableAdapter
{
	private final int BYTE_SIZE = Integer.BYTES * 7;

	private final Random random = new Random(System.nanoTime());
	private final VSandConstants constants;
	private final VSandApplication application;
	private final Vector2i boardSize;

	private ByteBuffer buffer = null;

	private IVulkanInputManager inputManager;

	public VSandConstantAdapter(VSandConstants constants)
	{
		super(constants);
		this.constants = constants;

		application = (VSandApplication) ModelUtil.getApplication(constants);
		boardSize = new Vector2i(application.getSize());
	}

	@Override
	public void allocate(MemoryStack stack, IAllocationContext context)
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);

		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		final var engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		inputManager = engineAdapter.getInputManager();
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
		int forceClear = 0;
		if (constants.isForceClear())
		{
			forceClear = 1;
		}

		final float rNumber = random.nextFloat();
		final var size = EShapeSize.values()[application.getBrushSize() - 1];

		buffer.putFloat(rNumber);
		buffer.putInt(forceClear);
		buffer.putInt(constants.isShowSleepZones() ? 1 : 0);
		buffer.putInt(size.getSize() >> 1);

		final var cursorPosition = convertToBoardPosition(inputManager.getCursorPosition());
		buffer.putInt(cursorPosition.x);
		buffer.putInt(cursorPosition.y);

		final Material mainMaterial = application.getMainMaterial();
		final int index = application.getMaterials().getMaterials().indexOf(mainMaterial);
		buffer.putInt(index);

		buffer.flip();

		return buffer;
	}

	private Vector2i convertToBoardPosition(Vector2f mousePos)
	{
		final Vector2i res = new Vector2i((int) mousePos.x, (int) mousePos.y);

		final int boardWidth = boardSize.x;
		final int boardHeight = boardSize.y;

		final int width = application.getSize().x;
		final int height = application.getSize().y;
		if (width != boardWidth || height != boardHeight)
		{
			res.x *= (float) boardWidth / width;
			res.y *= (float) boardHeight / height;
		}

		return res;
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
