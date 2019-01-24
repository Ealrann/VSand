package org.sheepy.vulkan.sand.adapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.sheepy.lily.core.api.adapter.IServiceAdapterFactory;
import org.sheepy.lily.vulkan.process.compute.execution.ComputeCommandBuffer;
import org.sheepy.lily.vulkan.process.compute.pipeline.ComputePipelineAdapter;
import org.sheepy.vulkan.sand.model.RepeatComputePipeline;
import org.sheepy.vulkan.sand.model.VSandPackage;

public class RepeatComputePipelineAdapter extends ComputePipelineAdapter
{
	private final EAttribute REPEAT_FEATURE = VSandPackage.Literals.REPEAT_COMPUTE_PIPELINE__REPEAT_COUNT;

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
	public void record(ComputeCommandBuffer commandBuffer, int bindPoint)
	{
		bindDescriptor(commandBuffer, bindPoint, 0);

		int count = ((RepeatComputePipeline) pipeline).getRepeatCount();
		for (int i = 0; i < count; i++)
		{
			recordComputers(commandBuffer, bindPoint);
		}
	}

	@Override
	public boolean isApplicable(EClass eClass)
	{
		return VSandPackage.Literals.REPEAT_COMPUTE_PIPELINE == eClass;
	}

	public static RepeatComputePipelineAdapter adapt(RepeatComputePipeline object)
	{
		return IServiceAdapterFactory.INSTANCE.adapt(object, RepeatComputePipelineAdapter.class);
	}
}