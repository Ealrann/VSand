package org.sheepy.vsand;

import java.io.IOException;

import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.resource.IResourceLoader;
import org.sheepy.vsand.model.VSandApplication;

public class VSandApplicationLauncher
{
	private static final String APPLICATION_PATH = "Application.vsand";
	private static final String APPLICATION_VERSION = "1.3.1";

	private static final IResourceLoader resourceLoader = IResourceLoader.INSTANCE;

	public static void main(String[] args)
	{
		final var application = loadApplication();
		final var mainLoop = VSandMainLoop.create(application);

		LilyLauncher.launch(application, mainLoop);
	}

	public static VSandApplication loadApplication()
	{
		VSandApplication res = null;

		final var module = VSandApplicationLauncher.class.getModule();
		try
		{
			final var inputStream = module.getResourceAsStream(APPLICATION_PATH);
			final var resource = resourceLoader.loadResource(inputStream);
			res = (VSandApplication) resource.getContents().get(0);
			res.setVersion(APPLICATION_VERSION);
		} catch (final IOException e)
		{
			e.printStackTrace();
		}

		return res;
	}
}
