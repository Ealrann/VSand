package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkQueueSubmit;

import org.lwjgl.system.MemoryStack;
import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;

import test.vulkan.gameoflife.Board;
import test.vulkan.gameoflife.compute.LifeComputerPipeline;
import test.vulkan.gameoflife.compute.PixelComputerPipeline;

public class BoardPool implements IPipelinePool
{
	private LogicalDevice logicalDevice;
	private Board board;

	private CommandPool commandPool;

	private LifeComputerPipeline lifePipeline1;
	private LifeComputerPipeline lifePipeline2;

	private PixelComputerPipeline pixelPipeline;

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
		lifePipeline1 = new LifeComputerPipeline(logicalDevice, commandPool, board);
		lifePipeline2 = new LifeComputerPipeline(logicalDevice, commandPool, board);

		lifePipeline1.attachSourcePipeline(lifePipeline2);
		lifePipeline2.attachSourcePipeline(lifePipeline1);

		pixelPipeline = new PixelComputerPipeline(logicalDevice, commandPool,
				lifePipeline1.getBoardBuffer());
	}

	@Override
	public void load(MemoryStack stack, long surface, int width, int height)
	{
		lifePipeline1.load();
		lifePipeline2.load();

		// The lifePipeline1 wait for the lifePipeline2. But it can't be
		// signaled until we run it once. So we need to do it manually now.
		lifePipeline1.getSubmission().getWaitSemaphores().get(0).signalSemaphore(getCommandPool(),
				logicalDevice.getQueueManager().getComputeQueue());

		pixelPipeline.load();
	}

	@Override
	public void execute()
	{
		vkQueueSubmit(logicalDevice.getQueueManager().getComputeQueue(),
				lifePipeline1.getSubmitInfo(), VK_NULL_HANDLE);

		vkQueueSubmit(logicalDevice.getQueueManager().getComputeQueue(),
				lifePipeline2.getSubmitInfo(), VK_NULL_HANDLE);

		vkQueueSubmit(logicalDevice.getQueueManager().getComputeQueue(),
				pixelPipeline.getSubmitInfo(), VK_NULL_HANDLE);
	}

	@Override
	public void resize(long surface, int width, int height)
	{}

	@Override
	public void free()
	{
		pixelPipeline.free();
		lifePipeline1.free();
		lifePipeline2.free();
		commandPool.free();
	}

	@Override
	public CommandPool getCommandPool()
	{
		return commandPool;
	}

	public Image getImage()
	{
		return pixelPipeline.getImage();
	}
}
