package org.sheepy.vsand;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sheepy.lily.core.api.application.ApplicationLauncher;
import org.sheepy.lily.vulkan.model.VulkanEngine;

public class VSandHeadlessBenchmarkLauncher
{
	public static void main(String[] args)
	{
		final var application = VSandBenchmarkLauncher.createTestApplication();
		application.setHeadless(true);
		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		EcoreUtil.delete(vulkanEngine.getProcesses().get(1));

		final var mainLoop = VSandMainLoop.createBenchmark(application, 1400);

		ApplicationLauncher.launch(application, mainLoop);
	}
}
