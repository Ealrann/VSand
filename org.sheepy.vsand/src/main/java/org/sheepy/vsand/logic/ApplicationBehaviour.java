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
	@NotifyChanged
	public static void notifyChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var upateTask = application.getBoardUpdateTask();
		final var feature = notification.getFeature();

		if (feature == VSandPackage.Literals.VSAND_APPLICATION__NEXT_MODE
				&& notification.getNewBooleanValue() == true)
		{
			upateTask.setEnabled(true);
			upateTask.setRepeatCount(1);
		}
		else if(feature == VSandPackage.Literals.VSAND_APPLICATION__PAUSED)
		{
			upateTask.setEnabled(!notification.getNewBooleanValue());
		}
		else if (feature == VSandPackage.Literals.VSAND_APPLICATION__SPEED)
		{
			upateTask.setRepeatCount(notification.getNewIntValue());
		}
	}
}
