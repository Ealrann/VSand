package org.sheepy.vsand;

import org.junit.jupiter.api.Test;
import org.sheepy.lily.core.api.application.ApplicationLauncher;

public class VSandTest
{
	@Test
	public void test()
	{
		final var application = VSandBenchmarkLauncher.createTestApplication();
		final var mainLoop = VSandMainLoop.createBenchmark(application, 120);

		ApplicationLauncher.launch(application, mainLoop);
	}
}
