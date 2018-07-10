package org.sheepy.vulkan.sand;

import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;

import java.util.Collections;

import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.board.EMaterial;
import org.sheepy.vulkan.sand.board.EShape;
import org.sheepy.vulkan.sand.board.EShapeSize;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.pipelinepool.BoardPipelinePool;
import org.sheepy.vulkan.sand.pipelinepool.RenderPipelinePool;

public class SandApplication extends VulkanApplication
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 576;

	private static final int ZOOM = 1;

	// in update per frame
	private int speed = 1;

	private BoardPipelinePool boardPool;
	private RenderPipelinePool renderPool;
	private BoardModifications boardModifications;

	// private static final int TARGET_FPS = 60;
	// private static final float FRAME_TIME_STEP_MS = (1f / TARGET_FPS) * 1000;

	public SandApplication()
	{
		super(WIDTH, HEIGHT);

		LogicalDevice logicalDevice = initLogicalDevice();

		boardModifications = new BoardModifications(logicalDevice, ZOOM);

		BoardImage image = new BoardImage(logicalDevice);
		image.load(WIDTH * ZOOM, HEIGHT * ZOOM, VK_FORMAT_R8G8B8A8_UNORM);

		boardPool = new BoardPipelinePool(logicalDevice, boardModifications, image);

		renderPool = new RenderPipelinePool(logicalDevice, image.getImage(),
				Collections.emptyList());

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);
	}

	@Override
	public void mainLoop()
	{
		super.mainLoop();
	}

	private int count = 0;

	@Override
	public void drawFrame()
	{
		if (count++ == 90)
		{
			boardModifications.pushModification(EShape.Square, EShapeSize.ES5, 300, 200,
					EMaterial.Sand);
			System.out.println("Put sand");
		}

		for (int i = 0; i < speed * ZOOM; i++)
		{
			boardPool.execute();
		}

		renderPool.execute();
	}

	public static void main(String[] args)
	{
		VulkanApplication app = new SandApplication();

		try
		{
			app.run();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
