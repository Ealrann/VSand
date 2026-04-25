package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.analysis.LiquidMetrics;
import org.sheepy.vsand.analysis.LiquidStateAnalyzer;
import org.sheepy.vsand.testutil.FetchedBoard;
import org.sheepy.vsand.testutil.FetchedMass;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class PressureLiquidRegressionTest
{
	private static final long SEED = 1234L;
	private static final int SIMULATION_SPEED = 4;
	private static final int M_CAP_MAX = 6144;

	@Test
	void waterColumnSpreadsWithoutLosingParticles() throws IOException
	{
		final var harness = waterSpreadHarness();
		final var water = harness.materialIndex("Water");
		final var states = harness.fetchBoardsAndMassAtIterations(260, 20, 40, 80, 240);
		final var samples = samples(states, water, 20, 40, 80, 240);
		final var initialCells = samples.getFirst().metrics().cellCount();

		for (final var sample : samples)
		{
			assertEquals(initialCells,
						 sample.metrics().cellCount(),
						 "Water cell count drifted at frame %d".formatted(sample.frame()));
			assertOnlyMaterialHasMass(sample.board(), sample.mass(), water);
			assertIsolatedCellsReasonable(sample.metrics());
		}

		assertTrue(coreCellShare(samples.get(0), 12, 28) <= 0.48,
				   "Expected frame 20 spread core share <= 48%%, got %.2f%%"
						   .formatted(coreCellShare(samples.get(0), 12, 28) * 100.0));
		assertTrue(coreCellShare(samples.get(1), 12, 28) <= 0.38,
				   "Expected frame 40 spread core share <= 38%%, got %.2f%%"
						   .formatted(coreCellShare(samples.get(1), 12, 28) * 100.0));
		assertTrue(coreCellShare(samples.get(2), 12, 28) <= 0.35,
				   "Expected frame 80 spread core share <= 35%%, got %.2f%%"
						   .formatted(coreCellShare(samples.get(2), 12, 28) * 100.0));
		assertTrue(samples.get(3).metrics().surfaceRange() <= 4,
				   "Expected settled spread surface range <= 4 at frame 240, got %d"
						   .formatted(samples.get(3).metrics().surfaceRange()));
	}

	@Test
	void waterRisesInUPipeWithoutLosingParticles() throws IOException
	{
		final var harness = waterUPipeHarness();
		final var water = harness.materialIndex("Water");
		final var states = harness.fetchBoardsAndMassAtIterations(260, 20, 80, 120, 240);
		final var samples = samples(states, water, 20, 80, 120, 240);
		final var initialCells = samples.getFirst().metrics().cellCount();

		for (final var sample : samples)
		{
			assertEquals(initialCells,
						 sample.metrics().cellCount(),
						 "Water cell count drifted at frame %d".formatted(sample.frame()));
			assertOnlyMaterialHasMass(sample.board(), sample.mass(), water);
			assertIsolatedCellsReasonable(sample.metrics());
		}

		final var frame120 = samples.get(2);
		final var frame240 = samples.get(3);
		assertTrue(rightArmMassShare(frame120, water) >= 0.06,
				   "Expected U-pipe right arm mass share >= 6%% at frame 120, got %.2f%%"
						   .formatted(rightArmMassShare(frame120, water) * 100.0));
		assertTrue(rightArmMassShare(frame240, water) >= 0.08,
				   "Expected U-pipe right arm mass share >= 8%% at frame 240, got %.2f%%"
						   .formatted(rightArmMassShare(frame240, water) * 100.0));
		assertTrue(countWaterInRightArm(frame240, water) >= 14,
				   "Expected at least 14 water cells in the U-pipe right arm at frame 240, got %d"
						   .formatted(countWaterInRightArm(frame240, water)));
	}

	private static VSandTestHarness waterSpreadHarness() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(64, 64, SEED);
		harness.setBoardUpdateRepeatCount(SIMULATION_SPEED);
		harness.frameBoard("Wall", 1);
		harness.drawLine("Wall", 1, 56, 62, 56, 1);
		harness.drawLine("Water", 20, 10, 20, 42, 16);
		return harness;
	}

	private static VSandTestHarness waterUPipeHarness() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(64, 64, SEED);
		harness.setBoardUpdateRepeatCount(SIMULATION_SPEED);
		harness.frameBoard("Wall", 1);
		harness.drawLine("Wall", 16, 10, 16, 54, 2);
		harness.drawSquare("Wall", 32, 17, 14);
		harness.drawSquare("Wall", 32, 31, 14);
		harness.drawSquare("Wall", 32, 43, 14);
		harness.drawLine("Wall", 48, 10, 48, 54, 2);
		harness.drawLine("Wall", 16, 54, 48, 54, 2);
		harness.drawLine("Water", 20, 16, 20, 50, 5);
		return harness;
	}

	private static List<Sample> samples(final List<VSandTestHarness.FetchedBoardAndMass> states,
										final int water,
										final long... frames)
	{
		assertEquals(frames.length, states.size(), "Frame labels must match fetched states");
		final var samples = new ArrayList<Sample>(states.size());
		for (int i = 0; i < states.size(); i++)
		{
			final var state = states.get(i);
			final var metrics = LiquidStateAnalyzer.analyze(state.board(), state.mass(), water, "Water");
			samples.add(new Sample(frames[i], state.board(), state.mass(), metrics));
		}
		return List.copyOf(samples);
	}

	private static double coreCellShare(final Sample sample, final int x0, final int x1Inclusive)
	{
		final var totalCells = sample.metrics().cellCount();
		if (totalCells == 0) return 0.0;

		final var coreCells = sample.metrics()
									.columnProfile()
									.columns()
									.stream()
									.filter(column -> column.x() >= x0 && column.x() <= x1Inclusive)
									.mapToInt(column -> column.cellCount())
									.sum();
		return (double) coreCells / totalCells;
	}

	private static double rightArmMassShare(final Sample sample, final int water)
	{
		final var rightMass = sumMassInRect(sample.board(), sample.mass(), 42, 0, 47, 64, water);
		final var totalMass = sample.metrics().totalMass();
		return totalMass == 0 ? 0.0 : (double) rightMass / totalMass;
	}

	private static int countWaterInRightArm(final Sample sample, final int water)
	{
		int count = 0;
		for (int y = 0; y < sample.board().height(); y++)
		{
			for (int x = 42; x <= 46; x++)
			{
				if (sample.board().cell(x, y) == water)
				{
					count++;
				}
			}
		}
		return count;
	}

	private static long sumMassInRect(final FetchedBoard board,
									  final FetchedMass mass,
									  final int x0,
									  final int y0,
									  final int x1Exclusive,
									  final int y1Exclusive,
									  final int materialId)
	{
		long sum = 0;
		for (int y = Math.max(0, y0); y < Math.min(board.height(), y1Exclusive); y++)
		{
			for (int x = Math.max(0, x0); x < Math.min(board.width(), x1Exclusive); x++)
			{
				if (board.cell(x, y) == materialId)
				{
					sum += mass.mass(x, y);
				}
			}
		}
		return sum;
	}

	private static void assertOnlyMaterialHasMass(final FetchedBoard board, final FetchedMass mass, final int material)
	{
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				final var cellMass = mass.mass(x, y);
				if (board.cell(x, y) == material)
				{
					assertTrue(cellMass <= M_CAP_MAX, "Unexpected liquid mass at (%d, %d)".formatted(x, y));
				}
				else
				{
					assertEquals(0, cellMass, "Unexpected non-liquid mass at (%d, %d)".formatted(x, y));
				}
			}
		}
	}

	private static void assertIsolatedCellsReasonable(final LiquidMetrics metrics)
	{
		final var maxIsolatedCells = Math.max(16, metrics.cellCount() / 4);
		assertTrue(metrics.isolatedCells() <= maxIsolatedCells,
				   "Unexpected isolated liquid cells for %s: %d > %d"
						   .formatted(metrics.materialName(), metrics.isolatedCells(), maxIsolatedCells));
	}

	private record Sample(long frame, FetchedBoard board, FetchedMass mass, LiquidMetrics metrics)
	{
	}
}
