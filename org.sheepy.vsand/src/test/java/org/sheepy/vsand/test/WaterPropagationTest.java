package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.FetchedMass;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class WaterPropagationTest
{
	private static final int M_FULL = 4096;

	@Test
	void waterPropagatesLeft() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(32, 16);
		harness.frameBoard("Wall", 2);
		harness.drawSquare("Water", 24, 8, 10);

		final var states = harness.fetchBoardsAndMassAtIterations(160, 1, 120);
		final var after1 = states.getFirst();
		final var after120 = states.get(1);

		final int water = harness.materialIndex("Water");

		final long leftAfter1 = sumMassInRect(after1.board(), after1.mass(), 2, 2, 16, 14, water);
		final long leftAfter120 = sumMassInRect(after120.board(), after120.mass(), 2, 2, 16, 14, water);
		assertTrue(leftAfter120 >= leftAfter1,
				   "Expected water mass in the left area to be non-decreasing (leftAfter1=%d, leftAfter120=%d)"
						   .formatted(leftAfter1, leftAfter120));
		assertTrue(leftAfter120 >= 25L * M_FULL,
				   "Expected at least %d mass to reach the left area after 120 iterations (leftAfter1=%d, leftAfter120=%d)"
						   .formatted(25L * M_FULL, leftAfter1, leftAfter120));
	}

	@Test
	void waterBalancesLeftAndRight() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(48, 16);
		harness.frameBoard("Wall", 2);
		harness.drawSquare("Water", 23, 8, 10);
		harness.drawSquare("Water", 24, 8, 10);

		final var state = harness.fetchBoardAndMassAfterIterations(40, 120);
		final int water = harness.materialIndex("Water");

		final long leftMass = sumMassInRect(state.board(), state.mass(), 2, 2, 24, 14, water);
		final long rightMass = sumMassInRect(state.board(), state.mass(), 24, 2, 46, 14, water);
		final long total = leftMass + rightMass;
		final long diff = Math.abs(leftMass - rightMass);
		assertTrue(total > 0, "Expected some water mass to exist");
		assertTrue(diff * 20 <= total,
				   "Expected left/right water mass to be within 5%% after 40 iterations (left=%d, right=%d, diff=%d, total=%d)"
						   .formatted(leftMass, rightMass, diff, total));
	}

	private static long sumMassInRect(final org.sheepy.vsand.testutil.FetchedBoard board,
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
}
