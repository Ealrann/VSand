package org.sheepy.vsand;

import org.logoce.lmf.core.api.util.ModelUtil;
import org.joml.Vector2i;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.vulkan.model.vulkan.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vsand.logic.VSandMainLoop;
import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.DrawCommand;
import org.sheepy.vsand.model.vsand.DrawLine;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;
import java.util.List;

public final class VSandBenchmarkLauncher
{
	public static final int DEFAULT_ITERATION_COUNT = 3000;

	public static void main(String[] args) throws IOException
	{
		DebugUtil.parseMainArgs(args);

		final var application = createTestApplication();
		application.size(new Vector2i(1024, 600));
		application.scene().size(new Vector2i(1024, 600));

		final var vulkanEngine = (VulkanEngine) application.engines().get(0);
		final var graphicProcess = (GraphicProcess) vulkanEngine.processes().get(1);
		graphicProcess.executionManager().waitForExecution().clear();

		final var mainLoop = VSandMainLoop.createBenchmark(application, DEFAULT_ITERATION_COUNT);
		LilyLauncher.launch(application, mainLoop);
	}

	public static VSandApplication createTestApplication() throws IOException
	{
		final var application = VSandApplicationLauncher.loadApplication();

		// remove UI
		ModelUtil.delete(application.scene().compositors().get(1));

		final DemoDrawer drawer = new DemoDrawer(application);
		drawer.drawLavaPot(75, 400);

		return application;
	}

	private static final class DemoDrawer
	{
		private final List<DrawCommand> drawQueue;
		private final Material sand;
		private final Material plant;
		private final Material wall;
		private final Material water;
		private final Material lava;
		private final Material petrol;

		public DemoDrawer(VSandApplication application)
		{
			drawQueue = application.drawQueue();
			final var materialList = application.materials().materials();
			sand = findMaterial(materialList, "Sand");
			plant = findMaterial(materialList, "Plant");
			wall = findMaterial(materialList, "Wall");
			water = findMaterial(materialList, "Water");
			lava = findMaterial(materialList, "Lava");
			petrol = findMaterial(materialList, "Petrol");
		}

		public void drawLavaPot(int x, int y)
		{
			drawQueue.add(drawCircle(wall, x, y, 20));
			drawQueue.add(drawLine(wall, x, y, x + 150, y + 150, 20));
			drawQueue.add(drawLine(wall, x + 150, y + 150, x + 750, y + 150, 20));
			drawQueue.add(drawLine(wall, x + 750, y + 150, x + 900, y, 20));

			drawQueue.add(drawCircle(sand, x + 100, y + 50, 32));
			drawQueue.add(drawLine(sand, x + 100, y + 50, x + 800, y + 50, 32));

			drawQueue.add(drawCircle(water, x + 100, y + 20, 32));
			drawQueue.add(drawLine(water, x + 100, y + 20, x + 800, y + 20, 32));

			drawQueue.add(drawCircle(sand, x + 300, y + 5, 64));
			drawQueue.add(drawCircle(sand, x + 350, y - 5, 64));
			drawQueue.add(drawCircle(sand, x + 400, y + 5, 64));
			drawQueue.add(drawCircle(sand, x + 600, y + 20, 64));

			drawQueue.add(drawCircle(sand, x + 600, y + 20, 64));

			drawQueue.add(drawCircle(plant, x + 770, y + 80, 2));

			drawQueue.add(drawLine(petrol, x + 100, y - 10, x + 280, y - 10, 16));

			drawQueue.add(drawCircle(lava, x + 150, y - 220, 96));
			drawQueue.add(drawCircle(lava, x + 450, y - 350, 96));
			drawQueue.add(drawCircle(lava, x + 750, y - 250, 96));
		}

		private static DrawCommand drawCircle(final Material sand, int x, int y, int size)
		{
			final var draw = DrawCircle.builder().build();
			draw.material(sand);
			draw.x(x);
			draw.y(y);
			draw.size(size);
			return draw;
		}

		private static DrawCommand drawLine(final Material sand, int x1, int y1, int x2, int y2, int size)
		{
			final var draw = DrawLine.builder().build();
			draw.material(sand);
			draw.x1(x1);
			draw.y1(y1);
			draw.x2(x2);
			draw.y2(y2);
			draw.size(size);
			return draw;
		}

		private static Material findMaterial(List<Material> materials, String name)
		{
			return materials.stream().filter(m -> m.name().equals(name)).findFirst().orElse(null);
		}
	}
}
