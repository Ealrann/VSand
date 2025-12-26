package org.sheepy.vsand.logic;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.logoce.lmf.core.api.notification.Notification;
import org.sheepy.lily.core.api.adapter.NotifyChanged;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.IAdapter;
import org.logoce.lmf.core.api.extender.ModelExtender;
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
		final var upateTask = application.boardUpdateTask();

		if (notification.booleanValue() == true)
		{
			upateTask.enabled(true);
			upateTask.repeatCount(1);
		}
	}

	@NotifyChanged(featureIds = VSandApplication.FeatureIDs.PAUSED)
	private static void pausedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.notifier();
		final var upateTask = application.boardUpdateTask();

		upateTask.enabled(!notification.booleanValue());
	}

	@NotifyChanged(featureIds = VSandApplication.FeatureIDs.SPEED)
	private static void speedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.notifier();
		final var upateTask = application.boardUpdateTask();

		upateTask.repeatCount(notification.intValue());
	}
}
