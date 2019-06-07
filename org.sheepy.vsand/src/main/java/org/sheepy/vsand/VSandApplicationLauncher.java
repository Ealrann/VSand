package org.sheepy.vsand;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.sheepy.lily.core.api.application.ApplicationLauncher;
import org.sheepy.lily.core.api.resource.IResourceLoader;
import org.sheepy.lily.core.model.application.Application;

public class VSandApplicationLauncher
{
	private static final String APPLICATION_PATH = "Application.vsand";

	private static final IResourceLoader resourceLoader = IResourceLoader.INSTANCE;

	public static void main(String[] args)
	{
		final var module = VSandApplicationLauncher.class.getModule();
		final var resource = loadModuleResource(module);
		final var application = (Application) resource.getContents().get(0);
		final var mainLoop = new VSandMainLoop();

		ApplicationLauncher.launch(application, mainLoop);
	}

	private static Resource loadModuleResource(Module module)
	{
		Resource resource = null;
		try
		{
			final InputStream inputStream = module.getResourceAsStream(APPLICATION_PATH);
			resource = resourceLoader.loadResource(inputStream);
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		return resource;
	}
}
