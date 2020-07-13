package org.sheepy.vsand.input;

import org.sheepy.lily.core.api.extender.IExtender;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.vsand.audio.MaterialSoundManager;
import org.sheepy.vsand.draw.DrawManager;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.BoardUtil;

abstract class AbstractDrawer implements IExtender
{
	private final MaterialSoundManager soundManager = new MaterialSoundManager();
	private final DrawManager drawManager = new DrawManager();
	private final VSandApplication application;
	private final IInputManager inputManager;

	private boolean isDrawing = false;
	private boolean drawRequested = false;

	public AbstractDrawer(VSandApplication application)
	{
		this.application = application;
		inputManager = IInputManager.get(application);
		inputManager.listen(this::afterPollInputs, IInputManager.Features.AfterPollInputs);
	}

	private void afterPollInputs()
	{
		if (drawRequested)
		{
			final var material = getMaterial(application);
			final boolean materialHasChanged = drawManager.getMaterial() != material;
			if (isDrawing && materialHasChanged)
			{
				soundManager.stop();
				isDrawing = false;
			}

			if (!isDrawing)
			{
				soundManager.start(material);
				drawManager.start(material);
				isDrawing = true;
			}

			newDrawCommand();
		}
		else if (isDrawing)
		{
			soundManager.stop();
			isDrawing = false;
		}
	}

	private void newDrawCommand()
	{
		final var cursor = inputManager.getCursorPosition();
		final var cursorBoard = BoardUtil.toBoardPosition(cursor, application);
		final var brushSize = application.getBrushSize();
		final var drawCommand = drawManager.draw(brushSize, cursorBoard);
		application.getDrawQueue().add(drawCommand);
	}

	protected void setDrawRequested(boolean drawRequested)
	{
		this.drawRequested = drawRequested;
	}

	protected abstract Material getMaterial(VSandApplication application);

}
