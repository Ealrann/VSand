package org.sheepy.vulkan.sand.pipelinepool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.command.compute.ComputeCommandBuffer;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.vulkan.pipeline.compute.ComputeProcess;
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

	public SandComputeProcess(LogicalDevice logicalDevice, BoardModifications boardModifications,
			BoardImage boardImage, Buffer boardBuffer, ConfigurationBuffer configBuffer)
	{
		super(logicalDevice);

		int width = boardImage.getWidth();
		int height = boardImage.getHeight();

		List<IDescriptor> stepDescriptors = new ArrayList<>();
		stepDescriptors.add(configBuffer.getBuffer());
		stepDescriptors.add(boardBuffer);

		drawPipeline = new ComputePipeline(logicalDevice, descriptorPool, width, height, 1,
				Arrays.asList(boardBuffer, boardModifications), SHADER_DRAW);
		stepPipelineVert = new ComputePipeline(logicalDevice, descriptorPool, width, 1, 1,
				stepDescriptors, SHADER_VERT);
		stepPipelineHori = new ComputePipeline(logicalDevice, descriptorPool, 1, height, 1,
				stepDescriptors, SHADER_HORI);

		stepPipelineVert.setWorkgroupSize(32);
		stepPipelineHori.setWorkgroupSize(32);

		pixelCompute = new PixelComputePipeline(logicalDevice, descriptorPool, configBuffer,
				boardBuffer, boardImage);

		addPipeline(drawPipeline);
		addPipeline(stepPipelineHori);
		addPipeline(stepPipelineVert);
		addPipeline(pixelCompute);
	}

	public void setSpeed(int speed)
	{
		computePipelines.clear();

		computePipelines.add(drawPipeline);
		for (int i = 0; i < speed; i++)
		{
			computePipelines.add(stepPipelineHori);
			computePipelines.add(stepPipelineVert);
		}
		computePipelines.add(pixelCompute);
	}

	@Override
	public void recordCommand(ComputeCommandBuffer commandBuffer)
	{
		super.recordCommand(commandBuffer);
	}
}
