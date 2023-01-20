package org.sheepy.vsand;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.joml.Vector2i;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.core.api.util.DebugUtil;
import org.sheepy.lily.vulkan.model.VulkanEngine;
import org.sheepy.lily.vulkan.model.process.graphic.GraphicProcess;
import org.sheepy.vsand.logic.VSandMainLoop;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;

import java.io.IOException;
import java.util.List;

public final class VSandBenchmarkLauncher
{
	public static final int DEFAULT_ITERATION_COUNT = 3000;

	public static void main(String[] args) throws IOException
	{
		DebugUtil.parseMainArgs(args);

		final var application = createTestApplication();
		application.setSize(new Vector2i(1024, 600));
		application.getScene().setSize(new Vector2i(1024, 600));

		final var vulkanEngine = (VulkanEngine) application.getEngines().get(0);
		final var graphicProcess = (GraphicProcess) vulkanEngine.getProcesses().get(1);
		graphicProcess.getExecutionManager().getWaitForExecution().clear();

		final var mainLoop = VSandMainLoop.createBenchmark(application, DEFAULT_ITERATION_COUNT);
		LilyLauncher.launch(application, mainLoop);
	}

	public static VSandApplication createTestApplication() throws IOException
	{
		final var application = VSandApplicationLauncher.loadApplication();

		// remove UI
		EcoreUtil.delete(application.getScene().getCompositors().get(1));

		final DemoDrawer drawer = new DemoDrawer(application);
		drawer.drawLavaPot(75, 400);

		return application;
	}

	private static final class DemoDrawer
	{
		private final EList<DrawCommand> drawQueue;
		private final Material sand;
		private final Material plant;
		private final Material wall;
		private final Material water;
		private final Material lava;
		private final Material petrol;

		public DemoDrawer(VSandApplication application)
		{
			drawQueue = application.getDrawQueue();
			final var materialList = application.getMaterials().getMaterials();
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
			final var draw = VSandFactory.eINSTANCE.createDrawCircle();
			draw.setMaterial(sand);
			draw.setX(x);
			draw.setY(y);
			draw.setSize(size);
			return draw;
		}

		private static DrawCommand drawLine(final Material sand, int x1, int y1, int x2, int y2, int size)
		{
			final var draw = VSandFactory.eINSTANCE.createDrawLine();
			draw.setMaterial(sand);
			draw.setX1(x1);
			draw.setY1(y1);
			draw.setX2(x2);
			draw.setY2(y2);
			draw.setSize(size);
			return draw;
		}

		private static Material findMaterial(List<Material> materials, String name)
		{
			return materials.stream().filter(m -> m.getName().equals(name)).findFirst().orElse(null);
		}
	}
}
