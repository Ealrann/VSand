package org.sheepy.vsand.draw;

import org.joml.Vector2ic;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.game.api.audio.AudioConfiguration;
import org.sheepy.lily.game.api.audio.IAudioAllocation;
import org.sheepy.lily.game.api.audio.IAudioHandle;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;
import org.sheepy.vsand.util.BoardUtil;
import org.sheepy.vsand.util.EShapeSize;

public final class DrawManager
{
	private static final AudioConfiguration audioConfig = new AudioConfiguration.Builder().gain(0.85f).build();

	private final VSandApplication application;
	private final IInputManager inputManager;
	private boolean wasUsed = false;
	private Vector2ic previousCursor;
	private IAudioHandle audioHandle;

	public DrawManager(VSandApplication application, IInputManager inputManager)
	{
		this.application = application;
		this.inputManager = inputManager;
	}

	public void manage(Material material, boolean needDraw)
	{
		if (needDraw)
		{
			final var firstDraw = wasUsed == false;
			newcreateDrawCommand(material, firstDraw);

			if (firstDraw)
			{
				final var paintSound = material.getPaintSound();
				if (paintSound != null)
				{
					final var pitch = material.getPitch();
					final var soundAdapter = paintSound.allocationHandle(IAudioAllocation.class).get();
					audioHandle = soundAdapter.play(audioConfig.builder().pitch(pitch).build());
				}
			}
			wasUsed = true;
		}
		else
		{
			wasUsed = false;
			if (audioHandle != null)
			{
				audioHandle.end();
				audioHandle = null;
			}
		}
	}

	private void newcreateDrawCommand(Material material, boolean firstDraw)
	{
		final DrawCommand newCommand;

		final var size = EShapeSize.values()[application.getBrushSize() - 1];
		final var cursor = inputManager.getCursorPosition();
		final var cursorBoard = BoardUtil.toBoardPosition(cursor, application);

		if (firstDraw || (previousCursor.x() == cursorBoard.x() && previousCursor.y() == cursorBoard.y()))
		{
			final var command = VSandFactory.eINSTANCE.createDrawCircle();
			command.setMaterial(material);
			command.setSize(size.getSize());
			command.setX(cursorBoard.x());
			command.setY(cursorBoard.y());

			newCommand = command;
		}
		else
		{
			final var command = VSandFactory.eINSTANCE.createDrawLine();
			command.setMaterial(material);
			command.setSize(size.getSize());
			command.setX1(previousCursor.x());
			command.setY1(previousCursor.y());
			command.setX2(cursorBoard.x());
			command.setY2(cursorBoard.y());

			newCommand = command;
		}

		previousCursor = cursorBoard;
		application.getDrawQueue().add(newCommand);
	}
}