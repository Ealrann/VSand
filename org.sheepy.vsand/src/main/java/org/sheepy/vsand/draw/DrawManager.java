package org.sheepy.vsand.draw;

import org.joml.Vector2ic;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;
import org.sheepy.vsand.util.BoardUtil;
import org.sheepy.vsand.util.EShapeSize;

public class DrawManager
{
	private final VSandApplication application;
	private final IInputManager inputManager;

	private boolean wasUsed = false;
	private Vector2ic previousCursor;

	public DrawManager(VSandApplication application, IInputManager inputManager)
	{
		this.application = application;
		this.inputManager = inputManager;
	}

	public void manage(Material material, boolean needDraw)
	{
		DrawCommand command = null;

		if (needDraw)
		{
			final var firstDraw = wasUsed == false;
			command = createDrawCommand(material, firstDraw);
			wasUsed = true;
		}
		else
		{
			wasUsed = false;
		}

		if (command != null)
		{
			application.getDrawQueue().add(command);
		}
	}

	private DrawCommand createDrawCommand(Material material, boolean firstDraw)
	{
		DrawCommand res = null;

		final var size = EShapeSize.values()[application.getBrushSize() - 1];
		final var cursor = inputManager.getCursorPosition();
		final var cursorBoard = BoardUtil.toBoardPosition(cursor, application);

		if (firstDraw
				|| (previousCursor.x() == cursorBoard.x() && previousCursor.y() == cursorBoard.y()))
		{
			final var command = VSandFactory.eINSTANCE.createDrawCircle();
			command.setMaterial(material);
			command.setSize(size.getSize());
			command.setX(cursorBoard.x());
			command.setY(cursorBoard.y());

			res = command;
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

			res = command;
		}

		previousCursor = cursorBoard;

		return res;
	}
}