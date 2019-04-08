package org.sheepy.vsand.adapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.sheepy.lily.core.api.adapter.IAdapterFactoryService;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.Statefull;
import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.lily.vulkan.process.compute.pipeline.ComputePipelineAdapter;
import org.sheepy.vsand.model.RepeatComputePipeline;
import org.sheepy.vsand.model.VSandPackage;

@Statefull
@Adapter(scope = RepeatComputePipeline.class)
public class RepeatComputePipelineAdapter extends ComputePipelineAdapter
{
	private static final EAttribute REPEAT_FEATURE = VSandPackage.Literals.REPEAT_COMPUTE_PIPELINE__REPEAT_COUNT;

	public RepeatComputePipelineAdapter(ComputePipeline pipeline)
	{
		super(pipeline);
	}

	@Override
	public void notifyChanged(Notification notification)
	{
		if (notification.getFeature() == REPEAT_FEATURE
				&& notification.getOldIntValue() != notification.getNewIntValue())
		{
			recordNeeded = true;
		}

		super.notifyChanged(notification);
	}

	@Override
	public void record(VkCommandBuffer commandBuffer, int bindPoint, int index)
	{
		bindDescriptor(commandBuffer, bindPoint, new Integer[] {
				0
		});

		int count = ((RepeatComputePipeline) pipeline).getRepeatCount();
		for (int i = 0; i < count; i++)
		{
			recordComputers(commandBuffer, bindPoint);
		}
	}

	public static RepeatComputePipelineAdapter adapt(RepeatComputePipeline object)
	{
		return IAdapterFactoryService.INSTANCE.adapt(object, RepeatComputePipelineAdapter.class);
	}
}