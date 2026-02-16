package org.sheepy.vsand.test;

import org.junit.jupiter.api.Test;
import org.sheepy.vsand.testutil.VSandTestHarness;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class LiquidMassStackingTest
{
	private static final int M_FULL = 4096;

	@Test
	void stackedWaterShouldCompressBeyondBottomCell() throws IOException
	{
		final var harness = VSandTestHarness.loadDeterministic(32, 32, 1234L);
		harness.frameBoard("Wall", 1);
		harness.drawLine("Wall", 15, 1, 15, 30, 1);
		harness.drawLine("Wall", 17, 1, 17, 30, 1);
		harness.drawLine("Water", 16, 2, 16, 20, 1);

		final var state = harness.fetchBoardAndMassAfterIterations(200, 220);
		final var board = state.board();
		final var mass = state.mass();

		final int water = harness.materialIndex("Water");
		final int tubeX = 16;

		int bottomY = -1;
		int waterCellsInTube = 0;
		int waterCellsOutsideTube = 0;
		for (int y = 0; y < board.height(); y++)
		{
			for (int x = 0; x < board.width(); x++)
			{
				if (board.cell(x, y) == water)
				{
					if (x == tubeX)
					{
						waterCellsInTube++;
						bottomY = y;
					}
					else
					{
						waterCellsOutsideTube++;
					}
				}
			}
		}

		assertTrue(waterCellsInTube >= 2, "Expected water to remain in the tube after settling");
		assertEquals(0, waterCellsOutsideTube, "Expected water to remain confined in the 1-cell wide tube");

		final int bottomMass = mass.mass(tubeX, bottomY);
		final int aboveY = bottomY - 1;
		assertTrue(aboveY >= 0 && board.cell(tubeX, aboveY) == water,
				   "Expected at least 2 stacked water cells at the bottom (bottomY=%d)".formatted(bottomY));
		final int aboveMass = mass.mass(tubeX, aboveY);

		assertTrue(bottomMass > M_FULL,
				   "Expected the bottom cell to be compressed when water stacks (bottomY=%d, bottomMass=%d)".formatted(bottomY,
																												  bottomMass));
		assertTrue(aboveMass > M_FULL,
				   "Expected compression to propagate beyond the bottom cell (bottomY=%d, aboveY=%d, bottomMass=%d, aboveMass=%d)"
						   .formatted(bottomY, aboveY, bottomMass, aboveMass));
	}
}

