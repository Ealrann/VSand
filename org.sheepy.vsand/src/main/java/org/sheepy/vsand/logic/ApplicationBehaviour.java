package org.sheepy.vsand.logic;

import org.eclipse.emf.common.notify.Notification;
import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.adapter.annotation.NotifyChanged;
import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandPackage;

@ModelExtender(scope = VSandApplication.class)
@Adapter(singleton = true, lazy = false)
public final class ApplicationBehaviour implements IExtender
{
	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__NEXT_MODE)
	private static void nextModeChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final var nextModeEnabled = application.isNextMode();

		if (nextModeEnabled)
		{
			pauseApplication(application, false);
		}
	}

	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__PAUSED)
	private static void pausedChanged(Notification notification)
	{
		final var application = (VSandApplication) notification.getNotifier();
		final boolean pause = notification.getNewBooleanValue();

		pauseApplication(application, pause);
	}
//
//	@NotifyChanged(featureIds = VSandPackage.VSAND_APPLICATION__SPEED)
//	private static void speedChanged(Notification notification)
//	{
//		final var application = (VSandApplication) notification.getNotifier();
//		final var upateTask = application.getBoardUpdateTask();
//
//		upateTask.setRepeatCount(notification.getNewIntValue());
//	}

	private static void pauseApplication(final VSandApplication application, final boolean pause)
	{
		final var stepPipelines = application.getStepPipelines();
		final var pausePipeline = application.getPausePipeline();
		pausePipeline.setEnabled(pause);
		stepPipelines.forEach(p -> p.setEnabled(!pause));
	}
}
