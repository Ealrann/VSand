package org.sheepy.vsand.testutil;

import org.joml.Vector2i;
import org.logoce.lmf.core.api.util.ModelUtil;
import org.sheepy.lily.core.api.LilyLauncher;
import org.sheepy.lily.vulkan.api.process.IProcessAdapter;
import org.sheepy.lily.vulkan.model.process.CompositePipeline;
import org.sheepy.lily.vulkan.model.process.compute.ComputeProcess;
import org.sheepy.lily.vulkan.model.vulkan.VulkanEngine;
import org.sheepy.vsand.VSandApplicationLauncher;
import org.sheepy.vsand.fetch.BoardFetchService;
import org.sheepy.vsand.model.vsand.BoardConstantBuffer;
import org.sheepy.vsand.model.vsand.DrawCircle;
import org.sheepy.vsand.model.vsand.DrawLine;
import org.sheepy.vsand.model.vsand.DrawSquare;
import org.sheepy.vsand.model.vsand.Material;
import org.sheepy.vsand.model.vsand.VSandApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class VSandTestHarness
{
	private static final long DEFAULT_RANDOM_SEED = 0x5EED_1234_5678_9ABCL;

	private final VSandApplication application;
	private final BoardFetchService fetchService;
	private final Vector2i size;

	private VSandTestHarness(final VSandApplication application, final Vector2i size)
	{
		this.application = application;
		this.size = new Vector2i(size);
		fetchService = application.adaptNotNull(BoardFetchService.class);
	}

	public static VSandTestHarness load(final int width, final int height) throws IOException
	{
		return load(new Vector2i(width, height));
	}

	public static VSandTestHarness loadDeterministic(final int width, final int height) throws IOException
	{
		return loadDeterministic(new Vector2i(width, height), DEFAULT_RANDOM_SEED);
	}

	public static VSandTestHarness loadDeterministic(final int width, final int height, final long seed) throws IOException
	{
		return loadDeterministic(new Vector2i(width, height), seed);
	}

	public static VSandTestHarness load(final Vector2i size) throws IOException
	{
		final var app = VSandApplicationLauncher.loadApplication();
		app.size(new Vector2i(size));
		if (app.scene() != null)
		{
			app.scene().size(new Vector2i(size));
		}
		makeHeadless(app);
		return new VSandTestHarness(app, size);
	}

	public static VSandTestHarness loadDeterministic(final Vector2i size, final long seed) throws IOException
	{
		final var harness = load(size);
		harness.enableDeterministicRandom(seed);
		return harness;
	}

	public Vector2i size()
	{
		return new Vector2i(size);
	}

	public void enableDeterministicRandom(final long seed)
	{
		final var buffer = findBoardConstantBuffer(application);
		assertNotNull(buffer, "BoardConstantBuffer must exist");
		buffer.deterministicRandom(true);
		buffer.randomSeed(seed);
	}

	public void setBoardUpdateRepeatCount(final int repeatCount)
	{
		application.speed(repeatCount);
		final var simulationPipeline = findSimulationPipeline(application);
		if (simulationPipeline != null)
		{
			simulationPipeline.repeat(repeatCount);
		}
	}

	public Material material(final String name)
	{
		return findMaterial(application.materials().materials(), name);
	}

	public int materialIndex(final String name)
	{
		final var material = material(name);
		assertNotNull(material, "Material '%s' must exist".formatted(name));
		final int index = application.materials().materials().indexOf(material);
		assertTrue(index >= 0, "Material '%s' index should be valid".formatted(name));
		return index;
	}

	public void fillBoard(final String materialName)
	{
		final var material = material(materialName);
		assertNotNull(material, "Material '%s' must exist".formatted(materialName));
		fillBoard(material);
	}

	public void fillBoard(final Material material)
	{
		final int drawSize = Math.max(size.x(), size.y());
		final var draw = DrawSquare.builder().build();
		draw.material(material);
		draw.size(drawSize);
		draw.x(size.x() / 2);
		draw.y(size.y() / 2);
		application.drawQueue().add(draw);
	}

	public void frameBoard(final String materialName, final int thickness)
	{
		final var material = material(materialName);
		assertNotNull(material, "Material '%s' must exist".formatted(materialName));
		frameBoard(material, thickness);
	}

	public void frameBoard(final Material material, final int thickness)
	{
		final int w = size.x();
		final int h = size.y();

		drawLine(material, 0, 0, w - 1, 0, thickness);
		drawLine(material, 0, h - 1, w - 1, h - 1, thickness);
		drawLine(material, 0, 0, 0, h - 1, thickness);
		drawLine(material, w - 1, 0, w - 1, h - 1, thickness);
	}

	public void drawLine(final String materialName,
						 final int x1,
						 final int y1,
						 final int x2,
						 final int y2,
						 final int thickness)
	{
		final var material = material(materialName);
		assertNotNull(material, "Material '%s' must exist".formatted(materialName));
		drawLine(material, x1, y1, x2, y2, thickness);
	}

	public void drawLine(final Material material,
						 final int x1,
						 final int y1,
						 final int x2,
						 final int y2,
						 final int thickness)
	{
		final var draw = DrawLine.builder().build();
		draw.material(material);
		draw.x1(x1);
		draw.y1(y1);
		draw.x2(x2);
		draw.y2(y2);
		draw.size(thickness);
		application.drawQueue().add(draw);
	}

	public void drawCircle(final String materialName, final int x, final int y, final int sizePx)
	{
		final var material = material(materialName);
		assertNotNull(material, "Material '%s' must exist".formatted(materialName));
		drawCircle(material, x, y, sizePx);
	}

	public void drawCircle(final Material material, final int x, final int y, final int sizePx)
	{
		final var draw = DrawCircle.builder().build();
		draw.material(material);
		draw.x(x);
		draw.y(y);
		draw.size(sizePx);
		application.drawQueue().add(draw);
	}

	public void drawSquare(final String materialName, final int x, final int y, final int sizePx)
	{
		final var material = material(materialName);
		assertNotNull(material, "Material '%s' must exist".formatted(materialName));
		drawSquare(material, x, y, sizePx);
	}

	public void drawSquare(final Material material, final int x, final int y, final int sizePx)
	{
		final var draw = DrawSquare.builder().build();
		draw.material(material);
		draw.x(x);
		draw.y(y);
		draw.size(sizePx);
		application.drawQueue().add(draw);
	}

	public FetchedBoard fetchBoardAfterIterations(final long iterationsBeforeFetch, final long maxTotalIterations)
	{
		final var loop = new FetchLoop(application, fetchService, iterationsBeforeFetch, maxTotalIterations);
		LilyLauncher.launch(application, loop);

		final var bytes = fetchService.lastBoard();
		assertNotNull(bytes, "Board must be fetched");
		return FetchedBoard.fromSwizzledBytes(size.x(), size.y(), bytes);
	}

	public FetchedBoardAndMass fetchBoardAndMassAfterIterations(final long iterationsBeforeFetch, final long maxTotalIterations)
	{
		final var loop = new FetchLoop(application, fetchService, iterationsBeforeFetch, maxTotalIterations);
		LilyLauncher.launch(application, loop);

		final var boardBytes = fetchService.lastBoard();
		final var massBytes = fetchService.lastMass();
		assertNotNull(boardBytes, "Board must be fetched");
		assertNotNull(massBytes, "Mass must be fetched");
		return new FetchedBoardAndMass(FetchedBoard.fromSwizzledBytes(size.x(), size.y(), boardBytes),
									   FetchedMass.fromSwizzledBytes(size.x(), size.y(), massBytes));
	}

	public List<FetchedBoard> fetchBoardsAtIterations(final long maxTotalIterations, final long... fetchIterations)
	{
		final var loop = new MultiFetchLoop(application, fetchService, fetchIterations, maxTotalIterations);
		LilyLauncher.launch(application, loop);

		final var results = loop.results();
		assertTrue(results.size() == fetchIterations.length,
				   "Expected %d fetched boards but got %d".formatted(fetchIterations.length, results.size()));
		return results;
	}

	public List<FetchedBoardAndMass> fetchBoardsAndMassAtIterations(final long maxTotalIterations, final long... fetchIterations)
	{
		final var loop = new MultiFetchStateLoop(application, fetchService, fetchIterations, maxTotalIterations);
		LilyLauncher.launch(application, loop);

		final var results = loop.results();
		assertTrue(results.size() == fetchIterations.length,
				   "Expected %d fetched states but got %d".formatted(fetchIterations.length, results.size()));
		return results;
	}

	private static Material findMaterial(final List<Material> materials, final String name)
	{
		return materials.stream().filter(m -> name.equals(m.name())).findFirst().orElse(null);
	}

	private static BoardConstantBuffer findBoardConstantBuffer(final VSandApplication application)
	{
		return application.streamTree()
						  .filter(BoardConstantBuffer.class::isInstance)
						  .map(BoardConstantBuffer.class::cast)
						  .findFirst()
						  .orElse(null);
	}

	private static void makeHeadless(final VSandApplication application)
	{
		application.scene(null);

		final var vulkanEngine = (VulkanEngine) application.engines().getFirst();
		if (vulkanEngine.processes().size() > 1)
		{
			ModelUtil.delete(vulkanEngine.processes().get(1));
		}

		final var computeProcess = (ComputeProcess) vulkanEngine.processes().getFirst();
		computeProcess.resetAllowed(true);
	}

	private static CompositePipeline findSimulationPipeline(final VSandApplication application)
	{
		return application.streamTree()
						  .filter(CompositePipeline.class::isInstance)
						  .map(CompositePipeline.class::cast)
						  .filter(pipeline -> "Simulation".equals(pipeline.name()))
						  .findFirst()
						  .orElse(null);
	}

	private static final class FetchLoop implements Runnable
	{
		private final VSandApplication application;
		private final BoardFetchService fetchService;
		private final long fetchAfterIterations;
		private final long maxIterations;

		private IProcessAdapter boardProcessAdapter;
		private boolean loaded = false;
		private long iteration = 0;

		private FetchLoop(final VSandApplication application,
						  final BoardFetchService fetchService,
						  final long fetchAfterIterations,
						  final long maxIterations)
		{
			this.application = application;
			this.fetchService = fetchService;
			this.fetchAfterIterations = fetchAfterIterations;
			this.maxIterations = maxIterations;
		}

		@Override
		public void run()
		{
			if (loaded == false)
			{
				final var vulkanEngine = (VulkanEngine) application.engines().get(0);
				final var boardProcess = (ComputeProcess) vulkanEngine.processes().get(0);
				boardProcessAdapter = boardProcess.adaptNotNull(IProcessAdapter.class);
				loaded = true;
			}

			if (iteration + 1 == fetchAfterIterations)
			{
				application.fetchRequested(true);
			}

			boardProcessAdapter.run();

			if (fetchService.lastBoard() != null || iteration >= maxIterations)
			{
				application.run(false);
			}
			iteration++;
		}
	}

	private static final class MultiFetchLoop implements Runnable
	{
		private final VSandApplication application;
		private final BoardFetchService fetchService;
		private final long[] fetchAfterIterations;
		private final long maxIterations;
		private final List<FetchedBoard> results = new ArrayList<>();

		private IProcessAdapter boardProcessAdapter;
		private boolean loaded = false;
		private long iteration = 0;
		private int nextFetchIndex = 0;
		private byte[] lastSeenBoard = null;

		private MultiFetchLoop(final VSandApplication application,
							   final BoardFetchService fetchService,
							   final long[] fetchAfterIterations,
							   final long maxIterations)
		{
			this.application = application;
			this.fetchService = fetchService;
			this.fetchAfterIterations = fetchAfterIterations;
			this.maxIterations = maxIterations;
		}

		public List<FetchedBoard> results()
		{
			return results;
		}

		@Override
		public void run()
		{
			if (loaded == false)
			{
				final var vulkanEngine = (VulkanEngine) application.engines().get(0);
				final var boardProcess = (ComputeProcess) vulkanEngine.processes().get(0);
				boardProcessAdapter = boardProcess.adaptNotNull(IProcessAdapter.class);
				loaded = true;
			}

			if (nextFetchIndex < fetchAfterIterations.length && iteration + 1 == fetchAfterIterations[nextFetchIndex])
			{
				application.fetchRequested(true);
				nextFetchIndex++;
			}

			boardProcessAdapter.run();

			final var fetched = fetchService.lastBoard();
			if (fetched != null && fetched != lastSeenBoard)
			{
				final var decoded = FetchedBoard.fromSwizzledBytes(application.size().x(), application.size().y(), fetched);
				results.add(decoded);
				lastSeenBoard = fetched;
			}

			if (results.size() == fetchAfterIterations.length || iteration >= maxIterations)
			{
				application.run(false);
			}
			iteration++;
		}
	}

	public record FetchedBoardAndMass(FetchedBoard board, FetchedMass mass)
	{
	}

	private static final class MultiFetchStateLoop implements Runnable
	{
		private final VSandApplication application;
		private final BoardFetchService fetchService;
		private final long[] fetchAfterIterations;
		private final long maxIterations;
		private final List<FetchedBoardAndMass> results = new ArrayList<>();

		private IProcessAdapter boardProcessAdapter;
		private boolean loaded = false;
		private long iteration = 0;
		private int nextFetchIndex = 0;
		private byte[] lastSeenBoard = null;

		private MultiFetchStateLoop(final VSandApplication application,
									final BoardFetchService fetchService,
									final long[] fetchAfterIterations,
									final long maxIterations)
		{
			this.application = application;
			this.fetchService = fetchService;
			this.fetchAfterIterations = fetchAfterIterations;
			this.maxIterations = maxIterations;
		}

		public List<FetchedBoardAndMass> results()
		{
			return results;
		}

		@Override
		public void run()
		{
			if (loaded == false)
			{
				final var vulkanEngine = (VulkanEngine) application.engines().get(0);
				final var boardProcess = (ComputeProcess) vulkanEngine.processes().get(0);
				boardProcessAdapter = boardProcess.adaptNotNull(IProcessAdapter.class);
				loaded = true;
			}

			if (nextFetchIndex < fetchAfterIterations.length && iteration + 1 == fetchAfterIterations[nextFetchIndex])
			{
				application.fetchRequested(true);
				nextFetchIndex++;
			}

			boardProcessAdapter.run();

			final var fetchedBoard = fetchService.lastBoard();
			if (fetchedBoard != null && fetchedBoard != lastSeenBoard)
			{
				final var fetchedMass = fetchService.lastMass();
				assertNotNull(fetchedMass, "Mass must be fetched");

				final var decodedBoard = FetchedBoard.fromSwizzledBytes(application.size().x(), application.size().y(), fetchedBoard);
				final var decodedMass = FetchedMass.fromSwizzledBytes(application.size().x(), application.size().y(), fetchedMass);
				results.add(new FetchedBoardAndMass(decodedBoard, decodedMass));
				lastSeenBoard = fetchedBoard;
			}

			if (results.size() == fetchAfterIterations.length || iteration >= maxIterations)
			{
				application.run(false);
			}
			iteration++;
		}
	}
}
