package org.sheepy.vsand;

import org.lwjgl.system.Configuration;
import org.logoce.lmf.core.api.model.ModelRegistry;
import org.logoce.lmf.core.loader.api.loader.LmLoader;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.resource.IModelExtension;
import org.sheepy.lily.core.api.resource.IResourceService;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.vsand.logic.VSandMainLoop;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class VSandApplicationLauncher
{
	private static final String APPLICATION_PATH = "Application.vsand.lm";
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
		final var application = loadApplication(module);
		final var properties = new Properties();
		properties.load(module.getResourceAsStream(PROPERTIES_FILE));
		application.version(properties.getProperty("version").replaceAll("'", ""));
		return application;
	}

	private static VSandApplication loadApplication(final Module module) throws IOException
	{
		final var resourceService = IResourceService.INSTANCE;
		if (resourceService != null)
		{
			final var loadedApplication = resourceService.loadApplication(module, APPLICATION_PATH);
			if (loadedApplication.isPresent())
			{
				return (VSandApplication) loadedApplication.get();
			}
		}

		try (final var inputStream = module.getResourceAsStream(APPLICATION_PATH))
		{
			if (inputStream == null)
			{
				throw new IOException("Resource not found: " + APPLICATION_PATH);
			}

			final var registryBuilder = new ModelRegistry.Builder(ModelRegistry.empty());
			for (final var extension : IModelExtension.EXTENSIONS)
			{
				for (final var pkg : extension.getEPackages())
				{
					registryBuilder.register(pkg.model());
				}
			}

			final var registry = registryBuilder.build();
			final var loader = new LmLoader(registry);
			final var sourceBytes = inputStream.readAllBytes();
			final var roots = loader.loadObjects(new ByteArrayInputStream(sourceBytes));
			if (roots.isEmpty())
			{
				final var document = loader.loadModel(new String(sourceBytes, StandardCharsets.UTF_8));
				document.diagnostics().forEach(d -> System.err.println("[LMF] " + d));
				return throwCantLoad("Empty model roots");
			}

			final var root = roots.getFirst();
			if (root instanceof final VSandApplication application)
			{
				return application;
			}

			System.err.println("Unexpected root type: " + root.getClass());
			return throwCantLoad("Unexpected root type");
		}
	}

	private static VSandApplication throwCantLoad()
	{
		return throwCantLoad(null);
	}

	private static VSandApplication throwCantLoad(final String reason)
	{
		throw new RuntimeException("Can't load Application" + (reason == null || reason.isBlank() ? "" : " (" + reason + ")"));
	}
}
