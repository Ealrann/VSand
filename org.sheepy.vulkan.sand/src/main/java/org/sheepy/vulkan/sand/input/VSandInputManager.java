package org.sheepy.vulkan.sand.input;

import org.eclipse.emf.common.util.EList;
import org.sheepy.common.api.input.IInputManager.IInputListener;
import org.sheepy.common.api.input.event.KeyEvent;
import org.sheepy.common.api.input.event.MouseButtonEvent;
import org.sheepy.common.api.input.event.ScrollEvent;
import org.sheepy.common.api.types.SVector2f;
import org.sheepy.common.model.types.EKeyState;
import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.RepeatComputePipeline;
import org.sheepy.vulkan.sand.model.VSandApplication;

public class VSandInputManager implements IInputListener
{
	private final VSandApplication application;
	private final RepeatComputePipeline stepPipeline;

	private boolean shiftPressed = false;

	private boolean leftClicPressed = false;
	private boolean rightClicPressed = false;

	public VSandInputManager(VSandApplication application, RepeatComputePipeline stepPipeline)
	{
		this.application = application;
		this.stepPipeline = stepPipeline;
	}

	@Override
	public void onKeyEvent(KeyEvent event)
	{
		switch (event.key)
		{
		// Shift
		case 340:
			shiftPressed = event.state == EKeyState.PRESSED;
			break;
		// n
		case 'n' - 32:
			application.setNextMode(true);
			stepPipeline.setEnabled(true);
			stepPipeline.setRepeatCount(1);
			break;
		}

		// Pressed specific
		if (event.state == EKeyState.PRESSED)
		{
			switch (event.key)
			{
			// Space bar
			case 32:
				stepPipeline.setEnabled(!stepPipeline.isEnabled());
				break;
			// *
			case 331:
				int speedMinus = stepPipeline.getRepeatCount();
				speedMinus = Math.max(1, speedMinus / 2);
				stepPipeline.setRepeatCount(speedMinus);
				break;
			// /
			case 332:
				int speedPlus = stepPipeline.getRepeatCount();
				speedPlus = Math.min(16, speedPlus * 2);
				stepPipeline.setRepeatCount(speedPlus);
				break;
			// -
			case 333:
				int brushMinus = application.getBrushSize();
				brushMinus = Math.max(1, brushMinus - 1);
				application.setBrushSize(brushMinus);
				break;

			// +
			case 334:
				int brushPlus = application.getBrushSize();
				brushPlus = Math.min(8, brushPlus + 1);
				application.setBrushSize(brushPlus);
				break;
			}
		}
	}

	@Override
	public void onScrollEvent(ScrollEvent event)
	{
		Material mainMaterial = null;
		if (shiftPressed) mainMaterial = application.getSecondaryMaterial();
		else mainMaterial = application.getMainMaterial();
		var materials = application.getMaterials().getMaterials();
		int index = materials.indexOf(mainMaterial);
		Material next = null;

		if (event.yOffset > 0f)
		{
			next = findNextUserFriendlyMaterial(materials, index, -1);
		}
		else if (event.yOffset < 0f)
		{
			next = findNextUserFriendlyMaterial(materials, index, 1);
		}

		if (shiftPressed) application.setSecondaryMaterial(next);
		else application.setMainMaterial(next);
	}

	private static Material findNextUserFriendlyMaterial(	EList<Material> materials,
															int index,
															int direction)
	{
		Material next;
		do
		{
			index += direction;
			if (index < 0)
			{
				index = materials.size() - 1;
			}
			else if (index == materials.size())
			{
				index = 0;
			}
			next = materials.get(index);
		} while (next.isUserFriendly() == false);

		return next;
	}

	@Override
	public void onMouseClickEvent(SVector2f location, MouseButtonEvent event)
	{
		switch (event.button)
		{
		case LEFT:
			leftClicPressed = event.pressed;
			rightClicPressed = false;
			break;
		case RIGHT:
			rightClicPressed = event.pressed;
			leftClicPressed = false;
			break;
		default:
		}
	}

	public boolean isLeftClicPressed()
	{
		return leftClicPressed;
	}

	public boolean isRightClicPressed()
	{
		return rightClicPressed;
	}
}
