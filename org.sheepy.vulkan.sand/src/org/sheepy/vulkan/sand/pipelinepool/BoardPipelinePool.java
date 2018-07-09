package org.sheepy.vulkan.sand.pipelinepool;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.command.CommandPool;
import org.sheepy.lily.game.vulkan.command.SingleTimeCommand;
import org.sheepy.lily.game.vulkan.descriptor.IDescriptor;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.IPipelinePool;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.lily.game.vulkan.pipeline.compute.ComputeProcessPool;
import org.sheepy.lily.game.vulkan.pipeline.compute.Computer;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.compute.ConfigurationBuffer;
import org.sheepy.vulkan.sand.compute.DrawComputer;
import org.sheepy.vulkan.sand.compute.PixelComputer;

public class BoardPipelinePool implements IPipelinePool
{

	private static final String SHADER_VERT_PAIR = "org/sheepy/vulkan/sand/game_step_vert_pair.comp.spv";
	private static final String SHADER_VERT_IMPAIR = "org/sheepy/vulkan/sand/game_step_vert_impair.comp.spv";

	private LogicalDevice logicalDevice;

	private BoardModifications boardModifications;
	private BoardImage boardImage;

	private CommandPool commandPool;

	private ComputeProcessPool boardProcesses;
	private ComputeProcessPool mergeBoardProcesses;

	private ConfigurationBuffer configBuffer;
	private Buffer boardBuffer;
	private Buffer movedBuffer;
	private Buffer movedCleanBuffer;

	public BoardPipelinePool(LogicalDevice logicalDevice, BoardModifications boardModifications,
			BoardImage boardImage)
	{
		this.logicalDevice = logicalDevice;
		this.boardModifications = boardModifications;
		this.boardImage = boardImage;

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			commandPool = CommandPool.alloc(stack, logicalDevice,
					logicalDevice.getQueueManager().getComputeQueueIndex());
		}

