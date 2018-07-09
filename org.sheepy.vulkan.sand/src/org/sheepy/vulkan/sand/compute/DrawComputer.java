package org.sheepy.vulkan.sand.compute;

import java.util.Arrays;

import org.sheepy.lily.game.vulkan.buffer.Buffer;
import org.sheepy.lily.game.vulkan.device.LogicalDevice;
import org.sheepy.lily.game.vulkan.pipeline.compute.Computer;
import org.sheepy.vulkan.sand.board.BoardModifications;

public class DrawComputer extends Computer
{
	private static final String SHADER_LOCATION = "org/sheepy/vulkan/sand/draw.comp.spv";

	public DrawComputer(LogicalDevice logicalDevice, Buffer targetBuffer,
			BoardModifications modifications)
	{
		super(logicalDevice, SHADER_LOCATION, modifications.getWidth(), modifications.getHeight(),
				Arrays.asList(targetBuffer, modifications));
	}
}
