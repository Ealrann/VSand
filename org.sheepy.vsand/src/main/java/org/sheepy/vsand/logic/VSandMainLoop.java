package org.sheepy.vsand.logic;

import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAdapter;
import org.sheepy.lily.vulkan.api.process.IProcessAdapter;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.IPipelineTask;
import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.util.FPSCounter;

public final class VSandMainLoop implements Runnable
{
	private final FPSCounter fpsCounter = new FPSCounter();
	private final VSandApplication application;
	private final boolean benchmarkMode;
	private final int stopIteration;

	private IProcessAdapter boardProcessAdapter;
	private IProcessAdapter renderProcessAdapter;
	private IPipelineTask boardImageBarrier;
	private boolean loaded = false;
	private long currentIteration = 0;
	private long startNs;
	private long frameDurationNs;
	private long nextRenderDate;

	public static VSandMainLoop create(VSandApplication application)
	{
		return new VSandMainLoop(application, false, -1);
	}

	public static VSandMainLoop createBenchmark(VSandApplication application, int iterationCount)
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
	public void run()
	{
		if (loaded == false)
		{
			load();
			loaded = true;
		}

		if (DebugUtil.DEBUG_ENABLED) fpsCounter.step();

		boardProcessAdapter.run();

		if (application.isNextMode() == true)
		{
			application.setNextMode(false);
			application.setPaused(true);
		}

		if (benchmarkMode == false)
		{
			renderProcessAdapter.run();
		}
		else
		{
			if (renderProcessAdapter != null && nextRenderDate < System.nanoTime())
			{
				renderProcessAdapter.run();
				nextRenderDate = System.nanoTime() + frameDurationNs;
				boardImageBarrier.setEnabled(true);
			}
			else
			{
				boardImageBarrier.setEnabled(false);
			}
		}

		currentIteration++;
		if (currentIteration == stopIteration)
		{
			application.setRun(false);
			printBenchmarkResult();
		}
	}

	private void load()
	{
		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		final var processes = vulkanEngine.getProcesses();
		final var boardProcess = (ComputeProcess) processes.get(0);
		final var boardToPixelPipeline = (ComputePipeline) (boardProcess.getPipelinePkg().getPipelines().get(2));
		boardProcessAdapter = boardProcess.adaptNotNull(IProcessAdapter.class);
		boardImageBarrier = boardToPixelPipeline.getTaskPkgs().get(0).getTasks().get(2);
		final var graphicProcess = processes.size() > 1 ? (GraphicProcess) processes.get(1) : null;
		renderProcessAdapter = graphicProcess != null ? graphicProcess.adaptNotNull(IProcessAdapter.class) : null;

		final var engineAdapter = vulkanEngine.adapt(IVulkanEngineAdapter.class);
		final var window = engineAdapter.getWindow();
		if (window != null)
		{
			frameDurationNs = (long) ((1. / window.getRefreshRate()) * 1e9);
		}

		startNs = System.nanoTime();
		nextRenderDate = System.nanoTime() + frameDurationNs;

		if (benchmarkMode == true)
		{
			System.out.println("VSand benchmark is running...");
		}
	}

	private void printBenchmarkResult()
	{
		final double duration = (System.nanoTime() - startNs) / 1e9;
		final double durationPerIteration = duration / stopIteration;

		final long score = (long) ((1. / durationPerIteration));

		System.out.println("\nBenchmark finished successfully");
		System.out.println("Iteration count: " + stopIteration);
		System.out.println("Total duration: " + duration + " seconds");
		System.out.println("\nScore: " + score);
		System.out.println();
	}
}
