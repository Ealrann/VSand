package org.sheepy.vulkan.sand.compute;

import java.util.Arrays;

import org.sheepy.vulkan.buffer.Buffer;
import org.sheepy.vulkan.pipeline.Context;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;
import org.sheepy.vulkan.util.ModuleResource;

public class PixelComputePipeline extends ComputePipeline
{
	private static final String SHADER_LOCATION = "org/sheepy/vulkan/sand/board_to_pixel.comp.spv";

	public PixelComputePipeline(Context context, ConfigurationBuffer configuration, Buffer board,
			BoardImage image)
	{
		super(context, image.getWidth(), image.getHeight(), 1,
				Arrays.asList(configuration.getBuffer(), board, image),
				new ModuleResource(PixelComputePipeline.class.getModule(), SHADER_LOCATION));
	}
}
