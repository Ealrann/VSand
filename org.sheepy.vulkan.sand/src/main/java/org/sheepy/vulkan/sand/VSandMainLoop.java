package org.sheepy.vulkan.sand;

import static org.lwjgl.glfw.GLFW.*;

import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.EList;
import org.lwjgl.glfw.GLFWVidMode;
import org.sheepy.common.api.cadence.IMainLoop;
import org.sheepy.common.api.input.IInputManager;
import org.sheepy.common.api.input.IInputManager.IInputListener;
import org.sheepy.common.api.input.event.KeyEvent;
import org.sheepy.common.api.input.event.MouseButtonEvent;
import org.sheepy.common.api.input.event.ScrollEvent;
import org.sheepy.common.api.types.SVector2f;
import org.sheepy.common.model.application.Application;
import org.sheepy.common.model.types.EKeyState;
import org.sheepy.common.model.types.EMouseButton;
import org.sheepy.vulkan.api.adapter.IProcessAdapter;
import org.sheepy.vulkan.api.adapter.IVulkanEngineAdapter;
import org.sheepy.vulkan.api.concurrent.IFence;
import org.sheepy.vulkan.api.window.IWindow;
import org.sheepy.vulkan.model.IProcess;
import org.sheepy.vulkan.model.VulkanEngine;
import org.sheepy.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vulkan.model.resource.Buffer;
import org.sheepy.vulkan.model.resource.Image;
import org.sheepy.vulkan.sand.buffer.BoardBufferLoader;
import org.sheepy.vulkan.sand.buffer.BoardDecisionLoader;
import org.sheepy.vulkan.sand.buffer.BoardImageLoader;
import org.sheepy.vulkan.sand.buffer.ConfigurationBufferLoader;
import org.sheepy.vulkan.sand.buffer.ModificationsManager;
import org.sheepy.vulkan.sand.buffer.TransformationBufferLoader;
import org.sheepy.vulkan.sand.logic.EShape;
import org.sheepy.vulkan.sand.logic.EShapeSize;
import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.RepeatComputePipeline;
import org.sheepy.vulkan.sand.model.VSandApplication;
import org.sheepy.vulkan.sand.model.VSandConstants;
import org.sheepy.vulkan.sand.util.FPSCounter;

public class VSandMainLoop implements IMainLoop
{
	private IVulkanEngineAdapter engineAdapter;
	private IWindow window;

	private final FPSCounter fpsCounter = new FPSCounter();

	// in update per frame
	private boolean next = false;
	private boolean firstDrawLeft = false;
	private boolean drawEnabledLeftClic = false;
	private boolean firstDrawRight = false;
	private boolean drawEnabledRightClic = false;

	private IProcessAdapter boardProcessAdapter;
	private IProcessAdapter renderProcessAdapter;

	private ModificationsManager modificationsManager;
	private VSandConstants constant;
	private RepeatComputePipeline stepPipeline;
	private ComputePipeline drawPipeline;
	private IInputManager inputManager;
	private VSandApplication application;
	private boolean shiftPressed;
	private long refreshTimeAvailableNs;
	private long nextFrameEndDateNs = 0;

	private IFence drawFence;

	@Override
	public void load(Application _application)
	{
		application = (VSandApplication) _application;

		int width = application.getSize().x;
		int height = application.getSize().y;

		var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		inputManager = engineAdapter.getInputManager();

		var boardImage = (Image) vulkanEngine.getSharedResources().getResources().get(0);
		BoardImageLoader.load(boardImage, width, height);

		EList<IProcess> processes = vulkanEngine.getProcesses();
		for (IProcess process : processes)
		{
			if (process instanceof ComputeProcess)
			{
				var boardProcess = (ComputeProcess) process;
				boardProcessAdapter = IProcessAdapter.adapt(process);

				drawPipeline = (ComputePipeline) boardProcess.getUnits().get(0);
				stepPipeline = (RepeatComputePipeline) boardProcess.getUnits().get(1);

				constant = (VSandConstants) stepPipeline.getConstants();

				var boardBuffer = (Buffer) boardProcess.getResources().get(0);
				var configurationBuffer = (Buffer) boardProcess.getResources().get(1);
				var transformationBuffer = (Buffer) boardProcess.getResources().get(2);
				var decisionBuffer = (Buffer) boardProcess.getResources().get(3);
				var modificationBuffer = (Buffer) boardProcess.getResources().get(4);

				modificationsManager = new ModificationsManager(modificationBuffer);
				BoardBufferLoader.load(boardBuffer, width, height);
				BoardDecisionLoader.load(decisionBuffer, width, height);
				ConfigurationBufferLoader.load(configurationBuffer);
				TransformationBufferLoader.load(transformationBuffer);
			}
			else if (process instanceof GraphicProcess)
			{
				renderProcessAdapter = IProcessAdapter.adapt(process);
			}
		}

		inputManager.addListener(new MouseListener());

		engineAdapter.allocate();
		window = engineAdapter.getWindow();

		drawFence = engineAdapter.newFence();

		long monitor = glfwGetPrimaryMonitor();
		GLFWVidMode glfwGetVideoMode = glfwGetVideoMode(monitor);
		refreshTimeAvailableNs = (long) (1. / glfwGetVideoMode.refreshRate() * 1e9);
		nextFrameEndDateNs = System.nanoTime() + refreshTimeAvailableNs;
	}

