package test.vulkan.gameoflife.compute;

import java.util.Arrays;

import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.Computer;

public class PixelComputer extends Computer
{
	private static final String SHADER_LOCATION = "test/vulkan/gameoflife/life2pixel.comp.spv";

	public PixelComputer(LogicalDevice logicalDevice, BoardBuffer board, BoardImage image)
	{
		super(logicalDevice, SHADER_LOCATION, board.getWidth(), board.getHeight(),
				Arrays.asList(board, image));
	}
}
