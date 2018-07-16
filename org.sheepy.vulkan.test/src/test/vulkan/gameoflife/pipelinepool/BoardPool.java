package test.vulkan.gameoflife.pipelinepool;

import static org.lwjgl.vulkan.VK10.VK_FORMAT_R8G8B8A8_UNORM;

import org.sheepy.vulkan.buffer.Image;
import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.PipelinePool;
import org.sheepy.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.vulkan.pipeline.compute.ComputeProcessPool;

import test.vulkan.gameoflife.Board;
import test.vulkan.gameoflife.compute.BoardBuffer;
import test.vulkan.gameoflife.compute.BoardImage;
import test.vulkan.gameoflife.compute.LifeCompute;
import test.vulkan.gameoflife.compute.PixelCompute;

public class BoardPool extends PipelinePool
{
	private LogicalDevice logicalDevice;
	private Board board;
	private BoardImage image;

	private ComputeProcessPool boardProcesses;

	private int currentIndex = 0;
	private BoardBuffer[] boardBuffers;

	public BoardPool(LogicalDevice logicalDevice, Board board)
	{
		super(logicalDevice, logicalDevice.getQueueManager().getComputeQueueIndex());
		this.logicalDevice = logicalDevice;
		this.board = board;

		buildPipelines();
	}

	public void buildPipelines()
	{
		BoardBuffer boardBuffer1 = new BoardBuffer(logicalDevice, board, commandPool,
				logicalDevice.getQueueManager().getComputeQueue());
		BoardBuffer boardBuffer2 = new BoardBuffer(logicalDevice, board, commandPool,
				logicalDevice.getQueueManager().getComputeQueue());
		boardBuffers = new BoardBuffer[2];
		boardBuffers[0] = boardBuffer1;
		boardBuffers[1] = boardBuffer2;

		subAllocationObjects.add(boardBuffer1);
		subAllocationObjects.add(boardBuffer2);

		image = new BoardImage(logicalDevice, commandPool,
				logicalDevice.getQueueManager().getComputeQueue(), board.getWidth(),
				board.getHeight(), VK_FORMAT_R8G8B8A8_UNORM);

		subAllocationObjects.add(image);

		ComputeProcess boardProcess1 = new ComputeProcess(logicalDevice);
		ComputeProcess boardProcess2 = new ComputeProcess(logicalDevice);

		DescriptorPool dPool1 = boardProcess1.getDescriptorPool();
		DescriptorPool dPool2 = boardProcess2.getDescriptorPool();

		LifeCompute lifeComputer1 = new LifeCompute(logicalDevice, dPool1, boardBuffer2,
				boardBuffer1);
		LifeCompute lifeComputer2 = new LifeCompute(logicalDevice, dPool2, boardBuffer1,
				boardBuffer2);

		PixelCompute pixelComputer1 = new PixelCompute(logicalDevice, dPool1, boardBuffer1, image);
		PixelCompute pixelComputer2 = new PixelCompute(logicalDevice, dPool2, boardBuffer2, image);

		boardProcess1.addPipeline(lifeComputer1);
		boardProcess1.addPipeline(pixelComputer1);

		boardProcess2.addPipeline(lifeComputer2);
		boardProcess2.addPipeline(pixelComputer2);

		boardProcesses = new ComputeProcessPool(logicalDevice, commandPool);
		boardProcesses.addProcess(boardProcess1);
		boardProcesses.addProcess(boardProcess2);

		subAllocationObjects.add(boardProcesses);
	}

	@Override
	public void execute()
	{
		boardProcesses.exectue(logicalDevice.getQueueManager().getComputeQueue(), currentIndex);

		currentIndex = currentIndex == 1 ? 0 : 1;
	}

	public Image getImage()
	{
		return image.getImage();
	}
}
