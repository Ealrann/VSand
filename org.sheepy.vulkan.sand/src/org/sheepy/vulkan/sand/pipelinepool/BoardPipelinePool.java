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
import org.sheepy.vulkan.buffer.ImageBarrier;
import org.sheepy.vulkan.command.SingleTimeCommand;
import org.sheepy.vulkan.common.IAllocable;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.ComputeBufferBarrier;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.vulkan.pipeline.compute.ComputeProcessPool;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.compute.BoardDecisionBuffer;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.compute.ConfigurationBuffer;
import org.sheepy.vulkan.sand.compute.FrameUniformBuffer;
import org.sheepy.vulkan.sand.compute.PixelComputePipeline;
import org.sheepy.vulkan.sand.compute.TransformationBuffer;

public class BoardPipelinePool extends ComputeProcessPool implements IAllocable
{
	private static final String SHADER_STEP1 = "org/sheepy/vulkan/sand/game_step1_chooseTO.comp.spv";
	private static final String SHADER_STEP2 = "org/sheepy/vulkan/sand/game_step2_acceptFROM.comp.spv";
	private static final String SHADER_STEP3 = "org/sheepy/vulkan/sand/game_step3_swap.comp.spv";
	private static final String SHADER_STEP4 = "org/sheepy/vulkan/sand/game_step4_transformation.comp.spv";

	private static final String SHADER_DRAW = "org/sheepy/vulkan/sand/draw.comp.spv";

	private RepeatComputePipeline stepPipeline;
	private ComputePipeline drawPipeline;
	private PixelComputePipeline pixelCompute;

	private FrameUniformBuffer ubo;

	private BoardModifications boardModifications;
	private BoardImage boardImage;

	private BoardDecisionBuffer decision;
	private ConfigurationBuffer configBuffer;
	private TransformationBuffer tranformationBuffer;
	private Buffer boardBuffer;

	private ComputeProcess process;

	public BoardPipelinePool(LogicalDevice logicalDevice, BoardModifications boardModifications,
			BoardImage boardImage)
	{
		super(logicalDevice, true);

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
		configBuffer = new ConfigurationBuffer(logicalDevice, commandPool);
		tranformationBuffer = new TransformationBuffer(logicalDevice, commandPool);

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

		decision = new BoardDecisionBuffer(logicalDevice, width, height, commandPool);

		ubo = new FrameUniformBuffer(logicalDevice);

		allocationObjects.add(configBuffer);
		allocationObjects.add(tranformationBuffer);
		allocationObjects.add(ubo);
		allocationObjects.add(boardBuffer);
		allocationObjects.add(decision);
	}

	private void buildPipelines()
	{
		int width = boardImage.getWidth();
		int height = boardImage.getHeight();

		List<IDescriptor> stepDescriptors = new ArrayList<>();
		stepDescriptors.add(configBuffer.getBuffer());
		stepDescriptors.add(ubo.getBuffer());
		stepDescriptors.add(boardBuffer);
		stepDescriptors.add(decision.getBuffer());
		stepDescriptors.add(tranformationBuffer.getBuffer());

		drawPipeline = new ComputePipeline(logicalDevice, width, height, 1,
				Arrays.asList(boardBuffer, boardModifications), SHADER_DRAW);
		drawPipeline.setEnabled(false);
		drawPipeline.setWorkgroupSize(32, 16, 1);

		stepPipeline = new RepeatComputePipeline(width, height, 1, stepDescriptors);
		stepPipeline.addShader(logicalDevice.newComputeShader(SHADER_STEP1));
		stepPipeline.addShader(logicalDevice.newComputeShader(SHADER_STEP2));
		stepPipeline.addPipelineBarrier(new ComputeBufferBarrier(boardBuffer,
				VK_ACCESS_MEMORY_READ_BIT, VK_ACCESS_MEMORY_WRITE_BIT));
		stepPipeline.addShader(logicalDevice.newComputeShader(SHADER_STEP3));
		stepPipeline.addShader(logicalDevice.newComputeShader(SHADER_STEP4));
		stepPipeline.setWorkgroupSize(32, 16, 1);

		pixelCompute = new PixelComputePipeline(logicalDevice, configBuffer, boardBuffer,
				boardImage);
		pixelCompute.setWorkgroupSize(32, 16, 1);

		process = new ComputeProcess(logicalDevice);
		process.addProcessUnit(drawPipeline);
		process.addProcessUnit(stepPipeline);
		process.addProcessUnit(new ComputeBufferBarrier(boardBuffer, VK_ACCESS_MEMORY_WRITE_BIT,
				VK_ACCESS_MEMORY_READ_BIT));
		process.addProcessUnit(pixelCompute);

		addProcess(process);
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
					ImageBarrier.execute(commandBuffer, boardImage.getImage(),
							VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT,
							VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT, VK_IMAGE_LAYOUT_UNDEFINED,
							VK_IMAGE_LAYOUT_GENERAL, 0, VK_ACCESS_SHADER_WRITE_BIT);
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
		stepPipeline.setRepeat(speed);
	}

	@Override
	public void execute()
	{
		ubo.update();

		if (boardModifications.isEmpty() == false)
		{
			drawPipeline.setEnabled(true);
			boardModifications.updateVkBuffer();
		}
		else
		{
			drawPipeline.setEnabled(false);
		}

		if (process.isDirty())
		{
			vkQueueWaitIdle(logicalDevice.getQueueManager().getComputeQueue());
			recordCommands();
		}

		super.execute();
	}
}
