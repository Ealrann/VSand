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

public final class VSandMainLoop implements IMainLoop
{
	private final FPSCounter fpsCounter = new FPSCounter();
	private final VSandApplication application;
	private final boolean benchmarkMode;
	private final int stopIteration;

	private IVulkanEngineAdapter engineAdapter;
	private IProcessAdapter boardProcessAdapter;
	private IProcessAdapter renderProcessAdapter;

	private IInputManager inputManager;
	private long currentIteration = 0;
	private long startNs;
	private long frameDurationNs;
	private long nextRenderDate;

	public static final VSandMainLoop create(VSandApplication application)
	{
		return new VSandMainLoop(application, false, -1);
	}

	public static final VSandMainLoop createBenchmark(	VSandApplication application,
														int iterationCount)
	{
		return new VSandMainLoop(application, true, iterationCount);
	}

	private VSandMainLoop(VSandApplication application, boolean benchmarkMode, int stopIteration)
	{
		this.application = application;
		this.benchmarkMode = benchmarkMode;
		this.stopIteration = stopIteration;
	}

	@Override
	public void load(Application _application)
	{
		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		engineAdapter = IVulkanEngineAdapter.adapt(vulkanEngine);
		final Window window = engineAdapter.getWindow();
		frameDurationNs = (long) ((1. / window.getRefreshRate()) * 1e9);
		inputManager = engineAdapter.getInputManager();

		gatherProcesses(vulkanEngine);

		final var boardSize = new Vector2i(application.getSize());
		final var mainDrawManager = new DrawManager(application, inputManager, boardSize);
		final var secondaryDrawManager = new DrawManager(application, inputManager, boardSize);

		if (benchmarkMode == false)
		{
			final var vsandInputManager = new VSandInputManager(window, application,
					mainDrawManager, secondaryDrawManager);
			inputManager.addListener(vsandInputManager);
		}

		startNs = System.nanoTime();
		nextRenderDate = System.nanoTime() + frameDurationNs;
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

		if (benchmarkMode == false)
		{
			renderProcessAdapter.prepareNextAndExecute();
		}
		else
		{
			if (nextRenderDate > System.nanoTime())
			{
				renderProcessAdapter.prepareNextAndExecute();
				nextRenderDate = System.nanoTime() + frameDurationNs;
			}
		}

		currentIteration++;
		if (currentIteration == stopIteration)
		{
			application.setRun(false);
			printBenchmarkResult();
		}
	}

	private void printBenchmarkResult()
	{
		final double duration = (System.nanoTime() - startNs) / 1e9;
		final double durationPerIteration = duration / stopIteration;
		
		final long score = (long) ((1. / durationPerIteration) * 100);

		System.out.println("Benchmark duration: " + duration + " seconds");
		System.out.println("Score: " + score);
	}

	@Override
	public void free(Application application)
	{}
}
