package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.FetchedMass;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LiquidMassInvariantTest
{
	private static final int M_CAP_MAX = 6144;

	@Test
	void drawingWaterCreatesMassOnWaterOnly() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(16, 16);
		harness.fillBoard("Wall");
		harness.drawSquare("Water", 8, 8, 6);

		final var state = harness.fetchBoardAndMassAfterIterations(3, 24);
		final var board = state.board();
		final var mass = state.mass();

		final int water = harness.materialIndex("Water");
		assertOnlyWaterHasMass(board, mass, water);
		assertTrue(totalMass(board, mass, water) > 0, "Expected some water mass to exist");
	}

	@Test
	void massMovesWithWater() throws IOException
	{
			final var harness = VSandTestHarness.loadDeterministic(32, 16);
			harness.frameBoard("Wall", 2);
			harness.drawSquare("Water", 24, 8, 10);

			final var states = harness.fetchBoardsAndMassAtIterations(160, 8, 120);
			final int water = harness.materialIndex("Water");

			final var after8 = states.getFirst();
			final var after120 = states.get(1);

			assertOnlyWaterHasMass(after8.board(), after8.mass(), water);
			assertOnlyWaterHasMass(after120.board(), after120.mass(), water);
			final long massAfter8 = totalMass(after8.board(), after8.mass(), water);
			final long massAfter120 = totalMass(after120.board(), after120.mass(), water);
			assertEquals(massAfter8,
						 massAfter120,
						 "Expected water mass to be conserved (after8=%d, after120=%d)".formatted(massAfter8, massAfter120));
		}

	private static void assertOnlyWaterHasMass(final org.sheepy.vsand.testutil.FetchedBoard board,
											  final FetchedMass mass,
											  final int water)
	{
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				final int mat = board.cell(x, y);
				final int m = mass.mass(x, y);
				if (mat == water)
				{
					assertTrue(m > 0 && m <= M_CAP_MAX, "Unexpected water mass at (%d, %d)".formatted(x, y));
				}
				else
				{
					assertEquals(0, m, "Unexpected mass at (%d, %d)".formatted(x, y));
				}
			}
		}
	}

	private static long totalMass(final org.sheepy.vsand.testutil.FetchedBoard board,
								  final FetchedMass mass,
								  final int water)
	{
		long total = 0;
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (board.cell(x, y) == water)
				{
					total += mass.mass(x, y);
				}
			}
		}
		return total;
	}

}
