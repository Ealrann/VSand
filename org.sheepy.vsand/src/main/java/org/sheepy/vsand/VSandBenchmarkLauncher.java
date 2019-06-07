package org.sheepy.vsand;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sheepy.lily.core.api.application.ApplicationLauncher;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;

public class VSandBenchmarkLauncher
{
	public static void main(String[] args)
	{
		final var application = createTestApplication();
		final var mainLoop = VSandMainLoop.createBenchmark(application, 2500);

		ApplicationLauncher.launch(application, mainLoop);
	}

	public static VSandApplication createTestApplication()
	{
		final var application = VSandApplicationLauncher.loadApplication();

		// remove UI
		EcoreUtil.delete(application.getViews().get(0));

		final DemoDrawer drawer = new DemoDrawer(application);
		drawer.drawLavaPot(300, 400);
		drawer.drawLavaPot(650, 200);

		return application;
	}

	private static final class DemoDrawer
	{

		private final EList<DrawCommand> drawQueue;
		private final EList<Material> materialList;
		private final Material sand;
		private final Material wall;
		private final Material water;
		private final Material wax;
		private final Material lava;
		private final Material fuel;

		public DemoDrawer(VSandApplication application)
		{
			drawQueue = application.getDrawQueue();
			materialList = application.getMaterials().getMaterials();
			sand = findMaterial(materialList, "Sand");
			wall = findMaterial(materialList, "Wall");
			water = findMaterial(materialList, "Water");
			wax = findMaterial(materialList, "Wax");
			lava = findMaterial(materialList, "Lava");
			fuel = findMaterial(materialList, "Fuel");
		}

		public void drawLavaPot(int x, int y)
		{
			drawQueue.add(drawCircle(wall, x, y, 24));
			drawQueue.add(drawLine(wall, x, y, x, y + 200, 24));
			drawQueue.add(drawCircle(wall, x, y + 200, 24));
			drawQueue.add(drawLine(wall, x, y + 200, x + 300, y + 200, 24));
			drawQueue.add(drawCircle(wall, x + 300, y + 200, 24));
			drawQueue.add(drawLine(wall, x + 300, y + 200, x + 300, y, 24));
			drawQueue.add(drawCircle(wall, x + 300, y, 24));

			drawQueue.add(drawLine(sand, x + 20, y + 150, x + 270, y + 150, 32));
			drawQueue.add(drawLine(water, x + 20, y + 110, x + 270, y + 110, 32));
			drawQueue.add(drawLine(fuel, x + 20, y + 70, x + 270, y + 70, 32));

			drawQueue.add(drawCircle(wax, x + 25, y, 24));
			drawQueue.add(drawLine(wax, x + 25, y, x + 275, y, 24));

			drawQueue.add(drawCircle(lava, x + 150, y - 100, 96));
		}

		private DrawCommand drawCircle(final Material sand, int x, int y, int size)
		{
			final var draw = VSandFactory.eINSTANCE.createDrawCircle();
			draw.setMaterial(sand);
			draw.setX(x);
			draw.setY(y);
			draw.setSize(size);
			return draw;
		}

		private DrawCommand drawLine(final Material sand, int x1, int y1, int x2, int y2, int size)
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
			return materials.stream().filter(m -> m.getName().equals(name)).findFirst().get();
		}
	}
}
