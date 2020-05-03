package org.sheepy.vsand.input;

import org.eclipse.emf.common.util.EList;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.input.event.KeyEvent;
import org.sheepy.lily.core.api.input.event.MouseClickEvent;
import org.sheepy.lily.core.api.input.event.ScrollEvent;
import org.sheepy.lily.core.model.types.EKeyState;
import org.sheepy.lily.game.api.window.IWindow;
import org.sheepy.vsand.draw.DrawManager;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

public final class VSandInputManager
{
	private final VSandApplication application;
	private final IWindow window;
	private final DrawManager leftDrawManager;
	private final DrawManager rightDrawManager;

	private boolean shiftPressed = false;

	private boolean leftClickPressed = false;
	private boolean rightClickPressed = false;

	public VSandInputManager(final IInputManager inputManager,
							 IWindow window,
							 VSandApplication application,
							 DrawManager leftDrawManager,
							 DrawManager rightDrawManager)
	{
		this.window = window;
		this.application = application;
		this.leftDrawManager = leftDrawManager;
		this.rightDrawManager = rightDrawManager;

		inputManager.listen(this::onMouseOverUI, IInputManager.Features.MouseOverUIEvent);
		inputManager.listen(this::afterPollInputs, IInputManager.Features.AfterPollInputs);
		inputManager.listen(this::onKeyEvent, IInputManager.Features.KeyEvent);
		inputManager.listen(this::onScrollEvent, IInputManager.Features.ScrollEvent);
		inputManager.listen(this::onMouseClickEvent, IInputManager.Features.MouseClickEvent);
	}

	private void onMouseOverUI(Boolean overUI)
	{
		window.hideCursor(!overUI);
	}

	private void afterPollInputs()
	{
		leftDrawManager.manage(application.getMainMaterial(), leftClickPressed);
		rightDrawManager.manage(application.getSecondaryMaterial(), rightClickPressed);
	}

	private void onKeyEvent(KeyEvent event)
	{
		switch (event.key)
		{
			case 340 -> shiftKeyEvent(event.state); // Shift
		}

		// Pressed specific
		if (event.state == EKeyState.PRESSED)
		{
			switch (event.key)
			{
				case 32 -> application.setPaused(!application.isPaused()); // space bar
				case 331 -> doubleSpeed(); // *
				case 332 -> halfSpeed(); // /
				case 333 -> smallerBrush(); // -
				case 334 -> biggerBrush(); // +
				case 'n' - 32 -> application.setNextMode(true); // n
				case 'f' - 32 -> application.getScene().setFullscreen(!application.getScene().isFullscreen());
				case 256 -> application.setRun(false); // Escape
				case 's' - 32 -> showDebug();
			}
		}
		else
		{
			switch (event.key)
			{
				case 's' - 32 -> application.setForceClear(false);
			}
		}
	}

	private void shiftKeyEvent(final EKeyState state)
	{
		if (state == EKeyState.PRESSED)
		{
			shiftPressed = true;
		}
		else if (state == EKeyState.RELEASED)
		{
			shiftPressed = false;
		}
	}

	private void showDebug()
	{
		application.setForceClear(true);
		application.setShowSleepZones(!application.isShowSleepZones());
	}

	private void biggerBrush()
	{
		final int current = application.getBrushSize();
		final int brushPlus = Math.min(8, current + 1);
		application.setBrushSize(brushPlus);
	}

	private void smallerBrush()
	{
		final int current = application.getBrushSize();
		final int brushMinus = Math.max(1, current - 1);
		application.setBrushSize(brushMinus);
	}

	private void halfSpeed()
	{
		final int current = application.getSpeed();
		final int speedPlus = Math.min(16, current * 2);
		application.setSpeed(speedPlus);
	}

	private void doubleSpeed()
	{
		final int current = application.getSpeed();
		final int speedMinus = Math.max(1, current / 2);
		application.setSpeed(speedMinus);
	}

	private void onScrollEvent(ScrollEvent event)
	{
		final var mainMaterial = shiftPressed ? application.getSecondaryMaterial() : application.getMainMaterial();
		final boolean goDown = event.yOffset > 0f;
		final var next = findMaterial(mainMaterial, goDown);

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

	private Material findMaterial(Material mainMaterial, boolean needNext)
	{
		final var materials = application.getMaterials().getMaterials();
		final int index = materials.indexOf(mainMaterial);
		if (needNext)
		{
			return findNextUserFriendlyMaterial(materials, index, -1);
		}
		else
		{
			return findNextUserFriendlyMaterial(materials, index, 1);
		}
	}

	private static Material findNextUserFriendlyMaterial(EList<Material> materials, int index, int direction)
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

	private void onMouseClickEvent(MouseClickEvent event)
	{
		switch (event.button)
		{
			case LEFT:
				leftClickPressed = event.pressed;
				rightClickPressed = false;
				break;
			case RIGHT:
				rightClickPressed = event.pressed;
				leftClickPressed = false;
				break;
			default:
		}
	}
}
