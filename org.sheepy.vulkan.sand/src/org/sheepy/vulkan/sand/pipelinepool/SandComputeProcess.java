package org.sheepy.vulkan.sand.pipelinepool;

import static org.lwjgl.vulkan.VK10.VK_ACCESS_MEMORY_READ_BIT;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_MEMORY_WRITE_BIT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.command.compute.ComputeCommandBuffer;
import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.vulkan.pipeline.compute.PipelineBarrier;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.compute.BoardDecisionBuffer;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.compute.ConfigurationBuffer;
import org.sheepy.vulkan.sand.compute.PixelComputePipeline;

public class SandComputeProcess extends ComputeProcess
{
	private static final String SHADER_STEP1 = "org/sheepy/vulkan/sand/game_step1_chooseTO.comp.spv";
	private static final String SHADER_STEP2 = "org/sheepy/vulkan/sand/game_step2_acceptFROM.comp.spv";
	private static final String SHADER_STEP3 = "org/sheepy/vulkan/sand/game_step3_swap.comp.spv";

	private static final String SHADER_DRAW = "org/sheepy/vulkan/sand/draw.comp.spv";

	private RepeatComputePipeline stepPipeline;
	public final ComputePipeline drawPipeline;
	private PixelComputePipeline pixelCompute;

	private int speed = 1;
	private boolean dirty = false;

	public SandComputeProcess(LogicalDevice logicalDevice, CommandPool commandPool,
			BoardModifications boardModifications, BoardDecisionBuffer decision,
			BoardImage boardImage, Buffer boardBuffer, ConfigurationBuffer configBuffer)
	{
		super(logicalDevice);

		int width = boardImage.getWidth();
		int height = boardImage.getHeight();

		List<IDescriptor> stepDescriptors = new ArrayList<>();
		stepDescriptors.add(configBuffer.getBuffer());
		stepDescriptors.add(boardBuffer);
		stepDescriptors.add(decision.getBuffer());

		drawPipeline = new ComputePipeline(logicalDevice, descriptorPool, width, height, 1,
				Arrays.asList(boardBuffer, boardModifications), SHADER_DRAW);
		drawPipeline.setEnabled(false);

		stepPipeline = new RepeatComputePipeline(logicalDevice, descriptorPool, width, height, 1,
				stepDescriptors);
		stepPipeline.addPipelineBarrier(new PipelineBarrier(decision.getBuffer(),
				VK_ACCESS_MEMORY_READ_BIT, VK_ACCESS_MEMORY_WRITE_BIT));
		stepPipeline.addShader(SHADER_STEP1);
		stepPipeline.addShader(SHADER_STEP2);
		stepPipeline.addPipelineBarrier(new PipelineBarrier(decision.getBuffer(),
				VK_ACCESS_MEMORY_WRITE_BIT, VK_ACCESS_MEMORY_READ_BIT));
		stepPipeline.addShader(SHADER_STEP3);

		pixelCompute = new PixelComputePipeline(logicalDevice, descriptorPool, configBuffer,
				boardBuffer, boardImage);

		addProcessUnit(new PipelineBarrier(boardBuffer, VK_ACCESS_MEMORY_READ_BIT,
				VK_ACCESS_MEMORY_WRITE_BIT));
		addProcessUnit(drawPipeline);
		addProcessUnit(stepPipeline);
		addProcessUnit(new PipelineBarrier(boardBuffer, VK_ACCESS_MEMORY_WRITE_BIT,
				VK_ACCESS_MEMORY_READ_BIT));
		addProcessUnit(pixelCompute);
	}

	public void setSpeed(int speed)
	{
		if (this.speed != speed)
		{
			this.speed = speed;
			dirty = true;
			stepPipeline.repeat = speed;
		}
	}

	public boolean isDirty()
	{
		return dirty;
	}

	@Override
	public void recordCommand(ComputeCommandBuffer commandBuffer)
	{
		super.recordCommand(commandBuffer);
		dirty = false;
	}

	public void setNeedDraw(boolean value)
	{
		if (drawPipeline.isEnabled() != value)
		{
			drawPipeline.setEnabled(value);
			dirty = true;
		}
	}

	class RepeatComputePipeline extends ComputePipeline
	{
		int repeat = 1;

		public RepeatComputePipeline(LogicalDevice logicalDevice, DescriptorPool descriptorPool,
				int width, int height, int depth, List<IDescriptor> descriptors)
		{
			super(logicalDevice, descriptorPool, width, height, depth, descriptors);
		}

		@Override
		public List<IComputePipelineExecutableUnit> getExecutablesUnit()
		{
			List<IComputePipelineExecutableUnit> res = new ArrayList<>();

			for (int i = 0; i < repeat; i++)
			{
				res.addAll(super.getExecutablesUnit());
			}

			return res;
		}
	}
}
