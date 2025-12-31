package org.sheepy.vsand;

import org.joml.Vector2i;
import org.logoce.lmf.core.api.util.ModelUtil;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.vulkan.model.process.CompositePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.vulkan.VulkanEngine;
import org.sheepy.vsand.dump.BoardStateDumpLoop;
import org.sheepy.vsand.dump.DumpConfig;
import org.sheepy.vsand.dump.Scenario;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;

public final class VSandStateDumpLauncher
{
	public static void main(final String[] args) throws IOException
	{
		DebugUtil.parseMainArgs(args);
		final DumpConfig config;
		try
		{
			config = DumpConfig.parse(args);
		}
		catch (IllegalArgumentException e)
		{
			System.err.println(e.getMessage());
			return;
		}

		final var application = createHeadlessApplication(config.size());
		config.applyTo(application);

		final Scenario scenario;
		try
		{
			scenario = Scenario.parse(config.scenarioPath());
		}
		catch (RuntimeException e)
		{
			System.err.println("Failed to parse scenario: " + config.scenarioPath());
			e.printStackTrace(System.err);
			return;
		}
		scenario.applyTo(application);

		final var simulationPipeline = findSimulationPipeline(application);
		final var mainLoop = new BoardStateDumpLoop(application, simulationPipeline, config);

		LilyLauncher.launch(application, mainLoop);
	}

	private static VSandApplication createHeadlessApplication(final Vector2i size) throws IOException
	{
		final var application = VSandApplicationLauncher.loadApplication();
		application.size(new Vector2i(size));
		if (application.scene() != null)
		{
			application.scene().size(new Vector2i(size));
		}

		application.scene(null);
		final var vulkanEngine = (VulkanEngine) application.engines().getFirst();
		if (vulkanEngine.processes().size() > 1)
		{
			ModelUtil.delete(vulkanEngine.processes().get(1));
		}

		final var computeProcess = (ComputeProcess) vulkanEngine.processes().getFirst();
		computeProcess.resetAllowed(true);

		return application;
	}

	private static CompositePipeline findSimulationPipeline(final VSandApplication application)
	{
		return application.streamTree()
						  .filter(CompositePipeline.class::isInstance)
						  .map(CompositePipeline.class::cast)
						  .filter(pipeline -> "Simulation".equals(pipeline.name()))
						  .findFirst()
						  .orElse(null);
	}
}
