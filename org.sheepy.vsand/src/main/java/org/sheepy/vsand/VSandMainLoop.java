package org.sheepy.vsand;

import static org.lwjgl.glfw.GLFW.*;

import org.eclipse.emf.common.util.EList;
import org.lwjgl.glfw.GLFWVidMode;
import org.sheepy.lily.core.api.cadence.IMainLoop;
import org.sheepy.lily.core.api.input.IInputManager;
import org.sheepy.lily.core.api.types.SVector2i;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.vulkan.api.adapter.IProcessAdapter;
import org.sheepy.lily.vulkan.api.adapter.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.concurrent.IFence;
import org.sheepy.lily.vulkan.model.IProcess;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.model.resource.Image;
import org.sheepy.vsand.buffer.BoardBufferLoader;
import org.sheepy.vsand.buffer.BoardDecisionLoader;
import org.sheepy.vsand.buffer.BoardImageLoader;
import org.sheepy.vsand.buffer.ConfigurationBufferLoader;
import org.sheepy.vsand.buffer.ModificationsManager;
import org.sheepy.vsand.buffer.TransformationBufferLoader;
import org.sheepy.vsand.input.VSandInputManager;
import org.sheepy.vsand.model.RepeatComputePipeline;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandConstants;
import org.sheepy.vsand.util.FPSCounter;
import org.sheepy.vsand.util.VSyncGuard;

public class VSandMainLoop implements IMainLoop
{
	private static final long FENCE_TIMEOUT_NS = (long) (500 * 1e6);

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

	private VSyncGuard vsyncGuard;

	private IFence drawFence;
	private VSandInputManager vsandInputManager;

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

		gatherAndLoadProcesses(width, height, vulkanEngine);

		vsandInputManager = new VSandInputManager(application, constants, stepPipeline);
		inputManager.addListener(vsandInputManager);

		engineAdapter.allocate();

		SVector2i boardSize = new SVector2i(stepPipeline.getWidth(), stepPipeline.getHeight());
		mainDrawManager = new DrawManager(application, inputManager, modificationsManager,
				boardSize);
		secondaryDrawManager = new DrawManager(application, inputManager, modificationsManager,
				boardSize);

		drawFence = engineAdapter.newFence(true);

		long monitor = glfwGetPrimaryMonitor();
		GLFWVidMode glfwGetVideoMode = glfwGetVideoMode(monitor);
		long refreshTimeAvailableNs = (long) (1. / glfwGetVideoMode.refreshRate() * 1e9);

		vsyncGuard = new VSyncGuard(refreshTimeAvailableNs);
		vsyncGuard.start();
	}

	private void gatherAndLoadProcesses(int width, int height, VulkanEngine vulkanEngine)
	{
		EList<IProcess> processes = vulkanEngine.getProcesses();
		for (IProcess process : processes)
		{
			if (process instanceof ComputeProcess)
			{
				var boardProcess = (ComputeProcess) process;
				boardProcessAdapter = IProcessAdapter.adapt(process);

				drawPipeline = (ComputePipeline) boardProcess.getUnits().get(0);
				stepPipeline = (RepeatComputePipeline) boardProcess.getUnits().get(1);

				constants = (VSandConstants) stepPipeline.getConstants();

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
	}

	@Override
	public void step(Application _application)
	{
		if (application.isDebug()) fpsCounter.step();

		updateDrawManager();

		constants.setFirstPass(true);

		boardProcessAdapter.prepare();
		if (drawPipeline.isEnabled())
		{
			drawFence.reset();
			boardProcessAdapter.execute(drawFence);
		}
		else
		{
			boardProcessAdapter.execute();
		}

		if (application.isNextMode() == true)
		{
			application.setNextMode(false);
			stepPipeline.setEnabled(false);
		}

		renderProcessAdapter.prepare();
		renderProcessAdapter.execute();

		vsyncGuard.step();
	}

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
			if (drawFence.waitForSignal(FENCE_TIMEOUT_NS) == false)
			{
				System.err.println("Frame too long");
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
