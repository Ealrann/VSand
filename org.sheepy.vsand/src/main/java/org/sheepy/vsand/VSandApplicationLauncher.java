package org.sheepy.vsand;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.sheepy.lily.core.api.application.ApplicationLauncher;
import org.sheepy.lily.core.api.resource.IResourceLoader;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.vulkan.model.IProcess;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.resource.Buffer;
import org.sheepy.lily.vulkan.model.resource.Image;
import org.sheepy.vsand.buffer.BoardBufferLoader;
import org.sheepy.vsand.buffer.BoardDecisionLoader;
import org.sheepy.vsand.buffer.BoardImageLoader;
import org.sheepy.vsand.buffer.ConfigurationBufferLoader;
import org.sheepy.vsand.buffer.ModificationsManager;
import org.sheepy.vsand.buffer.TransformationBufferLoader;

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

		String debugProperty = System.getProperty("debug");
		if (debugProperty != null && debugProperty.equals("false") == false)
		{
			application.setDebug(true);
		}

		configureProcesses(application);

		VSandMainLoop mainLoop = new VSandMainLoop();
		ApplicationLauncher.launch(application, mainLoop);
	}

	private static void configureProcesses(Application application)
	{
		var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		int width = application.getSize().x;
		int height = application.getSize().y;

		var boardImage = (Image) vulkanEngine.getResourcePkg().getResources().get(0);
		BoardImageLoader.load(boardImage, width, height);
		
		EList<IProcess> processes = vulkanEngine.getProcesses();
		for (IProcess process : processes)
		{
			if (process instanceof ComputeProcess)
			{
				var boardProcess = (ComputeProcess) process;
				var resources = boardProcess.getResourcePkg().getResources();

				var boardBuffer = (Buffer) resources.get(0);
				var configurationBuffer = (Buffer) resources.get(1);
				var transformationBuffer = (Buffer) resources.get(2);
				var decisionBuffer = (Buffer) resources.get(3);

				var modificationBuffer = (Buffer) resources.get(4);
				modificationBuffer.setSize(ModificationsManager.BYTE_SIZE);
				BoardBufferLoader.load(boardBuffer, width, height);
				BoardDecisionLoader.load(decisionBuffer, width, height);
				ConfigurationBufferLoader.load(configurationBuffer);
				TransformationBufferLoader.load(transformationBuffer);
			}
		}
	}
}
