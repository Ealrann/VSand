package org.sheepy.vsand.input;

import org.logoce.lmf.core.api.adapter.Adapter;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.lmf.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.input.event.MouseClickEvent;
import org.logoce.lmf.core.api.notification.observatory.IObservatoryBuilder;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandApplication;

@ModelExtender(scope = VSandApplication.class)
@Adapter
@AutoLoad
public final class PrimaryDrawer extends AbstractDrawer
{
	private PrimaryDrawer(final VSandApplication application, final IObservatoryBuilder observatory)
	{
		super(application);

		observatory.focus(IInputManager.get(application))
				   .listen(this::onMouseClickEvent, IInputManager.Features.MouseClickEvent);
	}

	@Override
	protected Material getMaterial(VSandApplication application)
	{
		return application.mainMaterial();
	}

	private void onMouseClickEvent(MouseClickEvent event)
	{
		switch (event.button)
		{
			case LEFT:
				setDrawRequested(event.pressed);
				break;
			case RIGHT:
				setDrawRequested(false);
				break;
			default:
		}
	}
}
