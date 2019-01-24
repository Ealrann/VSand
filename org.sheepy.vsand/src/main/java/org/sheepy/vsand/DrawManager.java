package org.sheepy.vulkan.sand;

import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.types.SVector2f;
import org.sheepy.lily.core.api.types.SVector2i;
import org.sheepy.vulkan.sand.buffer.ModificationsManager;
import org.sheepy.vulkan.sand.buffer.ModificationsManager.DrawData;
import org.sheepy.vulkan.sand.logic.EShape;
import org.sheepy.vulkan.sand.logic.EShapeSize;
import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.VSandApplication;

public class DrawManager
{
	private final VSandApplication application;
	private final IInputManager inputManager;
	private final ModificationsManager modificationsManager;

	private boolean wasUsed = false;
	private final SVector2i boardSize;

	public DrawManager(	VSandApplication application,
						IInputManager inputManager,
						ModificationsManager modificationsManager,
						SVector2i boardSize)
	{
		this.application = application;
		this.inputManager = inputManager;
		this.modificationsManager = modificationsManager;
		this.boardSize = boardSize;
	}

	public void manage(Material material, boolean needDraw)
	{
		DrawData data = null;

		if (needDraw)
		{
			var firstDraw = wasUsed == false;
			data = newDrawData(material, firstDraw);
			wasUsed = true;
		}
		else
		{
			wasUsed = false;
		}

		if (data != null)
		{
			modificationsManager.pushModification(data);
		}
	}

	private DrawData newDrawData(Material material, boolean firstDraw)
	{
		var size = EShapeSize.values()[application.getBrushSize() - 1];
		var shape = firstDraw ? EShape.Circle : EShape.Line;

		var cursor = inputManager.getMouseLocation();
		var cursorBoard = convertToBoardPosition(cursor);

		return new DrawData(size, shape, cursorBoard, material);
	}

	private SVector2i convertToBoardPosition(SVector2f mousePos)
	{
		SVector2i res = new SVector2i((int) mousePos.x, (int) mousePos.y);

		int boardWidth = boardSize.x;
		int boardHeight = boardSize.y;

		int width = application.getSize().x;
		int height = application.getSize().y;
		if (width != boardWidth || height != boardHeight)
		{
			res.x *= (float) boardWidth / width;
			res.y *= (float) boardHeight / height;
		}

		return res;
	}
}