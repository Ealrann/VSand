package org.sheepy.vsand.dump;

import org.sheepy.lily.vulkan.api.process.IProcessAdapter;
import org.sheepy.lily.vulkan.model.process.AbstractPipeline;
import org.sheepy.lily.vulkan.model.process.CompositePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.vulkan.VulkanEngine;
import org.sheepy.vsand.fetch.BoardFetchService;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;
import java.util.Objects;

public final class BoardStateDumpLoop implements Runnable
{
	private static final int FETCH_ONLY_SPEED = 2;

	private final VSandApplication application;
	private final CompositePipeline simulationPipeline;
	private final DumpConfig config;

	private final BoardFetchService fetchService;

	private IProcessAdapter computeProcessAdapter;
	private StateDumpWriter writer;
	private AbstractPipeline fetchPipeline;

	private boolean loaded = false;
	private Phase phase = Phase.DRAIN_DRAW_QUEUE;
	private boolean drawQueueDrainedOnce = false;
	private int frameIndex = 0;

	private byte[] lastSeenBoard = null;

	public BoardStateDumpLoop(final VSandApplication application,
							  final CompositePipeline simulationPipeline,
							  final DumpConfig config)
	{
		this.application = application;
		this.simulationPipeline = simulationPipeline;
		this.config = config;
		this.fetchService = application.adaptNotNull(BoardFetchService.class);
	}

	@Override
	public void run()
	{
		ensureLoaded();

		try
		{
			switch (phase)
			{
				case DRAIN_DRAW_QUEUE -> drainDrawQueue();
				case DUMP_INITIAL -> dumpInitial();
				case RUN_AND_DUMP -> runAndDump();
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("Dump failed", e);
		}
	}

	private void ensureLoaded()
	{
		if (loaded) return;

		final var vulkanEngine = (VulkanEngine) application.engines().getFirst();
		final var computeProcess = (ComputeProcess) vulkanEngine.processes().getFirst();
		computeProcessAdapter = computeProcess.adaptNotNull(IProcessAdapter.class);

		fetchPipeline = findFetchPipeline(simulationPipeline);

		try
		{
			writer = new StateDumpWriter(application, config);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to initialize dump writer", e);
		}

		loaded = true;
	}

	private void drainDrawQueue()
	{
		setSimulationEnabled(false);

		if (application.drawQueue().isEmpty() == false)
		{
			computeProcessAdapter.run();
			return;
		}

		if (drawQueueDrainedOnce == false)
		{
			drawQueueDrainedOnce = true;
			computeProcessAdapter.run();
			return;
		}

		phase = Phase.DUMP_INITIAL;
	}

	private void dumpInitial() throws IOException
	{
		setSimulationEnabled(false);

		final int originalSpeed = application.speed();
		application.speed(FETCH_ONLY_SPEED);

		final var state = fetchStateWithSimulationDisabled();
		writer.writeFrame(0, 0, state.board, state.mass);
		System.out.println("Wrote initial dump to: " + writer.outDir().toAbsolutePath());

		application.speed(originalSpeed);
		phase = Phase.RUN_AND_DUMP;
		frameIndex = 1;
	}

	private void runAndDump() throws IOException
	{
		if (frameIndex > config.frames())
		{
			System.out.println("Wrote dumps to: " + writer.outDir().toAbsolutePath());
			application.run(false);
			return;
		}

		final int originalSpeed = application.speed();

		setSimulationEnabled(true);
		application.speed(1);
		for (int i = 0; i < config.speed(); i++)
		{
			computeProcessAdapter.run();
		}

		setSimulationEnabled(false);
		application.speed(FETCH_ONLY_SPEED);
		final var state = fetchStateWithSimulationDisabled();

		final int simulatedTicks = frameIndex * config.speed();
		writer.writeFrame(frameIndex, simulatedTicks, state.board, state.mass);
		frameIndex++;

		application.speed(originalSpeed);
	}

	private FetchedState fetchStateWithSimulationDisabled()
	{
		setSimulationEnabled(false);

		final var previousBoard = fetchService.lastBoard();
		lastSeenBoard = previousBoard;
		if (fetchService.requestFetchNow() == false)
		{
			throw new IllegalStateException("Fetch request rejected (already in progress?)");
		}
		computeProcessAdapter.run();

		final var board = fetchService.lastBoard();
		final var mass = fetchService.lastMass();
		if (board != null && board != lastSeenBoard)
		{
			Objects.requireNonNull(mass, "Expected mass to be fetched with board");
			return new FetchedState(board, mass);
		}

		for (int i = 0; i < 8; i++)
		{
			computeProcessAdapter.run();
			final var retryBoard = fetchService.lastBoard();
			final var retryMass = fetchService.lastMass();
			if (retryBoard != null && retryBoard != lastSeenBoard)
			{
				Objects.requireNonNull(retryMass, "Expected mass to be fetched with board");
				return new FetchedState(retryBoard, retryMass);
			}
		}

		throw new IllegalStateException("Fetch did not complete (board unchanged)");
	}

	private void setSimulationEnabled(final boolean enabled)
	{
		if (simulationPipeline == null) return;

		simulationPipeline.record(true);

		final var pipelines = simulationPipeline.pipelines();
		if (pipelines == null || pipelines.isEmpty()) return;

		if (enabled)
		{
			pipelines.forEach(pipeline -> pipeline.record(true));
		}
		else
		{
			pipelines.forEach(pipeline -> pipeline.record(pipeline == fetchPipeline));
		}
	}

	private static int ensureEven(final int value)
	{
		return (value & 1) == 0 ? value : value + 1;
	}

	private static AbstractPipeline findFetchPipeline(final CompositePipeline simulationPipeline)
	{
		if (simulationPipeline == null) return null;

		return simulationPipeline.pipelines()
								 .stream()
								 .filter(pipeline -> "Fetch".equals(pipeline.name()))
								 .findFirst()
								 .orElse(null);
	}

	private enum Phase
	{
		DRAIN_DRAW_QUEUE,
		DUMP_INITIAL,
		RUN_AND_DUMP
	}

	private record FetchedState(byte[] board, byte[] mass)
	{
	}
}
