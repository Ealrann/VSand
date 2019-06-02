package org.sheepy.vsand;

import org.eclipse.emf.common.util.EList;
import org.joml.Vector2i;
import org.sheepy.lily.core.api.cadence.IMainLoop;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.process.IProcessAdapter;
import org.sheepy.lily.vulkan.model.IProcess;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.CompositeTask;
import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vsand.draw.DrawManager;
import org.sheepy.vsand.input.VSandInputManager;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.FPSCounter;
import org.sheepy.vulkan.window.Window;

public class VSandMainLoop implements IMainLoop
{
	private IVulkanEngineAdapter engineAdapter;

	private final FPSCounter fpsCounter = new FPSCounter();

	private IProcessAdapter boardProcessAdapter;
	private IProcessAdapter renderProcessAdapter;

	private ComputePipeline stepPipeline;
	private CompositeTask stepTasks;
	private IInputManager inputManager;
	private VSandApplication application;

	private ComputeProcess boardProcess;

	@Override
	public void load(Application _application)
	{
		application = (VSandApplication) _application;

		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		final Window window = engineAdapter.getWindow();
		inputManager = engineAdapter.getInputManager();

		gatherProcesses(vulkanEngine);

		final Vector2i boardSize = new Vector2i(stepPipeline.getWidth(), stepPipeline.getHeight());
		final var mainDrawManager = new DrawManager(application, inputManager, boardSize);
		final var secondaryDrawManager = new DrawManager(application, inputManager, boardSize);

		final var vsandInputManager = new VSandInputManager(window, application, stepTasks,
				mainDrawManager, secondaryDrawManager);
		inputManager.addListener(vsandInputManager);
	}

	private void gatherProcesses(VulkanEngine vulkanEngine)
	{
		final EList<IProcess> processes = vulkanEngine.getProcesses();
		for (final IProcess process : processes)
		{
			if (process instanceof ComputeProcess)
			{
				boardProcess = (ComputeProcess) process;
				boardProcessAdapter = IProcessAdapter.adapt(process);

				final var parts = boardProcess.getPartPkg().getParts();
				stepPipeline = (ComputePipeline) parts.get(1);
				stepTasks = (CompositeTask) stepPipeline.getTaskPkg().getTasks().get(2);
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
			stepPipeline.setEnabled(false);
		}

		renderProcessAdapter.prepareNextAndExecute();
	}

	@Override
	public void free(Application application)
	{}
}
