package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.VK10.*;

import org.lwjgl.system.MemoryStack;
import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;

import test.vulkan.gameoflife.Board;
import test.vulkan.gameoflife.compute.BoardImage;
import test.vulkan.gameoflife.compute.LifeComputerPipeline;
import test.vulkan.gameoflife.compute.PixelComputerPipeline;

public class BoardPool implements IPipelinePool
{
	private LogicalDevice logicalDevice;
	private Board board;
	private BoardImage image;

	private CommandPool commandPool;

	private LifeComputerPipeline[] lifePipelines;
	private PixelComputerPipeline[] pixelPipelines;

	private int currentIndex = 0;

	public BoardPool(LogicalDevice logicalDevice, Board board)
	{
		this.logicalDevice = logicalDevice;
		this.board = board;

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			commandPool = CommandPool.alloc(stack, logicalDevice,
					logicalDevice.getQueueManager().getComputeQueueIndex());
		}

		buildPipelines();
	}

	public void buildPipelines()
	{
		LifeComputerPipeline lifePipeline1 = new LifeComputerPipeline(logicalDevice, commandPool,
				board);
		LifeComputerPipeline lifePipeline2 = new LifeComputerPipeline(logicalDevice, commandPool,
				board);

		lifePipeline1.attachSourcePipeline(lifePipeline2);
		lifePipeline2.attachSourcePipeline(lifePipeline1);

		lifePipelines = new LifeComputerPipeline[2];
		lifePipelines[0] = lifePipeline1;
		lifePipelines[1] = lifePipeline2;

		image = new BoardImage(logicalDevice);
		image.load(commandPool, logicalDevice.getQueueManager().getComputeQueue(), board.getWidth(),
				board.getHeight(), VK_FORMAT_R8G8B8A8_UNORM);

		PixelComputerPipeline pixelPipeline1 = new PixelComputerPipeline(logicalDevice, commandPool,
				lifePipeline1.getBoardBuffer(), image);
		PixelComputerPipeline pixelPipeline2 = new PixelComputerPipeline(logicalDevice, commandPool,
				lifePipeline2.getBoardBuffer(), image);

		pixelPipelines = new PixelComputerPipeline[2];
		pixelPipelines[0] = pixelPipeline1;
		pixelPipelines[1] = pixelPipeline2;
	}

	@Override
	public void load(MemoryStack stack, long surface, int width, int height)
	{
		lifePipelines[0].load();
		lifePipelines[1].load();

		// The lifePipeline1 wait for the lifePipeline2. But it can't be
		// signaled until we run it once. So we need to do it manually now.
		lifePipelines[0].getSubmission().getWaitSemaphores().get(0).signalSemaphore(getCommandPool(),
				logicalDevice.getQueueManager().getComputeQueue());

		pixelPipelines[0].load();
		pixelPipelines[1].load();
	}

	@Override
	public void execute()
	{
		vkQueueSubmit(logicalDevice.getQueueManager().getComputeQueue(),
				lifePipelines[currentIndex].getSubmitInfo(), VK_NULL_HANDLE);

		vkQueueSubmit(logicalDevice.getQueueManager().getComputeQueue(),
				pixelPipelines[currentIndex].getSubmitInfo(), VK_NULL_HANDLE);

		currentIndex = currentIndex == 1 ? 0 : 1;
	}

	@Override
	public void resize(long surface, int width, int height)
	{}

	@Override
	public void free()
	{
		pixelPipelines[0].free();
		lifePipelines[0].free();
		pixelPipelines[1].free();
		lifePipelines[1].free();
		commandPool.free();
	}

	@Override
	public CommandPool getCommandPool()
	{
		return commandPool;
	}

	public Image getImage()
	{
		return image.getImage();
	}
}
