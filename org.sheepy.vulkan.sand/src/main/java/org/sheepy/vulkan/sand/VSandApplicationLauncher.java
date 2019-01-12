package org.sheepy.vulkan.sand;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.sheepy.common.api.application.ApplicationLauncher;
import org.sheepy.common.api.resource.IResourceLoader;
import org.sheepy.common.model.application.Application;

public class VSandApplicationLauncher
{
	private static final String APPLICATION_PATH = "Application.vsand";

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

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
		
		String debugProperty = System.getProperty("debug");
		if(debugProperty != null && debugProperty.equals("false") == false)
		{
			application.setDebug(true);
		}
		
		ApplicationLauncher.launch(application);
	}
}
