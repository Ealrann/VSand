package test.vulkan.gameoflife.pipelinepool;

import org.lwjgl.system.MemoryStack;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;

import test.vulkan.gameoflife.compute.Board;
import test.vulkan.gameoflife.compute.LifeComputerPipeline;

public class BoardPool implements IPipelinePool
{
	private LogicalDevice logicalDevice;
	private Board board;

	private CommandPool commandPool;

	private LifeComputerPipeline lifePipeline;

	public BoardPool(LogicalDevice logicalDevice, Board board)
	{
		this.logicalDevice = logicalDevice;
		this.board = board;

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			commandPool = CommandPool.alloc(stack, logicalDevice,
					logicalDevice.getQueueManager().getGraphicQueueIndex());
		}

		buildPipelines();
	}

	public void buildPipelines()
	{
		lifePipeline = new LifeComputerPipeline(logicalDevice, commandPool, board);
	}

	@Override
	public void load(MemoryStack stack, long surface, int width, int height)
	{
		lifePipeline.load();
	}

	@Override
	public void execute()
	{
		// vkQueueSubmit(logicalDevice.getQueueManager().getComputeQueue(),
		// lifePipeline.getSubmitInfo(), VK_NULL_HANDLE);
	}

	@Override
	public void resize(long surface, int width, int height)
	{}

	@Override
	public void free()
	{
		commandPool.free();
	}

	@Override
	public CommandPool getCommandPool()
	{
		return commandPool;
	}

}
