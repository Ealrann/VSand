package org.sheepy.vsand.adapter;

import java.nio.ByteBuffer;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.system.MemoryUtil;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Autorun;
import org.sheepy.lily.core.api.adapter.annotation.Dispose;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.core.api.adapter.annotation.Tick;
import org.sheepy.lily.core.api.util.ModelUtil;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.input.IVulkanInputManager;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.PushConstant;
import org.sheepy.vsand.logic.EShapeSize;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

@Statefull
@Adapter(scope = PushConstant.class, name = "VSandPushConstants")
public class VSandPushConstantAdapter implements IVulkanAdapter
{
	private final int BYTE_SIZE = Integer.BYTES * 7;

	private final Random random = new Random(System.nanoTime());
	private final PushConstant pushConstant;
	private final VSandApplication application;
	private final Vector2i boardSize;

	private ByteBuffer buffer = null;

	private IVulkanInputManager inputManager;

	public VSandPushConstantAdapter(PushConstant pushConstant)
	{
		this.pushConstant = pushConstant;

		application = (VSandApplication) ModelUtil.getApplication(pushConstant);
		boardSize = new Vector2i(application.getSize());
	}

	@Autorun
	public void load()
	{
		buffer = MemoryUtil.memAlloc(BYTE_SIZE);

		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		final var engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		inputManager = engineAdapter.getInputManager();

		updateBuffer();
	}

	@Dispose
	public void dispose()
	{
		MemoryUtil.memFree(buffer);
	}

	@Tick
	public void updateBuffer()
	{
		int forceClear = 0;
		if (application.isForceClear())
		{
			forceClear = 1;
		}

		final float rNumber = random.nextFloat();
		final var size = EShapeSize.values()[application.getBrushSize() - 1];

		buffer.putFloat(rNumber);
		buffer.putInt(forceClear);
		buffer.putInt(application.isShowSleepZones() ? 1 : 0);
		buffer.putInt(size.getSize() >> 1);

		final var cursorPosition = convertToBoardPosition(inputManager.getCursorPosition());
		buffer.putInt(cursorPosition.x);
		buffer.putInt(cursorPosition.y);

		final Material mainMaterial = application.getMainMaterial();
		final int index = application.getMaterials().getMaterials().indexOf(mainMaterial);
		buffer.putInt(index);

		buffer.flip();

		pushConstant.setData(buffer);
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
}
