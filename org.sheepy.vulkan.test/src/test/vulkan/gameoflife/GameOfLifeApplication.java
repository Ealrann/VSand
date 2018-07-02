package test.vulkan.gameoflife;

import static org.lwjgl.vulkan.VK10.vkQueueWaitIdle;

import org.sheepy.lily.game.vulkan.VulkanApplication;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;

import test.vulkan.gameoflife.pipelinepool.BoardPool;
import test.vulkan.gameoflife.pipelinepool.RenderPipelinePool;

public class GameOfLifeApplication extends VulkanApplication
{

	private static final int TARGET_FPS = 60;
	private static final int FRAME_TIME_STEP_MS = (int) ((1f / TARGET_FPS) * 1000);
	
	private BoardPool boardPool;
	private IPipelinePool renderPool;

	public GameOfLifeApplication(int width, int height)
	{
		super(width, height);

		LogicalDevice logicalDevice = initLogicalDevice();

		Board board = createBoard(logicalDevice, width, height);

		boardPool = new BoardPool(logicalDevice, board);
		renderPool = new RenderPipelinePool(logicalDevice, boardPool.getBoardBuffer());

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);
	}

	private Board createBoard(LogicalDevice logicalDevice, int width, int height)
	{
		Board board = new Board(width, height);

		// Create little spaceshift
		board.activate(0, 0);
		board.activate(0, 2);
		board.activate(3, 0);
		board.activate(4, 1);
		board.activate(4, 2);
		board.activate(4, 3);
		board.activate(1, 3);
		board.activate(2, 3);
		board.activate(3, 3);

		return board;
	}

	private long nextRenderDate = 0;

	@Override
	public void drawFrame()
	{
		while (nextRenderDate > System.currentTimeMillis())
		{
			vkQueueWaitIdle(logicalDevice.getQueueManager().getComputeQueue());
			boardPool.execute();
		}

		nextRenderDate = System.currentTimeMillis() + FRAME_TIME_STEP_MS;
		renderPool.execute();
	}

}
