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
import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.vsand.buffer.ModificationsManager;
import org.sheepy.vsand.input.VSandInputManager;
import org.sheepy.vsand.model.RepeatComputePipeline;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandConstants;
import org.sheepy.vsand.util.FPSCounter;

public class VSandMainLoop implements IMainLoop
{
	private IVulkanEngineAdapter engineAdapter;

	private DrawManager mainDrawManager;
	private DrawManager secondaryDrawManager;

	private final FPSCounter fpsCounter = new FPSCounter();

	private IProcessAdapter boardProcessAdapter;
	private IProcessAdapter renderProcessAdapter;

	private ModificationsManager modificationsManager;
	private VSandConstants constants;
	private RepeatComputePipeline stepPipeline;
	private ComputePipeline drawPipeline;
	private IInputManager inputManager;
	private VSandApplication application;

	private VSandInputManager vsandInputManager;

	private ComputeProcess boardProcess;

	@Override
	public void load(Application _application)
	{
		application = (VSandApplication) _application;

		var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		inputManager = engineAdapter.getInputManager();

		gatherProcesses(vulkanEngine);

		vsandInputManager = new VSandInputManager(application, constants, stepPipeline);
		inputManager.addListener(vsandInputManager);

		Vector2i boardSize = new Vector2i(stepPipeline.getWidth(), stepPipeline.getHeight());
		mainDrawManager = new DrawManager(application, inputManager, modificationsManager,
				boardSize);
		secondaryDrawManager = new DrawManager(application, inputManager, modificationsManager,
				boardSize);
	}

	private void gatherProcesses(VulkanEngine vulkanEngine)
	{
		EList<IProcess> processes = vulkanEngine.getProcesses();
		for (IProcess process : processes)
		{
			if (process instanceof ComputeProcess)
			{
				boardProcess = (ComputeProcess) process;
				boardProcessAdapter = IProcessAdapter.adapt(process);

				var pipelines = boardProcess.getPipelinePkg().getPipelines();
				drawPipeline = (ComputePipeline) pipelines.get(0);
				stepPipeline = (RepeatComputePipeline) pipelines.get(1);

				constants = (VSandConstants) stepPipeline.getConstants();
				var resources = boardProcess.getResourcePkg().getResources();

				var modificationBuffer = (Buffer) resources.get(4);
				modificationsManager = new ModificationsManager(modificationBuffer);
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

		var next = boardProcessAdapter.prepareNext();
		updateDrawManager();
		boardProcessAdapter.execute(next);

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

	private void updateDrawManager()
	{
		// Main draw
		mainDrawManager.manage(application.getMainMaterial(),
				vsandInputManager.isLeftClicPressed());

		// Secondary draw
		secondaryDrawManager.manage(application.getSecondaryMaterial(),
				vsandInputManager.isRightClicPressed());

		// Enable drawManager
		if (modificationsManager.isEmpty() == false)
		{
			if (drawPipeline.isEnabled() == false)
			{
				drawPipeline.setEnabled(true);
			}

			modificationsManager.update();
		}
		// disable drawManager
		else if (drawPipeline.isEnabled())
		{
			drawPipeline.setEnabled(false);
		}
	}
}
