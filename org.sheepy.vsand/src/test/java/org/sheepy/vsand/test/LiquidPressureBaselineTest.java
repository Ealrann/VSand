package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.analysis.ColumnMetrics;
import org.sheepy.vsand.analysis.LiquidMetrics;
import org.sheepy.vsand.analysis.LiquidStateAnalyzer;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LiquidPressureBaselineTest
{
	private static final long SEED = 1234L;
	private static final int SIMULATION_SPEED = 4;

	@Test
	void spreadBaselineStaysInsideReferenceEnvelope() throws IOException
	{
		final var samples = runScenario(64,
										64,
										"scenarios/liquids/spread/water_column_flatten_64x64.txt",
										20,
										40,
										80,
										120,
										240);

		assertSpreadSample(samples.get(0), 60, 24, 250, 350);
		assertSpreadSample(samples.get(1), 60, 16, 200, 260);
		assertSpreadSample(samples.get(2), 60, 8, 150, 230);
		assertSpreadSample(samples.get(3), 60, 6, 150, 220);
		assertSpreadSample(samples.get(4), 60, 6, 150, 220);
	}

	@Test
	void uPipeBaselineKeepsStrongRightArmRise() throws IOException
	{
		final var samples = runScenario(64,
										64,
										"scenarios/liquids/u-pipe/water_u_pipe_64x64.txt",
										120,
										240);

		assertUPipeSample(samples.get(0), 10, 40_000);
		assertUPipeSample(samples.get(1), 14, 50_000);
		assertTrue(samples.get(1).metrics().horizontalChunkSeamVoidPockets() == 0,
				   "Expected U-pipe horizontal seam pockets to settle to 0 at frame %d, got %d"
						   .formatted(samples.get(1).frame(), samples.get(1).metrics().horizontalChunkSeamVoidPockets()));
	}

	@Test
	void slopeBaselineKeepsChunkBoundaryArtifactsBounded() throws IOException
	{
		final var samples = runScenario(96,
										64,
										"scenarios/liquids/slope/water_slope_chunk_boundary_96x64.txt",
										40,
										240);

		assertLiquidCells(samples.get(0), 558);
		assertTrue(samples.get(0).metrics().bboxWidth() >= 90,
				   "Expected slope water to reach width >= 90 at frame %d, got %d"
						   .formatted(samples.get(0).frame(), samples.get(0).metrics().bboxWidth()));
		assertLiquidCellsBetween(samples.get(1), 557, 558);
		assertTrue(samples.get(1).metrics().bboxWidth() >= 90,
				   "Expected slope water to remain spread at frame %d, got width %d"
						   .formatted(samples.get(1).frame(), samples.get(1).metrics().bboxWidth()));
		assertTrue(samples.get(1).metrics().surfaceRange() <= 8,
				   "Expected slope surface range <= 8 at frame %d, got %d"
						   .formatted(samples.get(1).frame(), samples.get(1).metrics().surfaceRange()));
		assertTrue(samples.get(1).metrics().horizontalChunkSeamVoidPockets() <= 1,
				   "Expected slope horizontal seam pockets <= 1 at frame %d, got %d"
						   .formatted(samples.get(1).frame(), samples.get(1).metrics().horizontalChunkSeamVoidPockets()));
		assertTrue(samples.get(1).metrics().verticalChunkSeamVoidPockets() <= 3,
				   "Expected slope vertical seam pockets <= 3 at frame %d, got %d"
						   .formatted(samples.get(1).frame(), samples.get(1).metrics().verticalChunkSeamVoidPockets()));
	}

	private static List<Sample> runScenario(final int width,
											final int height,
											final String scenarioPath,
											final long... frames) throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(width, height, SEED);
		harness.setBoardUpdateRepeatCount(SIMULATION_SPEED);
		harness.applyScenario(scenarioPath(scenarioPath));

		final var water = harness.materialIndex("Water");
		final var states = harness.fetchBoardsAndMassAtIterations(260, frames);
		assertEquals(frames.length, states.size(), "Frame labels must match fetched states");

		final var samples = new ArrayList<Sample>(states.size());
		for (int i = 0; i < states.size(); i++)
		{
			final var state = states.get(i);
			final var metrics = LiquidStateAnalyzer.analyze(state.board(), state.mass(), water, "Water");
			samples.add(new Sample(frames[i], metrics));
		}
		return List.copyOf(samples);
	}

	private static void assertSpreadSample(final Sample sample,
										   final int minWidth,
										   final int maxSurfaceRange,
										   final int minCoreCells,
										   final int maxCoreCells)
	{
		assertLiquidCells(sample, 651);
		assertTrue(sample.metrics().bboxWidth() >= minWidth,
				   "Expected spread width >= %d at frame %d, got %d"
						   .formatted(minWidth, sample.frame(), sample.metrics().bboxWidth()));
		assertTrue(sample.metrics().surfaceRange() <= maxSurfaceRange,
				   "Expected spread surface range <= %d at frame %d, got %d"
						   .formatted(maxSurfaceRange, sample.frame(), sample.metrics().surfaceRange()));
		assertBetween("spread core cells",
					  sample.frame(),
					  sumCells(sample.metrics(), 12, 28),
					  minCoreCells,
					  maxCoreCells);
	}

	private static void assertUPipeSample(final Sample sample, final int minRightArmCells, final long minRightArmMass)
	{
		assertLiquidCells(sample, 179);
		assertTrue(sample.metrics().bboxWidth() >= 29,
				   "Expected U-pipe water width >= 29 at frame %d, got %d"
						   .formatted(sample.frame(), sample.metrics().bboxWidth()));
		assertTrue(sample.metrics().surfaceRange() <= 20,
				   "Expected U-pipe surface range <= 20 at frame %d, got %d"
						   .formatted(sample.frame(), sample.metrics().surfaceRange()));
		assertTrue(sumCells(sample.metrics(), 42, 46) >= minRightArmCells,
				   "Expected U-pipe right arm cells >= %d at frame %d, got %d"
						   .formatted(minRightArmCells, sample.frame(), sumCells(sample.metrics(), 42, 46)));
		assertTrue(sumMass(sample.metrics(), 42, 46) >= minRightArmMass,
				   "Expected U-pipe right arm mass >= %d at frame %d, got %d"
						   .formatted(minRightArmMass, sample.frame(), sumMass(sample.metrics(), 42, 46)));
	}

	private static void assertLiquidCells(final Sample sample, final int expectedCells)
	{
		assertEquals(expectedCells,
					 sample.metrics().cellCount(),
					 "Expected water cell count to match baseline at frame %d".formatted(sample.frame()));
	}

	private static void assertLiquidCellsBetween(final Sample sample, final int minCells, final int maxCells)
	{
		assertBetween("water cell count", sample.frame(), sample.metrics().cellCount(), minCells, maxCells);
	}

	private static void assertBetween(final String label,
									  final long frame,
									  final int value,
									  final int minInclusive,
									  final int maxInclusive)
	{
		assertTrue(value >= minInclusive && value <= maxInclusive,
				   "Expected %s at frame %d in [%d, %d], got %d"
						   .formatted(label, frame, minInclusive, maxInclusive, value));
	}

	private static int sumCells(final LiquidMetrics metrics, final int x0, final int x1Inclusive)
	{
		return metrics.columnProfile()
					  .columns()
					  .stream()
					  .filter(column -> isInsideColumnRange(column, x0, x1Inclusive))
					  .mapToInt(ColumnMetrics::cellCount)
					  .sum();
	}

	private static long sumMass(final LiquidMetrics metrics, final int x0, final int x1Inclusive)
	{
		return metrics.columnProfile()
					  .columns()
					  .stream()
					  .filter(column -> isInsideColumnRange(column, x0, x1Inclusive))
					  .mapToLong(ColumnMetrics::mass)
					  .sum();
	}

	private static boolean isInsideColumnRange(final ColumnMetrics column, final int x0, final int x1Inclusive)
	{
		return column.x() >= x0 && column.x() <= x1Inclusive;
	}

	private static Path scenarioPath(final String relativePath)
	{
		final var candidates = List.of(Path.of(relativePath),
									   Path.of("..").resolve(relativePath),
									   Path.of("VSand").resolve(relativePath));
		for (final var candidate : candidates)
		{
			if (Files.exists(candidate))
			{
				return candidate;
			}
		}
		throw new AssertionError("Missing baseline scenario: " + relativePath);
	}

	private record Sample(long frame, LiquidMetrics metrics)
	{
	}
}
