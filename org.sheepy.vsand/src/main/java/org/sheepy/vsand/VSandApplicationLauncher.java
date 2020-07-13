package org.sheepy.vsand;

import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.resource.IResourceLoader;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.vsand.logic.VSandMainLoop;
import org.sheepy.vsand.model.VSandApplication;

import java.io.IOException;

public class VSandApplicationLauncher
{
	private static final String APPLICATION_PATH = "Application.vsand";
	private static final String APPLICATION_VERSION = "1.4.0";

	private static final IResourceLoader resourceLoader = IResourceLoader.INSTANCE;

	public static void main(String[] args) throws IOException
	{
		DebugUtil.parseMainArgs(args);
		final var application = loadApplication();
		final var mainLoop = VSandMainLoop.create(application);

		LilyLauncher.launch(application, mainLoop);
	}

	public static VSandApplication loadApplication() throws IOException
	{
		final var module = VSandApplicationLauncher.class.getModule();
		final var inputStream = module.getResourceAsStream(APPLICATION_PATH);
		final var resource = resourceLoader.loadResource(inputStream);
		final var res = (VSandApplication) resource.getContents().get(0);
		res.setVersion(APPLICATION_VERSION);
		return res;
	}
}
