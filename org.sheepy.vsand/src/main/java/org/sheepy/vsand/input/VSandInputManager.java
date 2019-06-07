package org.sheepy.vsand.input;

import org.eclipse.emf.common.util.EList;
import org.sheepy.lily.core.api.input.IInputManager.IInputListener;
import org.sheepy.lily.core.api.input.event.KeyEvent;
import org.sheepy.lily.core.api.input.event.MouseButtonEvent;
import org.sheepy.lily.core.api.input.event.ScrollEvent;
import org.sheepy.lily.core.model.types.EKeyState;
import org.sheepy.vsand.draw.DrawManager;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vulkan.window.Window;

public class VSandInputManager implements IInputListener
{
	private final VSandApplication application;
	private final Window window;
	private final DrawManager leftDrawManager;
	private final DrawManager rightDrawManager;

	private boolean shiftPressed = false;

	private boolean leftClicPressed = false;
	private boolean rightClicPressed = false;

	public VSandInputManager(	Window window,
								VSandApplication application,
								DrawManager leftDrawManager,
								DrawManager rightDrawManager)
	{
		this.window = window;
		this.application = application;
		this.leftDrawManager = leftDrawManager;
		this.rightDrawManager = rightDrawManager;
	}

	@Override
	public void onMouseOverUI(boolean overUI)
	{
		window.hideCursor(!overUI);
	}

	@Override
	public void afterPollInputs()
	{
		leftDrawManager.manage(application.getMainMaterial(), leftClicPressed);
		rightDrawManager.manage(application.getSecondaryMaterial(), rightClicPressed);
	}

	@Override
	public void onKeyEvent(KeyEvent event)
	{
		switch (event.key)
		{
		// Shift
		case 340:
			if (event.state == EKeyState.PRESSED)
			{
				shiftPressed = true;
			}
			else if (event.state == EKeyState.RELEASED)
			{
				shiftPressed = false;
			}
			break;
		}

		// Pressed specific
		if (event.state == EKeyState.PRESSED)
		{
			switch (event.key)
			{
			// Space bar
			case 32:
				application.setPaused(!application.isPaused());
				break;
			// *
			case 331:
				int speedMinus = application.getSpeed();
				speedMinus = Math.max(1, speedMinus / 2);
				application.setSpeed(speedMinus);
				break;
			// /
			case 332:
				int speedPlus = application.getSpeed();
				speedPlus = Math.min(16, speedPlus * 2);
				application.setSpeed(speedPlus);
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
			// n
			case 'n' - 32:
				application.setNextMode(true);
				break;
			// f
			case 'f' - 32:
				application.setFullscreen(!application.isFullscreen());
				break;
			// Echap
			case 256:
				application.setRun(false);
				break;
			// s
			case 's' - 32:
				application.setForceClear(true);
				application.setShowSleepZones(!application.isShowSleepZones());
				break;
			}
		}
		else
		{
			switch (event.key)
			{
			// s
			case 's' - 32:
				application.setForceClear(false);
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
		final var materials = application.getMaterials().getMaterials();
		final int index = materials.indexOf(mainMaterial);
		Material next = null;

		if (event.yOffset > 0f)
		{
			next = findNextUserFriendlyMaterial(materials, index, -1);
		}
		else if (event.yOffset < 0f)
		{
			next = findNextUserFriendlyMaterial(materials, index, 1);
		}

		if (next != null)
		{
			if (shiftPressed)
			{
				application.setSecondaryMaterial(next);
			}
			else
			{
				application.setMainMaterial(next);
			}
		}
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
	public void onMouseClickEvent(MouseButtonEvent event)
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
}
