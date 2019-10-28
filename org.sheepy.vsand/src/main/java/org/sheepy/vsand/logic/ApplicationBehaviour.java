package org.sheepy.vsand.logic;

import org.eclipse.emf.common.notify.Notification;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.NotifyChanged;
import org.sheepy.lily.vulkan.api.adapter.IVulkanAdapter;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandPackage;

@Adapter(scope = VSandApplication.class)
public class ApplicationBehaviour implements IVulkanAdapter
{
	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__NEXT_MODE)
	public static void nextModeChanged(Notification notification)
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
	public static void pausedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var upateTask = application.getBoardUpdateTask();

		upateTask.setEnabled(!notification.getNewBooleanValue());
	}

	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__SPEED)
	public static void speedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var upateTask = application.getBoardUpdateTask();

		upateTask.setRepeatCount(notification.getNewIntValue());
	}
}
