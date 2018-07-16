package test.vulkan.gameoflife.compute;

import java.util.Arrays;

import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;

public class LifeCompute extends ComputePipeline
{
	private static final String SHADER_LOCATION = "test/vulkan/gameoflife/life.comp.spv";

	public LifeCompute(LogicalDevice logicalDevice, DescriptorPool descriptorPool, BoardBuffer sourceBuffer,
			BoardBuffer targetBuffer)
	{
		super(logicalDevice, descriptorPool, sourceBuffer.getWidth(), sourceBuffer.getHeight(), 1,
				Arrays.asList(sourceBuffer, targetBuffer), SHADER_LOCATION);
	}
}
