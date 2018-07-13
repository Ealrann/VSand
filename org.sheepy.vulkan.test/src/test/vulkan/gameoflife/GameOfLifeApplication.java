package test.vulkan.gameoflife;

import org.sheepy.vulkan.VulkanApplication;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.PipelinePool;

import test.vulkan.gameoflife.pipelinepool.BoardPool;
import test.vulkan.gameoflife.pipelinepool.RenderPipelinePool;

public class GameOfLifeApplication extends VulkanApplication
{
	private static final int TARGET_FPS = 60;
	private static final int FRAME_TIME_STEP_MS = (int) ((1f / TARGET_FPS) * 1000);

	private BoardPool boardPool;
	private PipelinePool renderPool;
	
	public GameOfLifeApplication(int width, int height)
	{
		super(width, height);

		LogicalDevice logicalDevice = initLogicalDevice();

		Board board = createBoard(logicalDevice, width, height);

		boardPool = new BoardPool(logicalDevice, board);
		renderPool = new RenderPipelinePool(logicalDevice, boardPool.getImage(), null);

		attachPipelinePool(boardPool);
		attachPipelinePool(renderPool);
	}

	private Board createBoard(LogicalDevice logicalDevice, int width, int height)
	{
		Board board = new Board(width, height);

		// Create little spaceshift
		board.activate(0, 200);
		board.activate(0, 202);
		board.activate(3, 200);
		board.activate(4, 201);
		board.activate(4, 202);
		board.activate(4, 203);
		board.activate(1, 203);
		board.activate(2, 203);
		board.activate(3, 203);

		// Create little spaceshift
		board.activate(0, 300);
		board.activate(0, 302);
		board.activate(3, 300);
		board.activate(4, 301);
		board.activate(4, 302);
		board.activate(4, 303);
		board.activate(1, 303);
		board.activate(2, 303);
		board.activate(3, 303);

		// Create little spaceshift
		board.activate(0, 400);
		board.activate(0, 402);
		board.activate(3, 400);
		board.activate(4, 401);
		board.activate(4, 402);
		board.activate(4, 403);
		board.activate(1, 403);
		board.activate(2, 403);
		board.activate(3, 403);

		return board;
	}

	private long nextRenderDate = 0;
	private int countFrame = 0;
	private long stopCountDate;
	private boolean countFrameEnabled = true;

	@Override
	public void mainLoop()
	{
		stopCountDate = System.currentTimeMillis() + 3000;
		nextRenderDate = System.currentTimeMillis() + FRAME_TIME_STEP_MS;
		super.mainLoop();
	}

	@Override
	public void drawFrame()
	{
		while (nextRenderDate > System.currentTimeMillis())
		{
			boardPool.execute();

			if (countFrameEnabled)
			{
				if (System.currentTimeMillis() > stopCountDate)
				{
					countFrameEnabled = false;
					System.out.println("UPS: " + (int) (countFrame / 3f));
				}
				else
				{
					countFrame ++;
				}
			}
		}

		nextRenderDate = System.currentTimeMillis() + FRAME_TIME_STEP_MS;
		renderPool.execute();
	}
}
