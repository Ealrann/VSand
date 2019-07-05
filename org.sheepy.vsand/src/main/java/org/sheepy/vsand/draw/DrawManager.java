package org.sheepy.vsand.draw;

import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;
import org.sheepy.vsand.util.EShapeSize;

public class DrawManager
{
	private final VSandApplication application;
	private final IInputManager inputManager;
	private final Vector2ic boardSize;

	private boolean wasUsed = false;
	private Vector2ic previousCursor;

	public DrawManager(	VSandApplication application,
						IInputManager inputManager,
						Vector2ic boardSize)
	{
		this.application = application;
		this.inputManager = inputManager;
		this.boardSize = boardSize;
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
		final var cursorBoard = convertToBoardPosition(cursor);

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

	private Vector2ic convertToBoardPosition(Vector2fc mousePos)
	{
		final var res = new Vector2i((int) mousePos.x(), (int) mousePos.y());

		final int boardWidth = boardSize.x();
		final int boardHeight = boardSize.y();

		final int width = application.getSize().x();
		final int height = application.getSize().y();
		if (width != boardWidth || height != boardHeight)
		{
			res.x *= (float) boardWidth / width;
			res.y *= (float) boardHeight / height;
		}

		return res;
	}
}