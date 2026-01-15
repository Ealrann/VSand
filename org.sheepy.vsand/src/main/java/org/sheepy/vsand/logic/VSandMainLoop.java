package org.sheepy.vsand.logic;

import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.vulkan.api.engine.IVulkanEngineAllocation;
import org.sheepy.lily.vulkan.api.process.IProcessAdapter;
import org.sheepy.lily.vulkan.model.vulkan.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.AbstractPipeline;
import org.sheepy.lily.vulkan.model.process.CompositePipeline;
import org.sheepy.lily.vulkan.model.process.IPipelineTask;
import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vsand.model.vsand.VSandApplication;
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

		if (application.nextMode() == true)
		{
			application.nextMode(false);
			application.paused(true);
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
				boardImageBarrier.enabled(true);
			}
			else
			{
				boardImageBarrier.enabled(false);
			}
		}

		currentIteration++;
		if (currentIteration == stopIteration)
		{
			boardProcessAdapter.waitIdle();
			application.run(false);
			printBenchmarkResult();
		}
	}

	private void load()
	{
		final var vulkanEngine = (VulkanEngine) application.engines().get(0);
		final var processes = vulkanEngine.processes();
		final var boardProcess = (ComputeProcess) processes.get(0);
		final var boardToPixelPipeline = findBoardToPixelPipeline(boardProcess);
		if (boardToPixelPipeline == null)
		{
			throw new IllegalStateException("Missing compute pipeline 'Board to Pixel' in VSand application model.");
		}
		boardProcessAdapter = boardProcess.adaptNotNull(IProcessAdapter.class);
		boardImageBarrier = boardToPixelPipeline.taskPkgs().get(0).tasks().get(2);
		final var graphicProcess = processes.size() > 1 ? (GraphicProcess) processes.get(1) : null;
		renderProcessAdapter = graphicProcess != null ? graphicProcess.adaptNotNull(IProcessAdapter.class) : null;

		final var engineAdapter = vulkanEngine.adapt(IVulkanEngineAllocation.class);
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

	private static ComputePipeline findBoardToPixelPipeline(final ComputeProcess boardProcess)
	{
		final var pipelinePkg = boardProcess.pipelinePkg();
		if (pipelinePkg == null) return null;

		final var pipelines = pipelinePkg.pipelines();
		final var byName = findComputePipelineByName(pipelines, "Board to Pixel");
		if (byName != null) return byName;

		return findComputePipelineByShaderName(pipelines, "boardToPixel");
	}

	private static ComputePipeline findComputePipelineByName(final java.util.List<? extends AbstractPipeline> pipelines,
															 final String name)
	{
		if (pipelines == null) return null;
		for (final var pipeline : pipelines)
		{
			if (pipeline instanceof final ComputePipeline computePipeline)
			{
				if (name.equals(computePipeline.name()))
				{
					return computePipeline;
				}
			}
			else if (pipeline instanceof final CompositePipeline compositePipeline)
			{
				final var nested = findComputePipelineByName(compositePipeline.pipelines(), name);
				if (nested != null) return nested;
			}
		}
		return null;
	}

	private static ComputePipeline findComputePipelineByShaderName(final java.util.List<? extends AbstractPipeline> pipelines,
																   final String shaderName)
	{
		if (pipelines == null) return null;
		for (final var pipeline : pipelines)
		{
			if (pipeline instanceof final ComputePipeline computePipeline)
			{
				final var shader = computePipeline.shader();
				if (shader != null && shaderName.equals(shader.name()))
				{
					return computePipeline;
				}
			}
			else if (pipeline instanceof final CompositePipeline compositePipeline)
			{
				final var nested = findComputePipelineByShaderName(compositePipeline.pipelines(), shaderName);
				if (nested != null) return nested;
			}
		}
		return null;
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