	@Override
	public void step(Application _application)
	{
		if (application.isDebug()) fpsCounter.step();

		SVector2f cursorPosition = inputManager.getMouseLocation();

		// Main draw
		if (drawEnabledLeftClic)
		{
			drawMain(cursorPosition, application);
		}

		// Secondary draw
		if (drawEnabledRightClic)
		{
			drawSecondary(cursorPosition, application);
		}

		// meter.startRecord();
		updateBoard();

		boardProcessAdapter.getQueue().waitIdle();
		constant.setFirstPass(true);
		boardProcessAdapter.prepare();

		drawFence.reset();
		boardProcessAdapter.execute(drawFence);
		// meter.endRecord();

		if (next == true)
		{
			next = false;
			stepPipeline.setEnabled(false);
		}

		renderProcessAdapter.prepare();
		renderProcessAdapter.execute();

		vsyncGuard();
	}

	// The Vulkan spec doesn't impose any vsync, even with Fifo (even if generally, drivers waits
	// VSync when Fifo
	// is enabled).
	// We ensure "VSync" here, in order to not consume all CPU/GPU resources.
	private void vsyncGuard()
	{
		long remainingUntilDeadlineNs = nextFrameEndDateNs - System.nanoTime();
		if (remainingUntilDeadlineNs > 0)
		{
			try
			{
				TimeUnit.NANOSECONDS.sleep(remainingUntilDeadlineNs);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			// We are late, so let's recalibrate the clock
			nextFrameEndDateNs = System.nanoTime();
		}

		nextFrameEndDateNs += refreshTimeAvailableNs;
	}

	private void updateBoard()
	{
		if (modificationsManager.isEmpty() == false)
		{
			drawPipeline.setEnabled(true);
			modificationsManager.update(drawFence);
		}
		else if (drawPipeline.isEnabled())
		{
			drawPipeline.setEnabled(false);
		}
	}

	private void drawMain(SVector2f cursorPosition, VSandApplication application)
	{
		EShapeSize size = EShapeSize.values()[application.getBrushSize() - 1];
		Material material = application.getMainMaterial();
		draw(cursorPosition, firstDrawLeft, size, material);
		firstDrawLeft = false;
	}

	private void drawSecondary(SVector2f cursorPosition, VSandApplication application)
	{
		EShapeSize size = EShapeSize.values()[application.getBrushSize() - 1];
		Material material = application.getSecondaryMaterial();
		draw(cursorPosition, firstDrawRight, size, material);
		firstDrawRight = false;
	}

	private void draw(	SVector2f cursorPosition,
						boolean firstDraw,
						EShapeSize size,
						Material material)
	{
		computeBoardMousePosition(cursorPosition);
		if (firstDraw)
		{
			modificationsManager.pushModification(EShape.Circle, size, (int) cursorPosition.x,
					(int) cursorPosition.y, material);
		}
		else
		{
			modificationsManager.pushModification(EShape.Line, size, (int) cursorPosition.x,
					(int) cursorPosition.y, material);
		}
	}

	private void computeBoardMousePosition(SVector2f mousePos)
	{
		int width = window.getSurface().width;
		int height = window.getSurface().height;
		if (width != SandApplication.WIDTH && height != SandApplication.HEIGHT)
		{
			mousePos.x *= (float) SandApplication.WIDTH / width;
			mousePos.y *= (float) SandApplication.HEIGHT / height;
		}
	}

	private class MouseListener implements IInputListener
	{

		@Override
		public void onKeyEvent(KeyEvent event)
		{
			// Shift
			if (event.key == 340)
			{
				shiftPressed = event.state == EKeyState.PRESSED;
			}

			if (event.state == EKeyState.PRESSED)
			{
				switch (event.key)
				{
				// Space bar
				case 32:
					stepPipeline.setEnabled(!stepPipeline.isEnabled());
					break;
				// n
				case 'n' - 32:
					next = true;
					stepPipeline.setEnabled(true);
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

			if (event.yOffset > 0)
			{
				next = findNextUserFriendlyMaterial(materials, index, -1);
			}
			else
			{
				next = findNextUserFriendlyMaterial(materials, index, 1);
			}

			if (shiftPressed) application.setSecondaryMaterial(next);
			else application.setMainMaterial(next);
		}

		private Material findNextUserFriendlyMaterial(	EList<Material> materials,
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

				if (index < 0)
				{
					index = materials.size() - 1;
				}
			} while (next.isUserFriendly() == false);
			return next;
		}

		@Override
		public void onMouseClickEvent(SVector2f location, MouseButtonEvent event)
		{
			if (event.button == EMouseButton.LEFT)
			{
				if (event.pressed == true)
				{
					if (drawEnabledLeftClic == false)
					{
						firstDrawLeft = true;
					}
					drawEnabledLeftClic = true;
				}
				else
				{
					drawEnabledLeftClic = false;
				}

				firstDrawRight = false;
				drawEnabledRightClic = false;
			}

			if (event.button == EMouseButton.RIGHT)
			{
				if (event.pressed == true)
				{
					if (drawEnabledRightClic == false)
					{
						firstDrawRight = true;
					}
					drawEnabledRightClic = true;
				}
				else
				{
					drawEnabledRightClic = false;
				}

				firstDrawLeft = false;
				drawEnabledLeftClic = false;
			}
		}
	}
}
