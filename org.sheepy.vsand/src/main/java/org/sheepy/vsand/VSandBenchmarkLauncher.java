package org.sheepy.vsand;

import java.util.List;

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
		final var mainLoop = VSandMainLoop.createBenchmark(application, 1000);

		ApplicationLauncher.launch(application, mainLoop);
	}

	public static VSandApplication createTestApplication()
	{
		final var application = VSandApplicationLauncher.loadApplication();
		
		EcoreUtil.delete(application.getViews().get(0));
		
		drawTestBoard(application);
		return application;
	}

	public static void drawTestBoard(VSandApplication application)
	{
		final var drawQueue = application.getDrawQueue();
		final var materialList = application.getMaterials().getMaterials();
		final var sand = findMaterial(materialList, "Sand");
		final var wall = findMaterial(materialList, "Wall");
		final var water = findMaterial(materialList, "Water");
		final var wax = findMaterial(materialList, "Wax");
		final var lava = findMaterial(materialList, "Lava");
		final var fuel = findMaterial(materialList, "Fuel");

		drawQueue.add(drawCircle(wall, 300, 400, 24));
		drawQueue.add(drawLine(wall, 300, 400, 300, 600, 24));
		drawQueue.add(drawCircle(wall, 300, 600, 24));
		drawQueue.add(drawLine(wall, 300, 600, 600, 600, 24));
		drawQueue.add(drawCircle(wall, 600, 600, 24));
		drawQueue.add(drawLine(wall, 600, 600, 600, 400, 24));
		drawQueue.add(drawCircle(wall, 600, 400, 24));

		drawQueue.add(drawLine(sand, 320, 550, 570, 550, 32));
		drawQueue.add(drawLine(water, 320, 510, 570, 510, 32));
		drawQueue.add(drawLine(fuel, 320, 470, 570, 470, 32));

		drawQueue.add(drawCircle(wax, 325, 400, 24));
		drawQueue.add(drawLine(wax, 325, 400, 575, 400, 24));

		drawQueue.add(drawCircle(lava, 450, 300, 96));
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

	private static DrawCommand drawLine(final Material sand,
										int x1,
										int y1,
										int x2,
										int y2,
										int size)
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
