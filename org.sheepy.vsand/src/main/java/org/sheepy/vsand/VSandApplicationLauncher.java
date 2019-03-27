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
		Module module = VSandApplicationLauncher.class.getModule();
		Resource resource = null;
		try
		{
			InputStream inputStream = module.getResourceAsStream(APPLICATION_PATH);
			resource = resourceLoader.loadResource(inputStream);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		Application application = (Application) resource.getContents().get(0);

		VSandMainLoop mainLoop = new VSandMainLoop();
		ApplicationLauncher.launch(application, mainLoop);
	}
}
