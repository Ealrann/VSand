package org.sheepy.vulkan.sand.pipelinepool;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.SingleTimeCommand;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.PipelinePool;
import org.sheepy.vulkan.pipeline.compute.ComputeProcessPool;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.compute.ConfigurationBuffer;

public class BoardPipelinePool extends PipelinePool implements IAllocable
{
	private LogicalDevice logicalDevice;
	private BoardModifications boardModifications;
	private BoardImage boardImage;

	private ComputeProcessPool boardProcessesPool;

	private ConfigurationBuffer configBuffer;
	private Buffer boardBuffer;

	private boolean speedChanged = false;
	private int speed = 1;
	private SandComputeProcess process;

	public BoardPipelinePool(LogicalDevice logicalDevice, BoardModifications boardModifications,
			BoardImage boardImage)
	{
		super(logicalDevice, logicalDevice.getQueueManager().getComputeQueueIndex(), true);

		this.logicalDevice = logicalDevice;
		this.boardModifications = boardModifications;
		this.boardImage = boardImage;

		createBuffers();
		buildPipelines();
	}

	private void createBuffers()
	{
		int width = boardImage.getWidth();
		int height = boardImage.getHeight();

		// configBuffer
		{
			configBuffer = new ConfigurationBuffer(logicalDevice);
		}

		// boardBuffer
		{
			int boardSizeByte = width * height * Integer.BYTES;
			boardBuffer = new Buffer(logicalDevice, boardSizeByte,
					VK_BUFFER_USAGE_STORAGE_BUFFER_BIT
							| VK_BUFFER_USAGE_TRANSFER_SRC_BIT
							| VK_BUFFER_USAGE_TRANSFER_DST_BIT,
					VK_MEMORY_PROPERTY_HOST_COHERENT_BIT | VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT);
			boardBuffer.configureDescriptor(VK_SHADER_STAGE_COMPUTE_BIT,
					VK_DESCRIPTOR_TYPE_STORAGE_BUFFER);
		}

		subAllocationObjects.add(configBuffer);
		subAllocationObjects.add(boardBuffer);
	}

	private void buildPipelines()
	{
		process = new SandComputeProcess(logicalDevice, boardModifications,
				boardImage, boardBuffer, configBuffer);

		boardProcessesPool = new ComputeProcessPool(logicalDevice, commandPool);
		boardProcessesPool.addProcess(process);

		subAllocationObjects.add(boardProcessesPool);
	}

	@Override
	public void allocate(MemoryStack stack)
	{
		super.allocate(stack);

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

		// Fill the board buffer with void (0)
		{
			ByteBuffer bBuffer = MemoryUtil.memCalloc((int) boardBuffer.getSize());
			boardBuffer.fillWithBuffer(bBuffer);
			MemoryUtil.memFree(bBuffer);
		}
	}

	public void setSpeed(int speed)
	{
		if (this.speed != speed)
		{
			speedChanged = true;
			process.setSpeed(speed);
			this.speed = speed;
		}
	}

	@Override
	public void execute()
	{
		boolean changed = false;
		if (boardModifications.isDirty())
		{
			boardModifications.updateVkBuffer();
			if (process.drawPipeline.isEnabled() == false)
			{
				process.drawPipeline.setEnabled(true);
				changed = true;
			}
		}
		else
		{
			if (process.drawPipeline.isEnabled() == true)
			{
				process.drawPipeline.setEnabled(false);
				changed = true;
			}
		}

		if (speedChanged)
		{
			changed = true;
			speedChanged = false;
		}

		if (changed == true)
		{
			boardProcessesPool.recordCommands();
		}

		boardProcessesPool.exectue(logicalDevice.getQueueManager().getComputeQueue(), 0);
	}

	@Override
	public void free()
	{}
}
