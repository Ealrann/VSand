package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.vsand.VSandBenchmarkLauncher;
import org.sheepy.vsand.logic.VSandMainLoop;

import java.io.IOException;

public class VSandTest
{
	@Test
	public void test() throws IOException
	{
		final var application = VSandBenchmarkLauncher.createTestApplication();
		final var mainLoop = VSandMainLoop.createBenchmark(application, 120);

		LilyLauncher.launch(application, mainLoop);
	}
}
