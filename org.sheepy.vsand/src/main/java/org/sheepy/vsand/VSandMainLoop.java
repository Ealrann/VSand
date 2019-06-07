package org.sheepy.vsand;

import org.joml.Vector2i;
import org.sheepy.lily.core.api.cadence.IMainLoop;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.process.IProcessAdapter;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vsand.draw.DrawManager;
import org.sheepy.vsand.input.VSandInputManager;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.FPSCounter;
import org.sheepy.vulkan.window.Window;

public class VSandMainLoop implements IMainLoop
{
	private final FPSCounter fpsCounter = new FPSCounter();

	private IVulkanEngineAdapter engineAdapter;
	private IProcessAdapter boardProcessAdapter;
	private IProcessAdapter renderProcessAdapter;

	private IInputManager inputManager;
	private VSandApplication application;

	@Override
	public void load(Application _application)
	{
		application = (VSandApplication) _application;

		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		final Window window = engineAdapter.getWindow();
		inputManager = engineAdapter.getInputManager();

		gatherProcesses(vulkanEngine);

		final var boardSize = new Vector2i(application.getSize());
		final var mainDrawManager = new DrawManager(application, inputManager, boardSize);
		final var secondaryDrawManager = new DrawManager(application, inputManager, boardSize);

		final var vsandInputManager = new VSandInputManager(window, application, mainDrawManager,
				secondaryDrawManager);
		inputManager.addListener(vsandInputManager);
	}

	private void gatherProcesses(VulkanEngine vulkanEngine)
	{
		final var processes = vulkanEngine.getProcesses();
		for (final var process : processes)
		{
			if (process instanceof ComputeProcess)
			{
				boardProcessAdapter = IProcessAdapter.adapt(process);
			}
			else if (process instanceof GraphicProcess)
			{
				renderProcessAdapter = IProcessAdapter.adapt(process);
			}
		}
	}

	@Override
	public void step(Application _application)
	{
		if (DebugUtil.DEBUG_ENABLED) fpsCounter.step();

		boardProcessAdapter.prepareNextAndExecute();

		if (application.isNextMode() == true)
		{
			application.setNextMode(false);
			application.setPaused(true);
		}

		renderProcessAdapter.prepareNextAndExecute();
	}

	@Override
	public void free(Application application)
	{}
}
