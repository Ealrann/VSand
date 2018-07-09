package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;

import org.lwjgl.system.MemoryStack;
import org.sheepy.lily.game.vulkan.buffer.Image;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputeProcessPool;

import test.vulkan.gameoflife.Board;
import test.vulkan.gameoflife.compute.BoardBuffer;
import test.vulkan.gameoflife.compute.BoardImage;
import test.vulkan.gameoflife.compute.LifeComputer;
import test.vulkan.gameoflife.compute.PixelComputer;

public class BoardPool implements IPipelinePool
{
	private LogicalDevice logicalDevice;
	private Board board;
	private BoardImage image;

	private CommandPool commandPool;

	private ComputeProcessPool boardProcesses;

	private int currentIndex = 0;
	private BoardBuffer[] boardBuffers;

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
		BoardBuffer boardBuffer1 = new BoardBuffer(logicalDevice, board);
		BoardBuffer boardBuffer2 = new BoardBuffer(logicalDevice, board);
		boardBuffers = new BoardBuffer[2];
		boardBuffers[0] = boardBuffer1;
		boardBuffers[1] = boardBuffer2;

		LifeComputer lifeComputer1 = new LifeComputer(logicalDevice, boardBuffer2, boardBuffer1);
		LifeComputer lifeComputer2 = new LifeComputer(logicalDevice, boardBuffer1, boardBuffer2);

		image = new BoardImage(logicalDevice);
		image.load(commandPool, logicalDevice.getQueueManager().getComputeQueue(), board.getWidth(),
				board.getHeight(), VK_FORMAT_R8G8B8A8_UNORM);

		PixelComputer pixelComputer1 = new PixelComputer(logicalDevice, boardBuffer1, image);
		PixelComputer pixelComputer2 = new PixelComputer(logicalDevice, boardBuffer2, image);

		ComputeProcess boardProcess1 = new ComputeProcess(logicalDevice);
		boardProcess1.addNewPipeline(lifeComputer1);
		boardProcess1.addNewPipeline(pixelComputer1);

		ComputeProcess boardProcess2 = new ComputeProcess(logicalDevice);
		boardProcess2.addNewPipeline(lifeComputer2);
		boardProcess2.addNewPipeline(pixelComputer2);

		boardProcesses = new ComputeProcessPool(logicalDevice, commandPool);
		boardProcesses.addProcess(boardProcess1);
		boardProcesses.addProcess(boardProcess2);
	}

	@Override
	public void load(MemoryStack stack, long surface, int width, int height)
	{
		boardBuffers[0].load(commandPool, logicalDevice.getQueueManager().getComputeQueue());
		boardBuffers[1].load(commandPool, logicalDevice.getQueueManager().getComputeQueue());

		boardProcesses.allocateNode(stack);
	}

	@Override
	public void execute()
	{
		boardProcesses.exectue(logicalDevice.getQueueManager().getComputeQueue(), currentIndex);

		currentIndex = currentIndex == 1 ? 0 : 1;
	}

	@Override
	public void resize(long surface, int width, int height)
	{}

	@Override
	public void free()
	{
		boardProcesses.freeNode();
		boardBuffers[0].free();
		boardBuffers[1].free();
		image.free();
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
