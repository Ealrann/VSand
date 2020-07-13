package org.sheepy.vsand.input;

import org.sheepy.lily.core.api.adapter.annotation.Adapter;
import org.sheepy.lily.core.api.extender.ModelExtender;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.input.event.MouseClickEvent;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

@ModelExtender(scope = VSandApplication.class)
@Adapter(lazy = false)
public final class PrimaryDrawer extends AbstractDrawer
{
	public PrimaryDrawer(final VSandApplication application)
	{
		super(application);

		final var inputManager = IInputManager.get(application);
		inputManager.listen(this::onMouseClickEvent, IInputManager.Features.MouseClickEvent);
	}

	@Override
	protected Material getMaterial(VSandApplication application)
	{
		return application.getMainMaterial();
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