		createBuffers();
		buildPipelines();
	}

	private void createBuffers()
	{
		int width = boardModifications.getWidth();
		int height = boardModifications.getHeight();

		// configBuffer
		{
			configBuffer = new ConfigurationBuffer(logicalDevice);
		}

		// boardBuffer
		{
			int boardSizeByte = width * height * Integer.BYTES;
			boardBuffer = Buffer.alloc(logicalDevice, boardSizeByte,
					VK_BUFFER_USAGE_STORAGE_BUFFER_BIT
							| VK_BUFFER_USAGE_TRANSFER_SRC_BIT
							| VK_BUFFER_USAGE_TRANSFER_DST_BIT,
					VK_MEMORY_PROPERTY_HOST_COHERENT_BIT | VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT);
			boardBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
					VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
		}

		// MovedBuffer
		{
			int movedSizeByte = (int) Math.ceil(width * height / 8f);
			movedBuffer = Buffer.alloc(logicalDevice, movedSizeByte,
					VK_BUFFER_USAGE_STORAGE_BUFFER_BIT | VK_BUFFER_USAGE_TRANSFER_DST_BIT,
					VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
			movedBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
					VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);

			movedCleanBuffer = Buffer.alloc(logicalDevice, movedSizeByte,
					VK_BUFFER_USAGE_STORAGE_BUFFER_BIT
							| VK_BUFFER_USAGE_TRANSFER_SRC_BIT
							| VK_BUFFER_USAGE_TRANSFER_DST_BIT,
					VK_MEMORY_PROPERTY_DEVICE_LOCAL_BIT);
			movedCleanBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
					VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
		}
	}

	private void buildPipelines()
	{
		int width = boardModifications.getWidth();
		int height = boardModifications.getHeight();

		DrawComputer modificationComputer = new DrawComputer(logicalDevice, boardBuffer,
				boardModifications);

		List<IDescriptor> stepDescriptors = new ArrayList<>();
		stepDescriptors.add(configBuffer.getBuffer());
		stepDescriptors.add(boardBuffer);
		stepDescriptors.add(movedBuffer);

		Computer stepVertPair = new Computer(logicalDevice, SHADER_VERT_PAIR, (int) width,
				(int) (height / 2f), stepDescriptors);
		Computer stepVertImpair = new Computer(logicalDevice, SHADER_VERT_IMPAIR, (int) width,
				(int) (height / 2f), stepDescriptors);

		PixelComputer pixelComputer = new PixelComputer(logicalDevice, configBuffer, boardBuffer,
				boardImage);

		ComputeProcess process = new BoardComputeProcess(logicalDevice, movedCleanBuffer,
				movedBuffer);
		process.addNewPipeline(stepVertPair);
		process.addNewPipeline(stepVertImpair);
		process.addNewPipeline(pixelComputer);

		boardProcesses = new ComputeProcessPool(logicalDevice, commandPool);
		boardProcesses.addProcess(process);

		ComputeProcess modificationProcess = new BoardComputeProcess(logicalDevice, movedCleanBuffer,
				movedBuffer);
		modificationProcess.addNewPipeline(modificationComputer);
		modificationProcess.addNewPipeline(stepVertPair);
		modificationProcess.addNewPipeline(stepVertImpair);
		modificationProcess.addNewPipeline(pixelComputer);

		mergeBoardProcesses = new ComputeProcessPool(logicalDevice, commandPool);
		mergeBoardProcesses.addProcess(modificationProcess);
	}

	@Override
	public void load(MemoryStack stack, long surface, int width, int height)
	{
		{
			SingleTimeCommand stc = new SingleTimeCommand(commandPool,
					logicalDevice.getQueueManager().getGraphicQueue())
			{
				@Override
				protected void doExecute(MemoryStack stack, VkCommandBuffer commandBuffer)
				{
					boardImage.getImage().transitionImageLayout(commandBuffer,
							VK_IMAGE_LAYOUT_UNDEFINED, VK_IMAGE_LAYOUT_GENERAL, 1,
							VK_PIPELINE_STAGE_TRANSFER_BIT, VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT, 0,
							VK_ACCESS_SHADER_WRITE_BIT);
				}
			};
			stc.execute();
		}

		// Fill the configurationBuffer
		{
			configBuffer.load(width, height);
		}

		// Fill the board buffer with void (0)
		{
			ByteBuffer bBuffer = MemoryUtil.memCalloc((int) boardBuffer.getSize());
			boardBuffer.fillWithBuffer(bBuffer);
			MemoryUtil.memFree(bBuffer);
		}

		// Fill the moved Clean Buffer
		{
			Buffer stagingMovedCleanBuffer = Buffer.alloc(logicalDevice, movedBuffer.getSize(),
					VK_BUFFER_USAGE_TRANSFER_SRC_BIT | VK_BUFFER_USAGE_TRANSFER_DST_BIT,
					VK_MEMORY_PROPERTY_HOST_COHERENT_BIT | VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT);

			ByteBuffer bBuffer = MemoryUtil.memCalloc((int) movedBuffer.getSize());
			stagingMovedCleanBuffer.fillWithBuffer(bBuffer);

			SingleTimeCommand stc = new SingleTimeCommand(commandPool,
					logicalDevice.getQueueManager().getGraphicQueue())
			{
				@Override
				protected void doExecute(MemoryStack stack, VkCommandBuffer commandBuffer)
				{
					Buffer.copyBuffer(vkCommandBuffer, stagingMovedCleanBuffer.getId(),
							movedCleanBuffer.getId(), (int) movedCleanBuffer.getSize());
				}
			};
			stc.execute();

			MemoryUtil.memFree(bBuffer);
			stagingMovedCleanBuffer.free();
		}

		mergeBoardProcesses.allocateNode(stack);
		boardProcesses.allocateNode(stack);
	}

	@Override
	public void execute()
	{
		if (boardModifications.isDirty())
		{
			boardModifications.updateVkBuffer();

			mergeBoardProcesses.exectue(logicalDevice.getQueueManager().getComputeQueue(), 0);
		}
		else
		{
			boardProcesses.exectue(logicalDevice.getQueueManager().getComputeQueue(), 0);
		}
	}

	@Override
	public void resize(long surface, int width, int height)
	{}

	@Override
	public void free()
	{
		mergeBoardProcesses.freeNode();
		boardProcesses.freeNode();

		movedBuffer.free();
		movedCleanBuffer.free();
		boardBuffer.free();
		configBuffer.free();

		commandPool.free();
	}

	@Override
	public CommandPool getCommandPool()
	{
		return commandPool;
	}
}
