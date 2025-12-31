package org.sheepy.vsand.logic;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.notification.Notification;
import org.sheepy.lily.core.api.adapter.NotifyChanged;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.vulkan.model.process.CompositePipeline;
import org.sheepy.vsand.model.vsand.VSandApplication;

@ModelExtender(scope = VSandApplication.class)
@Adapter(singleton = true)
@AutoLoad
public final class ApplicationBehaviour implements IAdapter
{
	@NotifyChanged(featureIds = VSandApplication.FeatureIDs.NEXT_MODE)
	private static void nextModeChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.notifier();
		final var simulationPipeline = findSimulationPipeline(application);

		if (notification.booleanValue() == true)
		{
			if (simulationPipeline != null)
			{
				simulationPipeline.record(true);
				simulationPipeline.repeat(1);
			}
		}
	}

	@NotifyChanged(featureIds = VSandApplication.FeatureIDs.PAUSED)
	private static void pausedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.notifier();
		final var simulationPipeline = findSimulationPipeline(application);

		if (simulationPipeline != null)
		{
			if (notification.booleanValue() == true)
			{
				simulationPipeline.record(false);
			}
			else
			{
				simulationPipeline.repeat(application.speed());
				simulationPipeline.record(true);
			}
		}
	}

	@NotifyChanged(featureIds = VSandApplication.FeatureIDs.SPEED)
	private static void speedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.notifier();
		final var simulationPipeline = findSimulationPipeline(application);

		if (simulationPipeline != null)
		{
			simulationPipeline.repeat(notification.intValue());
		}
	}

	private static CompositePipeline findSimulationPipeline(final VSandApplication application)
	{
		return application.streamTree()
						  .filter(CompositePipeline.class::isInstance)
						  .map(CompositePipeline.class::cast)
						  .filter(pipeline -> "Simulation".equals(pipeline.name()))
						  .findFirst()
						  .orElse(null);
	}
}
