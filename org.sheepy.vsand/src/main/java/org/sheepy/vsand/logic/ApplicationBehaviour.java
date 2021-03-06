package org.sheepy.vsand.logic;

import org.eclipse.emf.common.notify.Notification;
import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.adapter.NotifyChanged;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.IAdapter;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandPackage;

@ModelExtender(scope = VSandApplication.class)
@Adapter(singleton = true)
@AutoLoad
public final class ApplicationBehaviour implements IAdapter
{
	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__NEXT_MODE)
	private static void nextModeChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var upateTask = application.getBoardUpdateTask();

		if (notification.getNewBooleanValue() == true)
		{
			upateTask.setEnabled(true);
			upateTask.setRepeatCount(1);
		}
	}

	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__PAUSED)
	private static void pausedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var upateTask = application.getBoardUpdateTask();

		upateTask.setEnabled(!notification.getNewBooleanValue());
	}

	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__SPEED)
	private static void speedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var upateTask = application.getBoardUpdateTask();

		upateTask.setRepeatCount(notification.getNewIntValue());
	}
}
