package org.sheepy.vsand.input;

import org.logoce.adapter.api.Adapter;
import org.sheepy.lily.core.api.cadence.AutoLoad;
import org.logoce.extender.api.ModelExtender;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.input.event.MouseClickEvent;
import org.sheepy.lily.core.api.notification.observatory.IObservatoryBuilder;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = VSandApplication.class)
@Adapter
@AutoLoad
public final class SecondaryDrawer extends AbstractDrawer
{
	private SecondaryDrawer(final VSandApplication application, final IObservatoryBuilder observatory)
	{
		super(application);

		observatory.focus(IInputManager.get(application))
				   .listen(this::onMouseClickEvent, IInputManager.Features.MouseClickEvent);
	}

	@Override
	protected Material getMaterial(VSandApplication application)
	{
		return application.getSecondaryMaterial();
	}

	private void onMouseClickEvent(MouseClickEvent event)
	{
		switch (event.button)
		{
			case LEFT:
				setDrawRequested(false);
				break;
			case RIGHT:
				setDrawRequested(event.pressed);
				break;
			default:
		}
	}
}
