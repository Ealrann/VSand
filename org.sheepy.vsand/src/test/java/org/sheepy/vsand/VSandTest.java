package org.sheepy.vsand;

import org.junit.jupiter.api.Test;
import org.sheepy.lily.core.api.LilyLauncher;

public class VSandTest
{
	@Test
	public void test()
	{
		final var application = VSandBenchmarkLauncher.createTestApplication();
		final var mainLoop = VSandMainLoop.createBenchmark(application, 120);

		LilyLauncher.launch(application, mainLoop);
	}
}
