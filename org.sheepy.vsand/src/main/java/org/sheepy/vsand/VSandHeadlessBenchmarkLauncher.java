package org.sheepy.vsand;

import org.logoce.lmf.core.api.util.ModelUtil;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.vulkan.model.vulkan.VulkanEngine;
import org.sheepy.vsand.logic.VSandMainLoop;

import java.io.IOException;

public final class VSandHeadlessBenchmarkLauncher
{
	public static void main(String[] args) throws IOException
	{
		DebugUtil.parseMainArgs(args);

		final var application = VSandBenchmarkLauncher.createTestApplication();
		application.scene(null);
		final var vulkanEngine = (VulkanEngine) application.engines().get(0);
		ModelUtil.delete(vulkanEngine.processes().get(1));

		final var mainLoop = VSandMainLoop.createBenchmark(application, VSandBenchmarkLauncher.DEFAULT_ITERATION_COUNT);

		LilyLauncher.launch(application, mainLoop);
	}
}
