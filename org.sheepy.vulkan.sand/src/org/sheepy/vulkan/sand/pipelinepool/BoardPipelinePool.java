package org.sheepy.vulkan.sand.pipelinepool;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.SingleTimeCommand;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.PipelinePool;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.vulkan.pipeline.compute.ComputeProcessPool;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.compute.ConfigurationBuffer;
import org.sheepy.vulkan.sand.compute.PixelComputePipeline;

public class BoardPipelinePool extends PipelinePool implements IAllocable
{
	private static final String SHADER_VERT = "org/sheepy/vulkan/sand/game_step_vert.comp.spv";
	private static final String SHADER_HORI = "org/sheepy/vulkan/sand/game_step_hori.comp.spv";

	private static final String SHADER_DRAW = "org/sheepy/vulkan/sand/draw.comp.spv";

	private LogicalDevice logicalDevice;
	private BoardModifications boardModifications;
	private BoardImage boardImage;

	private ComputeProcessPool boardProcesses;

	private ConfigurationBuffer configBuffer;
	private Buffer boardBuffer;
	private ComputePipeline drawPipeline;
	private ComputePipeline stepPipelineVert;
	private ComputePipeline stepPipelineHori;

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
		int width = boardImage.getWidth();
		int height = boardImage.getHeight();

		List<IDescriptor> stepDescriptors = new ArrayList<>();
		stepDescriptors.add(configBuffer.getBuffer());
		stepDescriptors.add(boardBuffer);

		ComputeProcess process = new ComputeProcess(logicalDevice);
		DescriptorPool descriptorPool = process.getDescriptorPool();

		drawPipeline = new ComputePipeline(logicalDevice, descriptorPool, width, height, 1,
				Arrays.asList(boardBuffer, boardModifications), SHADER_DRAW);
		stepPipelineVert = new ComputePipeline(logicalDevice, descriptorPool, width, 1, 1, stepDescriptors, SHADER_VERT);
		stepPipelineHori = new ComputePipeline(logicalDevice, descriptorPool, 1, height, 1, stepDescriptors, SHADER_HORI);
		
		
		PixelComputePipeline pixelCompute = new PixelComputePipeline(logicalDevice, descriptorPool,
				configBuffer, boardBuffer, boardImage);

		process.addPipeline(drawPipeline);
		process.addPipeline(stepPipelineHori);
		process.addPipeline(stepPipelineVert);
		process.addPipeline(pixelCompute);

		boardProcesses = new ComputeProcessPool(logicalDevice, commandPool);
		boardProcesses.addProcess(process);

		subAllocationObjects.add(boardProcesses);
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

	@Override
	public void execute()
	{
		//TODO changed avec la speed
		boolean changed = false;
		if (boardModifications.isDirty())
		{
			boardModifications.updateVkBuffer();
			if (drawPipeline.isEnabled() == false)
			{
				drawPipeline.setEnabled(true);
				changed = true;
			}
		}
		else
		{
			if (drawPipeline.isEnabled() == true)
			{
				drawPipeline.setEnabled(false);
				changed = true;
			}
		}
		
		if(changed == true)
		{
			boardProcesses.recordCommands();
		}
		
		boardProcesses.exectue(logicalDevice.getQueueManager().getComputeQueue(), 0);
	}

	@Override
	public void free()
	{}
}
