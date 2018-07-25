package org.sheepy.vulkan.sand.compute;

import java.util.Arrays;

import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;

public class PixelComputePipeline extends ComputePipeline
{
	private static final String SHADER_LOCATION = "org/sheepy/vulkan/sand/board_to_pixel.comp.spv";

	public PixelComputePipeline(LogicalDevice logicalDevice, ConfigurationBuffer configuration, Buffer board, BoardImage image)
	{
		super(logicalDevice, image.getWidth(), image.getHeight(), 1,
				Arrays.asList(configuration.getBuffer(), board, image), SHADER_LOCATION);
	}
}
