package org.sheepy.vsand;

import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.resource.IResourceService;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.vsand.logic.VSandMainLoop;
import org.sheepy.vsand.model.VSandApplication;

import java.io.IOException;
import java.util.Properties;

public final class VSandApplicationLauncher
{
	private static final String APPLICATION_PATH = "Application.vsand";
	private static final String PROPERTIES_FILE = "version.properties";

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
		final var application = (VSandApplication) IResourceService.INSTANCE.loadApplication(module, APPLICATION_PATH)
																			.orElseThrow(() -> new RuntimeException(
																					"Can't load Application"));

		final var properties = new Properties();
		properties.load(module.getResourceAsStream(PROPERTIES_FILE));
		application.setVersion(properties.getProperty("version").replaceAll("'", ""));
		return application;
	}
}
