package org.sheepy.vsand.util;

import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.sheepy.vsand.model.VSandApplication;

public final class BoardUtil
{
	public static Vector2ic toBoardPosition(Vector2fc mousePos, VSandApplication application)
	{
		final var boardSize = application.getSize();
		final var sceneSize = application.getScene().getSize();
		final float boardWidth = boardSize.x();
		final float boardHeight = boardSize.y();
		final float width = sceneSize.x();
		final float height = sceneSize.y();

		if (width != boardWidth || height != boardHeight)
		{
			final float scale = Math.min(width / boardWidth, height / boardHeight);
			final int dstWidth = (int) Math.ceil(scale * boardWidth);
			final int dstHeight = (int) Math.ceil(scale * boardHeight);

			int xOffset = 0;
			int yOffset = 0;
			if (dstWidth < width)
			{
				xOffset = (int) ((width - dstWidth) / 2f);
			}
			if (dstHeight < height)
			{
				yOffset = (int) ((height - dstHeight) / 2f);
			}
			final int x = (int) ((mousePos.x() - xOffset) * (boardWidth / dstWidth));
			final int y = (int) ((mousePos.y() - yOffset) * (boardHeight / dstHeight));
			return new Vector2i(x, y);
		}
		else
		{
			return new Vector2i((int) mousePos.x(), (int) mousePos.y());
		}
	}
}
