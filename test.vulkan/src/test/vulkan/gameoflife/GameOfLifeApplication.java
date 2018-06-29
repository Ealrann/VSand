package test.vulkan.gameoflife;

import static org.lwjgl.vulkan.VK10.vkQueueWaitIdle;

import org.sheepy.lily.game.vulkan.VulkanApplication;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;

import test.vulkan.gameoflife.compute.Board;
import test.vulkan.gameoflife.pipelinepool.BoardPool;
import test.vulkan.gameoflife.pipelinepool.RenderPipelinePool;

public class GameOfLifeApplication extends VulkanApplication
{
	private IPipelinePool boardPool;
	private IPipelinePool renderPool;

	public GameOfLifeApplication(int width, int height)
	{
		super(width, height);

		LogicalDevice logicalDevice = initLogicalDevice();

		Board board = createBoard(logicalDevice, width, height);

		boardPool = new BoardPool(logicalDevice, board);
		renderPool = new RenderPipelinePool(logicalDevice, board);

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);
	}

	private Board createBoard(LogicalDevice logicalDevice, int width, int height)
	{
		Board board = new Board(logicalDevice, width, height);

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

		// board.activate(0, 0);
		// board.activate(0, 1);
		// board.activate(0, 2);
		// board.activate(0, 4);
		// board.activate(1, 5);
		// board.activate(2, 6);
		// board.activate(3, 7);
		// board.activate(4, 8);
		//
		// board.activate(-width / 2, -height / 2);
		// board.activate((width / 2) - 1, (height / 2) - 1);

		return board;
	}

	@Override
	public void drawFrame()
	{
		vkQueueWaitIdle(logicalDevice.getQueueManager().getPresentQueue());

		for (IPipelinePool iPipelinePool : pipelinePools)
		{
			iPipelinePool.execute();
		}
	}

}
