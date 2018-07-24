package org.sheepy.vulkan.sand.pipelinepool;

import java.util.ArrayList;
import java.util.List;

import org.sheepy.vulkan.descriptor.DescriptorPool;
import org.sheepy.vulkan.descriptor.IDescriptor;
import org.sheepy.vulkan.device.LogicalDevice;
import org.sheepy.vulkan.pipeline.IPipelineExecutable;
import org.sheepy.vulkan.pipeline.compute.ComputePipeline;

class RepeatComputePipeline extends ComputePipeline
{
	private int repeat = 1;

	public RepeatComputePipeline(LogicalDevice logicalDevice, DescriptorPool descriptorPool,
			int width, int height, int depth, List<IDescriptor> descriptors)
	{
		super(logicalDevice, descriptorPool, width, height, depth, descriptors);
	}

	public void setRepeat(int repeat)
	{
		if (repeat != this.repeat)
		{
			this.repeat = repeat;
			dirty = true;
		}
	}

	@Override
	public List<IPipelineExecutable> getExecutables()
	{
		List<IPipelineExecutable> res = new ArrayList<>();

		for (int i = 0; i < repeat; i++)
		{
			res.addAll(super.getExecutables());
		}

		return res;
	}
}