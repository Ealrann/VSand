package org.sheepy.vsand.input;

import org.eclipse.emf.common.util.EList;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.input.event.KeyEvent;
import org.sheepy.lily.core.api.input.event.MouseClickEvent;
import org.sheepy.lily.core.api.input.event.ScrollEvent;
import org.sheepy.lily.core.model.types.EKeyState;
import org.sheepy.lily.game.api.window.IWindow;
import org.sheepy.lily.vulkan.api.input.IVulkanInputManager;
import org.sheepy.vsand.draw.DrawManager;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;

public class VSandInputManager
{
	private final VSandApplication application;
	private final IWindow window;
	private final DrawManager leftDrawManager;
	private final DrawManager rightDrawManager;

	private boolean shiftPressed = false;

	private boolean leftClickPressed = false;
	private boolean rightClickPressed = false;

	public VSandInputManager(final IVulkanInputManager inputManager,
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
					application.getScene().setFullscreen(!application.getScene().isFullscreen());
					break;
				// Escape
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
