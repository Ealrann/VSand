package test.vulkan.mesh;

import java.util.Collection;

import org.sheepy.vulkan.command.CommandPool;
import org.sheepy.vulkan.concurrent.ISignalEmitter;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.swap.graphic.GraphicSwapPipeline;

public class MeshSwapPipeline extends GraphicSwapPipeline
{
	public MeshSwapPipeline(LogicalDevice logicalDevice, MeshSwapConfiguration configuration,
			CommandPool commandPool)
	{
		this(logicalDevice, configuration, commandPool, null);
	}

	public MeshSwapPipeline(LogicalDevice logicalDevice, MeshSwapConfiguration configuration,
			CommandPool commandPool, Collection<ISignalEmitter> waitForSignals)
	{
		super(configuration, waitForSignals);

		if (configuration.descriptorPool != null)
		{
			subAllocates.add(1, configuration.descriptorPool);
		}
	}
}
