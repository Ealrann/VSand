package org.sheepy.vulkan.sand.compute;

import java.util.Arrays;

import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.Computer;

public class PixelComputer extends Computer
{
	private static final String SHADER_LOCATION = "org/sheepy/vulkan/sand/board_to_pixel.comp.spv";

	public PixelComputer(LogicalDevice logicalDevice, ConfigurationBuffer configuration,
			Buffer board, BoardImage image)
	{
		super(logicalDevice, SHADER_LOCATION, image.getWidth(), image.getHeight(),
				Arrays.asList(configuration.getBuffer(), board, image));
	}
}
