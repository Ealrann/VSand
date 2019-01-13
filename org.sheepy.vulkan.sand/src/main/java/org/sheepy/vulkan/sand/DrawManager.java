package org.sheepy.vulkan.sand;

import org.sheepy.common.api.input.IInputManager;
import org.sheepy.common.api.types.SVector2f;
import org.sheepy.common.api.types.SVector2i;
import org.sheepy.vulkan.common.window.Window;
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
	private final Window window;

	public DrawManager(	VSandApplication application,
						Window window,
						IInputManager inputManager,
						ModificationsManager modificationsManager)
	{
		this.application = application;
		this.window = window;
		this.inputManager = inputManager;
		this.modificationsManager = modificationsManager;
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

		int width = window.getSurface().width;
		int height = window.getSurface().height;
		if (width != VSandApplicationLauncher.WIDTH || height != VSandApplicationLauncher.HEIGHT)
		{
			res.x *= (float) VSandApplicationLauncher.WIDTH / width;
			res.y *= (float) VSandApplicationLauncher.HEIGHT / height;
		}

		return res;
	}
}