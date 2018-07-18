package org.sheepy.vulkan.sand.pipelinepool;

import static org.lwjgl.vulkan.VK10.VK_ACCESS_MEMORY_READ_BIT;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_MEMORY_WRITE_BIT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.command.compute.ComputeCommandBuffer;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.vulkan.pipeline.compute.ComputeProcess;
import org.sheepy.vulkan.pipeline.compute.PipelineBarrier;
import org.sheepy.vulkan.sand.board.BoardModifications;
import org.sheepy.vulkan.sand.compute.BoardImage;
import org.sheepy.vulkan.sand.compute.ConfigurationBuffer;
import org.sheepy.vulkan.sand.compute.PixelComputePipeline;

public class SandComputeProcess extends ComputeProcess
{
	private static final String SHADER_VERT = "org/sheepy/vulkan/sand/game_step_vert.comp.spv";
	private static final String SHADER_HORI = "org/sheepy/vulkan/sand/game_step_hori.comp.spv";
	private static final String SHADER_DRAW = "org/sheepy/vulkan/sand/draw.comp.spv";

	private ComputePipeline stepPipelineVert;
	private ComputePipeline stepPipelineHori;

	public final ComputePipeline drawPipeline;
	private PixelComputePipeline pixelCompute;

	public SandComputeProcess(LogicalDevice logicalDevice, CommandPool commandPool,
			BoardModifications boardModifications, BoardImage boardImage, Buffer boardBuffer,
			ConfigurationBuffer configBuffer)
	{
		super(logicalDevice);

		int width = boardImage.getWidth();
		int height = boardImage.getHeight();

		List<IDescriptor> stepDescriptors = new ArrayList<>();
		stepDescriptors.add(configBuffer.getBuffer());
		stepDescriptors.add(boardBuffer);

		drawPipeline = new ComputePipeline(logicalDevice, descriptorPool, width, height, 1,
				Arrays.asList(boardBuffer, boardModifications), SHADER_DRAW);
		drawPipeline.setEnabled(false);
		stepPipelineVert = new ComputePipeline(logicalDevice, descriptorPool, width, 1, 1,
				stepDescriptors, SHADER_VERT);
		stepPipelineVert.setWorkgroupSize(128);

		stepPipelineHori = new ComputePipeline(logicalDevice, descriptorPool, 1, height, 1,
				stepDescriptors, SHADER_HORI);
		stepPipelineHori.setWorkgroupSize(128);

		pixelCompute = new PixelComputePipeline(logicalDevice, descriptorPool, configBuffer,
				boardBuffer, boardImage);

		addProcessUnit(new PipelineBarrier(boardBuffer, VK_ACCESS_MEMORY_READ_BIT,
				VK_ACCESS_MEMORY_WRITE_BIT));
		addProcessUnit(drawPipeline);
		addProcessUnit(stepPipelineVert);
		addProcessUnit(stepPipelineHori);
		addProcessUnit(new PipelineBarrier(boardBuffer, VK_ACCESS_MEMORY_WRITE_BIT,
				VK_ACCESS_MEMORY_READ_BIT));
		addProcessUnit(pixelCompute);
	}

	private int speed = 1;
	private boolean dirty = false;

	public void setSpeed(int speed)
	{
		if (this.speed != speed)
		{
			this.speed = speed;
			dirty = true;
			computePipelines.clear();

			computePipelines.add(drawPipeline);
			for (int i = 0; i < speed; i++)
			{
				computePipelines.add(stepPipelineVert);
				computePipelines.add(stepPipelineHori);
			}
			computePipelines.add(pixelCompute);
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
}
