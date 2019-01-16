package org.sheepy.vulkan.sand;

import static org.lwjgl.glfw.GLFW.*;

import org.eclipse.emf.common.util.EList;
import org.lwjgl.glfw.GLFWVidMode;
import org.sheepy.common.api.cadence.IMainLoop;
import org.sheepy.common.api.input.IInputManager;
import org.sheepy.common.model.application.Application;
import org.sheepy.vulkan.api.adapter.IProcessAdapter;
import org.sheepy.vulkan.api.adapter.IVulkanEngineAdapter;
import org.sheepy.vulkan.api.concurrent.IFence;
import org.sheepy.vulkan.common.window.Window;
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
import org.sheepy.vulkan.sand.input.VSandInputManager;
import org.sheepy.vulkan.sand.model.RepeatComputePipeline;
import org.sheepy.vulkan.sand.model.VSandApplication;
import org.sheepy.vulkan.sand.model.VSandConstants;
import org.sheepy.vulkan.sand.util.FPSCounter;
import org.sheepy.vulkan.sand.util.VSyncGuard;

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
	private VSandConstants constant;
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

		vsandInputManager = new VSandInputManager(application, stepPipeline);
		inputManager.addListener(vsandInputManager);

		engineAdapter.allocate();
		var window = (Window) engineAdapter.getWindow();

		mainDrawManager = new DrawManager(application, window, inputManager, modificationsManager);
		secondaryDrawManager = new DrawManager(application, window, inputManager,
				modificationsManager);

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
	}

	@Override
	public void step(Application _application)
	{
		if (application.isDebug()) fpsCounter.step();

		updateDrawManager();

		constant.setFirstPass(true);

		drawFence.reset();
		boardProcessAdapter.prepare();
		boardProcessAdapter.execute(drawFence);

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
