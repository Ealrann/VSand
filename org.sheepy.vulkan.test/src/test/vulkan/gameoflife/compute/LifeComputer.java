package test.vulkan.gameoflife.compute;

import java.util.Arrays;

import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.Computer;

public class LifeComputer extends Computer
{
	private static final String SHADER_LOCATION = "test/vulkan/gameoflife/life.comp.spv";

	public LifeComputer(LogicalDevice logicalDevice, BoardBuffer sourceBuffer,
			BoardBuffer targetBuffer)
	{
		super(logicalDevice, SHADER_LOCATION, sourceBuffer.getWidth(), sourceBuffer.getHeight(),
				Arrays.asList(sourceBuffer, targetBuffer));
	}
}
