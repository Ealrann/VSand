package org.sheepy.vsand.util;

import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.sheepy.vsand.model.VSandApplication;

public final class BoardUtil
{
	public static Vector2ic toBoardPosition(Vector2fc mousePos,
													VSandApplication application)
	{
		final var boardSize = application.getSize();
		final var sceneSize = application.getScene().getSize();
		final float boardWidth = boardSize.x();
		final float boardHeight = boardSize.y();
		final float width = sceneSize.x();
		final float height = sceneSize.y();

		if (width != boardWidth || height != boardHeight)
		{
			final int x = (int) (mousePos.x() * (boardWidth / width));
			final int y = (int) (mousePos.y() * (boardHeight / height));
			return new Vector2i(x, y);
		}
		else
		{
			return new Vector2i((int) mousePos.x(), (int) mousePos.y());
		}
	}
}
